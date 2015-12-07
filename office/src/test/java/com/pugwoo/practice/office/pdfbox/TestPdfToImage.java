package com.pugwoo.practice.office.pdfbox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * 2015年12月7日 17:00:06
 * 测试从pdf转成图片
 * 
 * 对pdf的form的方式支持不好，出现了??的情况，form设置为readonly也没有用
 */
public class TestPdfToImage {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
        try {
	        String sourceDir = "C:/a.pdf"; // Pdf files are read from this folder
	        String destinationDir = "C:/image/"; // converted images from pdf document are saved here
	
	        File sourceFile = new File(sourceDir);
	        File destinationFile = new File(destinationDir);
	        if (!destinationFile.exists()) {
	            destinationFile.mkdir();
	            System.out.println("Folder Created -> "+ destinationFile.getAbsolutePath());
	        }
	        if (sourceFile.exists()) {
	            System.out.println("Images copied to Folder: "+ destinationFile.getName());             
	            PDDocument document = PDDocument.load(sourceDir);
	            List<PDPage> list = document.getDocumentCatalog().getAllPages();
	            System.out.println("Total files to be converted -> "+ list.size());
	
	            String fileName = sourceFile.getName().replace(".pdf", "");             
	            int pageNumber = 1;
	            for (PDPage page : list) {
	                BufferedImage image = page.convertToImage();
	                File outputfile = new File(destinationDir + fileName +"_"+ pageNumber +".png");
	                System.out.println("Image Created -> "+ outputfile.getName());
	                ImageIO.write(image, "png", outputfile);
	                pageNumber++;
	            }
	            document.close();
	            System.out.println("Converted Images are saved at -> "+ destinationFile.getAbsolutePath());
	        } else {
	            System.err.println(sourceFile.getName() +" File not exists");
	        }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
}
