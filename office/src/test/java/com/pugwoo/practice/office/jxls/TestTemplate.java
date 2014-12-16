package com.pugwoo.practice.office.jxls;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

/**
 * 2014年12月16日 14:32:06<br>
 * 
 * 通过excel模版和jxls标签，结合java bean数据，输出最终的excel文件.
 */
public class TestTemplate {

	@Test
	public void testGen() throws ParsePropertyException,
			InvalidFormatException, IOException {
        // 渲染的数据都放在这里
		Map<String, Object> beans = new HashMap<String, Object>();
		
		List<Department> departments = new ArrayList<Department>();
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(new Employee("nick", 27, 3000, 0.6));
		Department department = new Department("IT", new Employee("nick", 27, 3000, 0.6), employees);
		departments.add(department);
		beans.put("departments", departments);

		XLSTransformer transformer = new XLSTransformer();

		String dataXLS = "/jxls/basictags.xls";
		InputStream inputXML = new BufferedInputStream(getClass()
				.getResourceAsStream(dataXLS));

		Workbook workbook = transformer.transformXLS(inputXML, beans);

		String outFilePath = "C:/out_jxls.xls";
		OutputStream os = new BufferedOutputStream(new FileOutputStream(
				outFilePath));
		workbook.write(os);

		os.close();
	}

}
