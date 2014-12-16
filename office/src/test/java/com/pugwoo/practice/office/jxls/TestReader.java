package com.pugwoo.practice.office.jxls;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReadStatus;
import net.sf.jxls.reader.XLSReader;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * 2014年12月16日 13:49:48<br>
 * http://jxls.sourceforge.net/reference/reader.html<br>
 * 
 * 主要内容: <br>
 * 通过配置的方式，读取出指定模版的excel文件里的内容，然后再拼凑成java bean.
 */
public class TestReader {

	@Test
	public void test() throws InvalidFormatException, IOException, SAXException {

		String xmlConfig = "/jxls/departments.xml";
		String dataXLS = "/jxls/departmentdata.xls";
		
		InputStream inputXML = new BufferedInputStream(getClass()
				.getResourceAsStream(xmlConfig));
		XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);
		InputStream inputXLS = new BufferedInputStream(getClass()
				.getResourceAsStream(dataXLS));

		Department department = new Department();
		Department hrDepartment = new Department();

		List<Employee> departments = new ArrayList<Employee>();
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("department", department);
		beans.put("hrDepartment", hrDepartment);
		beans.put("departments", departments);

		XLSReadStatus readStatus = mainReader.read(inputXLS, beans);

		// 打印出读取出来的数据
		System.out.println(department.getName());
		
	}

}
