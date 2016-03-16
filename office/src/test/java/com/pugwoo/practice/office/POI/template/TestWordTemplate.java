package com.pugwoo.practice.office.POI.template;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pugwoo.practice.office.poi.template.Template;
import com.pugwoo.practice.office.poi.template.WordTemplate;

public class TestWordTemplate {

	public static void main(String[] args) throws Exception {
		String dataDOC = "/poi/test_template.docx";
		InputStream in = new BufferedInputStream(
				TestWordTemplate.class.getResourceAsStream(dataDOC));
		
		Template wordTemplate = new WordTemplate(in);
		
		OutputStream out = new FileOutputStream("c:/test_template_output.docx");
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("name", "nick");
		// context里的List的元素必须是Map<String, Object>
		List<Map<String, Object>> scores = new ArrayList<Map<String,Object>>();
		context.put("scores", scores);
		
		Map<String, Object> score1 = new HashMap<String, Object>();
		score1.put("name", "数学");
		score1.put("score", 99);
		scores.add(score1);
		
		Map<String, Object> score2 = new HashMap<String, Object>();
		score2.put("name", "语文");
		score2.put("score", 66);
		scores.add(score2);
		
		wordTemplate.merge(context, out);
	}
	
}
