package com.pugwoo.practice.office.xdocreport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


/**
 * 2015年1月14日 11:20:04
 * 例子代码位置：
 * https://github.com/opensagres/xdocreport.samples/blob/master/samples/fr.opensagres.xdocreport.samples.docx.converters/src/fr/opensagres/xdocreport/samples/docx/converters/pdf/ConvertDocxStructuresToPDF.java
 * 
 * 这个效果好很多
 */
public class TestConvertDocxToPDF {

	public static void main(String[] args) throws Exception {
		String inputFile = "C:/a.docx";
		String outputFile = "C:/a.pdf";
		
		if(args != null && args.length == 2) {
			inputFile = args[0];
			outputFile = args[1];
		}
		
		System.out.println("inputFile:" + inputFile + ",outputFile:" + outputFile);
		
		FileInputStream in = new FileInputStream(inputFile);
		XWPFDocument document = new XWPFDocument(in);
		File outFile = new File(outputFile);
		
		OutputStream out = new FileOutputStream(outFile);
		
        PdfOptions options = null;// PDFViaITextOptions.create().fontEncoding( "windows-1250" );
        PdfConverter.getInstance().convert( document, out, options );
	}
	
}
