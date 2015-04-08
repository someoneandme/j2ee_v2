package com.pugwoo.practice.office.POI.doc;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

/**
 * 2014年12月17日 16:58:01
 * 
 * 经过测试，目前只适合2003的word的文件
 * 
 * http://stackoverflow.com/questions/22615594/replacing-variables-in-a-word-
 * document-template-with-java
 * 
 * 表格方面的处理例子：http://tiantianhappy.iteye.com/blog/1839998
 */
public class TestReplaceWord2003 {

	public static void main(String[] args) {
		String outputPath = "C:/output_2003.doc";

		String dataDOC = "/poi/a.doc";
		InputStream in = new BufferedInputStream(
				TestReplaceWord2007.class.getResourceAsStream(dataDOC));

		try {
			HWPFDocument doc = new HWPFDocument(in);
			doc = replaceText(doc, "{XXXXX_NO}", "我的编号112233");
			doc = replaceText(doc, "{this_is_a_long_tag_in_a_table}",
					"2222233333");

			// 处理word 2003中的动态表格，自动增加一行及表格定位写入
			proceessTable(doc);

			saveWord(outputPath, doc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试一下表格的处理，主要是动态增加一行，并且根据单元格的位置
	 * 
	 * @param doc
	 */
	private static void proceessTable(HWPFDocument doc) {
		Range range = doc.getRange();// 得到文档的读取范围

		TableIterator it = new TableIterator(range);
		while (it.hasNext()) {
			Table table = (Table) it.next();
			
			// table.insertAfter("xxxxxxxxxxxx"); // 这个也不是新增行，也会损害表格
			
			// 迭代行，默认从0开始
			for (int i = 0; i < table.numRows(); i++) {
				TableRow tr = table.getRow(i);
				// 迭代列，默认从0开始
				for (int j = 0; j < tr.numCells(); j++) {
					TableCell td = tr.getCell(j);// 取得单元格
					// 取得单元格的内容
					for (int k = 0; k < td.numParagraphs(); k++) {
						Paragraph para = td.getParagraph(k);
						String s = para.text();
						System.out.println(">>>" + s);
					}
				}
				
				// 重要，由于经过测试，poi对新增一行记录比较困难，还没有好做法
				if(i==1) {
					tr.delete();
				}
				
				// if(i==2) tr.insertAfter("xxxxxxxxx"); 
				// 这样之类的不是新增行，反而会损害表格
			}
		}
		
		// TODO 新增一行记录
		// 目前看来，新增一行是比较困难的，删除一行倒是好做
		// 所以，动态的新增也只能通过删除来实现
	}

	private static HWPFDocument replaceText(HWPFDocument doc, String findText,
			String replaceText) {
		Range r1 = doc.getRange();
		// 每一层都是可以替换的,另外一个程序就是这么做的
		r1.replaceText(findText, replaceText);

		// for (int i = 0; i < r1.numSections(); ++i ) {
		// Section s = r1.getSection(i);
		// for (int x = 0; x < s.numParagraphs(); x++) {
		// Paragraph p = s.getParagraph(x);
		// for (int z = 0; z < p.numCharacterRuns(); z++) {
		// CharacterRun run = p.getCharacterRun(z);
		// String text = run.text();
		// if(text.contains(findText)) {
		// run.replaceText(findText, replaceText);
		// }
		// }
		// }
		// }

		return doc;
	}

	private static void saveWord(String filePath, HWPFDocument doc)
			throws FileNotFoundException, IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			doc.write(out);
		} finally {
			out.close();
		}
	}

}
