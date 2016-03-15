package com.pugwoo.practice.office.poi.template;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Closeables;

/**
 * word 2007模版，TAG标签约定为{}包裹起来的，用不了${}这种写法。
 *
 * 引入动态表格生成标签语言：
 * <foreach items="products" var="product">
 * {product.name} {product.code}
 * </foreach>
 *
 * 其中，items是渲染的List bean名称，而var是自定义的局部变量名，用于循环时获取当前行的属性。
 * 详见例子。
 *
 * @author boyu.xby 2014年12月24日 下午2:36:14
 */
public class WordTemplate extends AbstractTemplate {

    public static final String MERGE_PARAGRAPH_STYLE = "mergeParagraphStyle";

    protected static final Logger LOGGER = LoggerFactory.getLogger(WordTemplate.class);

    private static final String BEGIN_LABEL = "{"; // 只能单个字符
    private static final String END_LABEL = "}"; // 只能单个字符
    private static final String FOREACH_START = "^\\s*<foreach\\s+items=\"\\s*(\\w+)\\s*\"\\s+var=\"\\s*(\\w+)\\s*\"\\s*>\\s*$";
    private static final String FOREACH_END = "</foreach>";
    private static final Pattern FOREACH_START_PATTERN = Pattern.compile(FOREACH_START, Pattern.CASE_INSENSITIVE);
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("([^{]*)\\{([^}]+)\\}([^}]*)");
    private static final Pattern SINGLE_VARIABLE_PATTERN = Pattern.compile("\\{([^}]+)\\}");

    public WordTemplate(InputStream in) {
        super(in);
    }

    public WordTemplate(InputStream in, Map<String, Object> options) {
        super(in, options);
    }

    @Override
    public void merge(Map<String, Object> context, OutputStream out) throws Exception {
        try {
            doMerge(context, out);
        } finally {
            Closeables.closeQuietly(in);
            Closeables.close(out, true);
        }
    }

    protected void doMerge(Map<String, Object> context, OutputStream out) throws Exception {
        XWPFDocument doc = new XWPFDocument(in);

        // 处理段落替换
        processParagraphs(doc.getParagraphs(), context);

        // 处理表格替换
        Iterator<XWPFTable> it = doc.getTablesIterator();
        while (it.hasNext()) {
            processTable(it.next(), context);
        }

        doc.write(out);
    }

    private void processTable(XWPFTable table, Map<String, Object> context) {
        List<XWPFTableRow> rows = table.getRows();
        List<DynamicTableDescriptor> descriptors = new ArrayList<DynamicTableDescriptor>();
        int dynamicStart = -1;
        String itemsName = null, varName = null;
        for (int i = 0; i < rows.size(); i++) {
            XWPFTableRow row = rows.get(i);
            List<XWPFTableCell> cells = row.getTableCells();
            if (cells.size() == 1) {
                XWPFTableCell cell = cells.get(0);
                Matcher m = FOREACH_START_PATTERN.matcher(cell.getText());
                if (m.find()) {
                    dynamicStart = i;
                    itemsName = m.group(1);
                    varName = m.group(2);
                } else if (FOREACH_END.equalsIgnoreCase(cell.getText().trim())) {
                    if (i - dynamicStart > 1 && dynamicStart > -1) {
                        List<?> items = MapUtils.getValue(context, itemsName, List.class);
                        if (items == null) {
                            LOGGER.warn("Can't find {} in context, varName is {}.", itemsName, varName);
                        }
                        descriptors.add(new DynamicTableDescriptor(dynamicStart, i, itemsName, varName, items));
                        dynamicStart = -1;
                    } else {
                        throw new IllegalStateException(String.format("Illegal template, start = %s, end = %s", dynamicStart, i));
                    }
                } else {
                    processParagraphs(cell.getParagraphs(), context);
                }
            } else {
                for (XWPFTableCell cell : cells) {
                    processParagraphs(cell.getParagraphs(), context);
                }
            }
        }

        for (DynamicTableDescriptor descriptor : descriptors) {
            descriptor.process(table);
        }
    }


    private void processParagraphs(List<XWPFParagraph> paragraphs, Map<String, Object> context) {
        boolean mergeParagraphStyle = MapUtils.getValue(context, MERGE_PARAGRAPH_STYLE, Boolean.class, false);
        if (mergeParagraphStyle) {
            processParagraphsMergeStyle(paragraphs, context);
        } else {
            processParagraphsStrictly(paragraphs, context);
        }
    }

