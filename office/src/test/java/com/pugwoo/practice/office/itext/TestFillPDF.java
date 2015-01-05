package com.pugwoo.practice.office.itext;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * 2014年12月18日 14:47:08<br>
 *
 * 参考:http://wenku.baidu.com/link?url=K9T4zXZw0hGgRKaf7_jMRGXJEKgKwGMrDyEsu9iHMaLYwJ7t68x4ue5r0RNekOQUCZ_7a07o3hhXBRAifo0DzdU8etyHFRYP6Fd-zEwHwtq
 * 
 * 【注意】用adobe acrobat编辑form field时，把字体设置为宋体，而不是adobe自己的宋体，这样可以解决英文字体挤在一起的情况。
 */
public class TestFillPDF {

	public static void main(String[] args) throws IOException, DocumentException {
        String dataXLS = "/itext/学生成绩登记表 - form.pdf";
        InputStream inputXML = new BufferedInputStream(TestFillPDF.class
                .getResourceAsStream(dataXLS));
		
		PdfReader reader = new PdfReader(inputXML);
		PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("c:/output.pdf"));
		
		// 取出报表模版中的所有字段
		AcroFields form = stamp.getAcroFields();
		
		// 为字段赋值，注意字段名称是区分大小写的
		form.setField("stu_no_1", "372102");
		form.setField("stu_name_1", "nick xie");
		form.setField("stu_obj_1", "数学");
		form.setField("stu_score_1", String.valueOf(99));
		
		stamp.setFormFlattening(true);
		// 必须调用
		stamp.close();
	}
}
