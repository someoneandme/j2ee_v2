package com.pugwoo.practice.office.POI;

import java.io.FileOutputStream;
import java.io.IOException;
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
		
		// Processing table
		Iterator<XWPFTable> it = doc.getTablesIterator();
		while (it.hasNext()) {
			XWPFTable table = it.next();
			List<XWPFTableRow> rows = table.getRows();
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
		String inputFile = "C:/ExportSaleEntrust.doc";
		String outputPath = "C:/output_2007.docx";

		OPCPackage pack = POIXMLDocument.openPackage(inputFile);
		XWPFDocument doc = new XWPFDocument(pack);
		
		replaceText(doc, "#XXXXX_NO#", "我的编号");

		FileOutputStream fos = new FileOutputStream(outputPath);
		doc.write(fos);
		fos.flush();
		fos.close();
	}

}