    private static void processParagraphsStrictly(List<XWPFParagraph> paragraphs, Map<String, Object> context) {
        for (XWPFParagraph paragraph : paragraphs) {
            List<XWPFRun> runs = paragraph.getRuns();
            int startIndex = runs.size();
            for (int i = 0; i < runs.size(); i++) {
                String text = runs.get(i).getText(0);
                if (text != null) {
                    // XXX 这里可能有bug，当BEGIN_LABEL是多个字符时，他们很可能不在同一个run中，marked by boyu
                    if (text.contains(BEGIN_LABEL)) {
                        startIndex = i;
                    }
                    if (text.contains(END_LABEL)) {
                        if (i >= startIndex) {
                            String mergeText = mergeRun(runs, startIndex, i);
                            Matcher matcher = VARIABLE_PATTERN.matcher(mergeText);
                            if (matcher.find()) {
                                String key = matcher.group(2);
                                doReplace(context, key, runs, startIndex, i);
                            } else {
                                LOGGER.warn("Unexpected error, {} not match {}", VARIABLE_PATTERN, mergeText);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void processParagraphsMergeStyle(List<XWPFParagraph> paragraphs, Map<String, Object> context) {
        for (XWPFParagraph paragraph : paragraphs) {
            processParagraph(context, paragraph);
        }
    }

    private static void processParagraph(Map<String, Object> context, XWPFParagraph paragraph) {
        String text = paragraph.getText();
        String replaceText = doReplace(context, text);
        if (!replaceText.equals(text)) {
            for (int i = 0; i < paragraph.getRuns().size(); i++) {
                XWPFRun run = paragraph.getRuns().get(i);
                if (i == 0) {
                    run.setText(replaceText, 0);
                } else {
                    run.setText("", 0);
                }
            }
        }
    }


    private static void doReplace(Map<String, Object> context, String key, List<XWPFRun> runs, int startIndex, int endIndex) {
        if (context.containsKey(key)) {
            Object value = context.get(key);
            String replacedText = value != null ? value.toString() : "";
            if (endIndex == startIndex) {
                XWPFRun run = runs.get(endIndex);
                String text = run.getText(0);
                run.setText(text.replace(String.format("%s%s%s", BEGIN_LABEL, key, END_LABEL), replacedText), 0);
            } else {
                XWPFRun originalStartRun = runs.get(startIndex);
                String originalStartText = originalStartRun.getText(0);
                int m = originalStartText.lastIndexOf(BEGIN_LABEL);
                originalStartRun.setText(String.format("%s%s", originalStartText.substring(0, m), replacedText), 0);

                XWPFRun originalEndRun = runs.get(endIndex);
                String originalEndText = originalEndRun.getText(0);
                int n = originalEndText.indexOf(END_LABEL);
                originalEndRun.setText(originalEndText.substring(n + 1), 0);

                for (int j = startIndex + 1; j < endIndex; j++) {
                    runs.get(j).setText("", 0);
                }
            }
        }
    }

    private static String doReplace(Map<String, Object> context, String text) {
        Matcher matcher = SINGLE_VARIABLE_PATTERN.matcher(text);
        StringBuilder sb = new StringBuilder();
        int start = 0;
        while (matcher.find()) {
            sb.append(text.substring(start, matcher.start()));
            String key = matcher.group(1);
            if (context.containsKey(key)) {
                sb.append(context.get(matcher.group(1)));
            } else {
                sb.append(matcher.group(0));
            }
            start = matcher.end();
        }
        if (start > 0) {
            sb.append(text.substring(start));
            return sb.toString();
        }
        return text;
    }


    private static String mergeRun(List<XWPFRun> runs, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            sb.append(runs.get(i).getText(0));
        }
        return sb.toString();
    }

    private class DynamicTableDescriptor {
        public final int lowerIndex, upperIndex;
        public final String itemsName, varName;
        public final List<?> items;

        public DynamicTableDescriptor(int lowerIndex, int upperIndex, String itemsName, String varName, List<?> items) {
            this.lowerIndex = lowerIndex;
            this.upperIndex = upperIndex;
            this.itemsName = itemsName;
            this.varName = varName;
            this.items = items;
        }

        public void process(XWPFTable table) {
            if (upperIndex - lowerIndex > 3) {
                throw new IllegalStateException("foreach just can allow 1 or 2 rows.");
            }
            List<XWPFTableRow> templateRows = new ArrayList<XWPFTableRow>();
            for (int i = lowerIndex + 1; i < upperIndex; i++) {
                templateRows.add(table.getRow(i));
            }
            table.removeRow(upperIndex);
            if (items != null && items.size() > 0) {
                for (int i = items.size() - 1;  i >= 0; i--) {
                    Object item = items.get(i);
                    Map<String, Object> context = getContext(item);
                    for (XWPFTableRow templateRow : templateRows) {
                        XWPFTableRow newRow = copyRow(templateRow, table, context);
                        table.addRow(newRow, lowerIndex + 2);
                    }
                }
            }
            for (int i = 0; i <= templateRows.size(); i++) {
                table.removeRow(lowerIndex);
            }
        }

        private XWPFTableRow copyRow(XWPFTableRow source, XWPFTable table, Map<String, Object> context) {
            XWPFTableRow target = new XWPFTableRow((CTRow) source.getCtRow().copy(), table);
            for (XWPFTableCell cell : target.getTableCells()) {
                processParagraphsMergeStyle(cell.getParagraphs(), context);
            }
            return target;
        }

        private Map<String, Object> getContext(Object item) {
            Map<String, Object> context = new HashMap<String, Object>();
            if (item instanceof Map) {
                for (Object key : ((Map<?, ?>) item).keySet()) {
                    context.put(String.format("%s.%s", varName, key), ((Map<?, ?>) item).get(key));
                }
            } else {
                LOGGER.warn("{} is not a map.", item);
            }
            return context;
        }
    }


//    public static void main(String[] args) throws Exception {
//        InputStream in = new FileInputStream("/Users/james/Downloads/ExportSaleEntrust.docx");
//        WordTemplate template = new WordTemplate(in);
//        Map<String, Object> context = new HashMap<String, Object>();
//        context.put("products", ImmutableList.of(ImmutableMap.of("id", 1, "name", "name")));
//        context.put("projects", ImmutableList.of(ImmutableMap.of("id", 1, "name", "name")));
//        context.put("fees", ImmutableList.of(ImmutableMap.of("id", 1, "name", "name")));
//        template.merge(context, new FileOutputStream("/Users/james/Downloads/ExportSaleEntrust-ttt.docx"));
//    }


}
