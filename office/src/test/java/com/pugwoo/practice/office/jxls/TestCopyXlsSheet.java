package com.pugwoo.practice.office.jxls;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 测试生存sheet并渲染
 * refer: http://jxls.sourceforge.net/reference/dynamicsheets.html
 */
public class TestCopyXlsSheet {

	public static void main(String[] args) throws ParsePropertyException, InvalidFormatException, IOException {
		
        String dataXLS = "/jxls/test.xlsx";
        InputStream inputXML = new BufferedInputStream(TestCopyXlsSheet.class
                .getResourceAsStream(dataXLS));
        
        XLSTransformer transformer = new XLSTransformer();
        
        //创建测试数据
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("name", "电视");
        map1.put("price", "3000");
        map1.put("desc", "3D电视机");
        map1.put("note", "中文测试");
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("name", "空调");
        map2.put("price", "2000");
        map2.put("desc", "变频空调");
        map1.put("note", "测试中文");
        list.add(map1);
        list.add(map2);

        ArrayList<Object> objects = new ArrayList<Object>();
        objects.add(list);
        objects.add(list);
        objects.add(list);
        objects.add(list);

        //sheet的名称
        List<String> listSheetNames = new ArrayList<String>();
        listSheetNames.add("1");
        listSheetNames.add("2");
        listSheetNames.add("3");
        listSheetNames.add("4");
        
        /**
         * listSheetNames是sheetName的列表，这个无疑问
         * objects和listSheetName都是list，个数一致
         * 
         * 特别说明: 这里参数的逻辑是这样的：
         * 第4个参数字符串命名的是objects每个元素在每个sheet中的名称
         */
        Workbook workbook = transformer.transformMultipleSheetsList(inputXML,
        		objects, listSheetNames, "list", new HashMap<String, Object>(), 0);
        workbook.write(new FileOutputStream("c:/blue.xlsx"));
        
	}
	
}
