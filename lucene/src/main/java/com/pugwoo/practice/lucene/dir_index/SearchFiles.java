package com.pugwoo.practice.lucene.dir_index;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/**
 * 2015年6月8日 23:33:04
 */
public class SearchFiles {

	public static void main(String[] args) throws IOException, ParseException {
		String index = "C:/lucene/index";  // 索引的位置
		
		// 查询参数
		String field = "contents"; // 查询的域field
		String queryString = "hello"; // 要查询的关键词
		String queries = null; // 要查询的文件名?
		boolean raw = false;
		int hitsPerPage = 10;
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths
				.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();
		
		QueryParser parser = new QueryParser(field, analyzer);
		Query query = parser.parse(queryString);
		
		TopDocs results = searcher.search(query, hitsPerPage); // 这里只查第一页
		System.out.println(results.totalHits + " total matching documents");
		
		ScoreDoc[] hits = results.scoreDocs;
		for(ScoreDoc hit : hits) {
			Document doc = searcher.doc(hit.doc); // 这里就读取到索引时的Document了
			System.out.println(doc);
		}
		
		reader.close();
	}
	
	
}
