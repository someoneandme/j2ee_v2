package com.pugwoo.practice.lucene.analyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 2011年1月
 * 
 * 分词在索引中非常关键，Analyzer每种语言都可以有实现
 */
public class TestAnalyzer {

	public static void main(String[] args) throws IOException {
		// 生成一个StandardAnalyzer对象
		Analyzer analyzer = new StandardAnalyzer();
		String text = "lighter javaeye com is the are on";

		List<String> tokens = getToken(text, analyzer);
		
		for(String token : tokens) {
			System.out.println(token);
		}
	}
	
	private static List<String> getToken(String text, Analyzer analyzer) throws IOException {
		List<String> result = new ArrayList<String>();
		
		StringReader reader = new StringReader(text);
		// 生成TokenStream对象 ，第一个参数FieldName暂时不清楚有什么用，和Document的field有关系吗？
		TokenStream stream = analyzer.tokenStream("", reader);
		CharTermAttribute termAttribute = stream.getAttribute(CharTermAttribute.class);
		
		stream.reset();
		while (stream.incrementToken()) {
			String token = termAttribute.toString();
			result.add(token);
		}

		stream.end();
		stream.close();
		
		return result;
	}

}
