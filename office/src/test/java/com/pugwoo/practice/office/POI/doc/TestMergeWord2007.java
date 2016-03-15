package com.pugwoo.practice.office.POI.doc;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;

/**
 * 测试合并两份word文档
 */
public class TestMergeWord2007 {
	
	public static void merge(XWPFDocument src1Document, XWPFDocument src2Document, OutputStream dest) throws Exception {
	    CTBody src1Body = src1Document.getDocument().getBody();
	    
	    // src1Document加多一个分页标记
	    XWPFParagraph paragraph = src1Document.createParagraph();
	    XWPFRun run = paragraph.createRun();
	    run.addBreak(BreakType.PAGE);
	    
	    CTBody src2Body = src2Document.getDocument().getBody();        
	    
	    appendBody(src1Body, src2Body);
	    src1Document.write(dest);
	}
	
	public static void merge(InputStream src1, InputStream src2, OutputStream dest) throws Exception {
	    XWPFDocument src1Document = new XWPFDocument(src1);        
	    XWPFDocument src2Document = new XWPFDocument(src2);
	    merge(src1Document, src2Document, dest);
	}

	/**
	 * 思路：获得源CTBody src和目标CTBody append的xml格式的doc内容，可以理解为doc都可以表达为xml格式的数据
	 * 下面的做法就是合并两份文件的xml格式数据，然后覆盖到源目标文件上
	 */
	private static void appendBody(CTBody src, CTBody append) throws Exception {
		XmlOptions optionsOuter = new XmlOptions();
		optionsOuter.setSaveOuter();
		String appendString = append.xmlText(optionsOuter);
		String srcString = src.xmlText();
		String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
		String mainPart = srcString.substring(srcString.indexOf(">") + 1, srcString.lastIndexOf("<"));
		String sufix = srcString.substring(srcString.lastIndexOf("<"));
		String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
		CTBody makeBody = CTBody.Factory.parse(prefix + mainPart + addPart + sufix);
		src.set(makeBody);
	}

	public static void main(String[] args) throws Exception {
		String outputPath = "C:/output_2007_merge.docx";
		
		String dataDOC = "/poi/a.docx";
		InputStream in1 = new BufferedInputStream(
				TestReplaceWord2007.class.getResourceAsStream(dataDOC));
		InputStream in2 = new BufferedInputStream(
				TestReplaceWord2007.class.getResourceAsStream(dataDOC));

		FileOutputStream fos = new FileOutputStream(outputPath);
		merge(in1, in2, fos);
		fos.close();
	}

}
