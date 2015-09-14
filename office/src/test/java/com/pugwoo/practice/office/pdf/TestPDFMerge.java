package com.pugwoo.practice.office.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSmartCopy;



/**
 * 2015年9月10日 09:29:21
 */
public class TestPDFMerge {
	
	public static void concatenatePdfs(List<File> listOfPdfFiles, File outputFile)
			throws DocumentException, IOException {
		
        Document document = new Document();
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        PdfCopy copy = new PdfSmartCopy(document, outputStream);
        document.open();
        for (File inFile : listOfPdfFiles) {
            PdfReader reader = new PdfReader(inFile.getAbsolutePath());
            copy.addDocument(reader);
            reader.close();
        }
        document.close();
    }

	public static void main(String[] args) throws DocumentException, IOException {
		File file1 = new File("C:/1.pdf");
		File file2 = new File("C:/2.pdf");
		List<File> files = new ArrayList<File>();
		files.add(file1);
		files.add(file2);
		
		concatenatePdfs(files, new File("C:/merged.pdf"));
	}
	
}
