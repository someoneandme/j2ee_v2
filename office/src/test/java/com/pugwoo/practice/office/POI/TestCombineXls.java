package com.pugwoo.practice.office.POI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 2015年4月7日 16:02:28
 * 经过实验 发现复制还是复制不了完全的格式，首先背景图片没有了，其它行还是有点微调
 */
public class TestCombineXls {

	public static void main(String[] args) throws IOException {
		FileInputStream in = new FileInputStream("C:/blue.xls");
		
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		
		HSSFSheet sheet = workbook.createSheet();
		Util.copySheets(sheet, workbook.getSheetAt(0));
		
		workbook.write(new FileOutputStream(new File("C:/blue_copy.xls")));
	}
	
}
