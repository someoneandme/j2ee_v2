package com.pugwoo.practice.office.POI.doc;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

/**
 * 2014年12月18日 09:18:48
 * 
 * 关于表格的处理： 1. 增加一行： 2. 增加和当前行相同格式的一行：
 * http://stackoverflow.com/questions/16645344/xwpftablerow-adding-new-row-with-current-style
 * 
 * XWPFTable 有addRow和createRow的方法
 */
public class TestReplaceWord2007 {

	private static void processParagraphs(List<XWPFParagraph> paragraphList,
			Map<String, String> map) {
		for (XWPFParagraph paragraph : paragraphList) {
			/**
			 * 在实际使用中，下面的替换方法还是有些问题 在同一个表格中，一个paragraph被拆成多个Runs
			 * 导致我要替换的字符串被拆成多个Runs了 所以替换时是找不到需要替换的文本了
			 * 
			 * 看这里能不能封装成一个比较好的工具
			 * 
			 * 成功了! 参考:
			 * http://stackoverflow.com/questions/22268898/replacing-a-text-in-apache-poi-xwpf
			 *
			 * 这种方式会丢格式，所以应该有另外一种更好的做法
			 */
			
			/**
			 * 新的算法思路：
			 * 将runs看作是连续的段落，要替换的字符串一定是若干个连续的runs!
			 * 算法:
			 *    从左到右遍历runs，如果runs的子集contains key，
			 *    那么尝试去掉第一个runs，如果去掉之后仍然contains，则继续再测试去掉第2个runs
			 *    如果去掉之后没有contains，那么将这一整串的替换成替换后的text，放在第一个run中，其它的runs设空。
			 *    
			 * 完成
			 */
			replace_v2(paragraph, map);
		}
	}
	
	// 包括endPos
	private static String getSubString(List<XWPFRun> runs, int startPos, int endPos) {
		StringBuilder subStr = new StringBuilder();
		for(int i = startPos; i <= endPos; i++) {
			String text = runs.get(i).getText(0);
			if(text != null) {
				subStr.append(text.trim());
			}
		}
		return subStr.toString();
	}
	
	private static void replace_v2(XWPFParagraph paragraph, Map<String, String> map) {
		List<XWPFRun> runs = paragraph.getRuns();
		int i = 0;
		while(i < runs.size()) {
			int j;
			for(j = i; j < runs.size(); j++) {
				String subText = getSubString(runs, i, j);
				
				int newI = i;
				boolean isContain = false;
				for(String key : map.keySet()) {
//					key = "{" + key + "}"; // XXX mark 如果要约定可以由{}包含，那么可以这样写
					if(subText.contains(key)) {
						isContain = true;
						// 尝试让i增加，去掉可能的前缀
						for(int tmp = newI + 1; tmp <= j; tmp++) {
							if(getSubString(runs, tmp, j).contains(key)) {
								newI = tmp;
							} else {
								break;
							}
						}
					}
				}
				
				if(isContain) {
					// 从newI开始替换，替换完之后设置到newI的位置上
					String text = getSubString(runs, newI, j);
					for(String key : map.keySet()) {
						String value = map.get(key);
//						key = "{" + key + "}"; // XXX mark 如果要约定可以由{}包含，那么可以这样写
						if(text.contains(key)) {
							text = text.replace(key, value);
						}
					}
					runs.get(newI).setText(text, 0);
					for(int tmp = newI + 1; tmp <= j; tmp++) {
						runs.get(tmp).setText("", 0);
					}
					break;
				}
			}
			i = j + 1;
		}
	}

	/**
	 * 替换word2007中的tag，包括表格中的
	 * @param doc
	 * @param map
	 */
	public static void replaceText(XWPFDocument doc, Map<String, String> map) {
		// 处理Tag替换
		processParagraphs(doc.getParagraphs(), map);

		// 处理word2007文档中表格的Tag替换
		Iterator<XWPFTable> it = doc.getTablesIterator();
		while (it.hasNext()) {
			XWPFTable table = it.next();
			
			XWPFTableRow newRow = table.createRow(); // 这样就创建了一个空行并加入到表格后面了!!
			// table.addRow(newRow); // 如果这里放开，就相当于执行了两遍了
			System.out.println("add new row" + newRow); // 只执行了一次
			
			// 对于动态新增行，非常重要的就是复制已有的行，格式内容完全保持一致
			// 最重要的是，这个是完全复制
			XWPFTableRow rowToCopy = table.getRow(3);
			XWPFTableRow copiedRow = copyRow(rowToCopy, table);
			
			// 这样是可以替换到的，但是要放在 table.addRow(copiedRow); 之前，这样也算是勉强满足需求
//			{
//				List<XWPFTableCell> cells = copiedRow.getTableCells();
//				for (XWPFTableCell cell : cells) {
//					List<XWPFParagraph> paragraphListTable = cell
//							.getParagraphs();
//					processParagraphs(paragraphListTable, map);
//				}
//			}
			
			table.addRow(copiedRow);
			
			// FIXME 奇怪，新复制出来的行，里面的tag在替换时并没有被替换
			// 经过debug，目前只能说明拿出来的runs的setText根本就没有生效
			
			List<XWPFTableRow> rows = table.getRows();
			for (XWPFTableRow row : rows) {
				List<XWPFTableCell> cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
					processParagraphs(paragraphListTable, map);
				}
			}
			
		}
	}
	
    /**
     * 复制word表格行，这是在处理动态复制表格时，非常重要的方法
     */
	private static XWPFTableRow copyRow(XWPFTableRow source, XWPFTable table) {
        return new XWPFTableRow((CTRow) source.getCtRow().copy(), table);
    }

	public static void main(String[] args) throws IOException {
		String outputPath = "C:/output_2007.docx";

		String dataDOC = "/poi/a.docx";
		InputStream in = new BufferedInputStream(
				TestReplaceWord2007.class.getResourceAsStream(dataDOC));

		XWPFDocument doc = new XWPFDocument(in);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("{XXXXX_NO}", "我的编号112233");
		map.put("{this_is_a_long_tag_in_a_table}", "2222233333");

		replaceText(doc, map);

		FileOutputStream fos = new FileOutputStream(outputPath);
		doc.write(fos);
		fos.flush();
		fos.close();
	}

}
