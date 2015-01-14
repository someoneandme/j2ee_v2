package com.pugwoo.practice.office.docx4j;

import java.io.File;
import java.io.OutputStream;

import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

/**
 * 2015年1月14日 10:23:30
 */
public class ConvertToPDF {

	public static void main(String[] args) throws Exception {
		String inputfilepath = "c:/a.docx";
		String outputfilepath = "c:/a.pdf";
		
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(new File(inputfilepath));

		// Set up font mapper (optional)
		Mapper fontMapper = new IdentityPlusMapper();
		wordMLPackage.setFontMapper(fontMapper);
		PhysicalFont font = PhysicalFonts.get("Arial Unicode MS");
		// make sure this is in your regex (if any)!!!
		if (font != null) {
			fontMapper.put("Times New Roman", font);
			fontMapper.put("Arial", font);
		}
		fontMapper.put("Libian SC Regular", PhysicalFonts.get("SimSun"));

		// FO exporter setup (required)
		// .. the FOSettings object
		FOSettings foSettings = Docx4J.createFOSettings();
		foSettings.setWmlPackage(wordMLPackage);

		OutputStream os = new java.io.FileOutputStream(outputfilepath);
		
		Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
	}

}
