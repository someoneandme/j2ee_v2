package com.pugwoo.practice.office.POI;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * 2014年12月18日 09:18:48
 * 
 * 关于表格的处理：
 * 1. 增加一行：
 * 2. 增加和当前行相同格式的一行：
 * http://stackoverflow.com/questions/16645344/xwpftablerow-adding-new-row-with-current-style
 * 
 * XWPFTable 有addRow和createRow的方法
 */
public class TestReplaceWord2007 {

	private static void processParagraphs(List<XWPFParagraph> paragraphList,
			String findText, String replaceText) {
		for (XWPFParagraph paragraph : paragraphList) {
			List<XWPFRun> runs = paragraph.getRuns();
			for (XWPFRun run : runs) {
				String text = run.getText(0);
				text = text.replace(findText, replaceText);
				run.setText(text, 0);
			}
		}
	}

	public static void replaceText(XWPFDocument doc, String findText,
			String replaceText) {
		// Paragraph treatment
		List<XWPFParagraph> paragraphList = doc.getParagraphs();
		processParagraphs(paragraphList, findText, replaceText);
		
		// Processing table 处理word2007文档中的表格
		Iterator<XWPFTable> it = doc.getTablesIterator();
		while (it.hasNext()) {
			XWPFTable table = it.next();
			List<XWPFTableRow> rows = table.getRows();
			
			XWPFTableRow newRow = table.createRow(); // 这样就创建了一行并加入到表格后面了!!
//			table.addRow(newRow);  // 如果这里放开，就相当于执行了两遍了
			System.out.println("add new row"); // 只执行了一次
			// 插入新的一行，很奇怪加上这两行之后，表格多了2行.
			// 这里只执行了一次
			
			for (XWPFTableRow row : rows) {
				List<XWPFTableCell> cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
					processParagraphs(paragraphListTable, findText, replaceText);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		String outputPath = "C:/output_2007.docx";
		
		String dataDOC = "/poi/a.docx";
		InputStream in = new BufferedInputStream(TestReplaceWord2007
				.class.getResourceAsStream(dataDOC));

		XWPFDocument doc = new XWPFDocument(in);
		
		replaceText(doc, "{XXXXX_NO}", "我的编号112233");

		FileOutputStream fos = new FileOutputStream(outputPath);
		doc.write(fos);
		fos.flush();
		fos.close();
	}

}
