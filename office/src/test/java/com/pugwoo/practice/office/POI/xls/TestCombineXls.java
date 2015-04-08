package com.pugwoo.practice.office.POI.xls;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 2015年4月7日 16:02:28
 * 经过实验 发现复制还是复制不了完全的格式，首先背景图片没有了，其它行还是有点微调
 */
public class TestCombineXls {

	public static void main(String[] args) throws IOException {
		
		String excel1 = "/poi/xls/excel_1.xls";
		String excel2 = "/poi/xls/excel_2.xls";
		
		String outFile = "C:/combine_result.xls";
		
		InputStream in = TestCombineXls.class.getResourceAsStream(excel1);
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		
		InputStream in2 = TestCombineXls.class.getResourceAsStream(excel2);
		HSSFWorkbook workbook2 = new HSSFWorkbook(in2);
		
		HSSFSheet sheet = workbook.createSheet("sheet from 2"); // 创建了一个新的sheet
		Util.copySheets(sheet, workbook2.getSheetAt(0)); // 将第二个文件的sheet复制写入进来
		
		workbook.write(new FileOutputStream(outFile));
	}
	
}
