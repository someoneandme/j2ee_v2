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
		int hitsPerPage = 2; // 分页
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths
				.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();
		
		QueryParser parser = new QueryParser(field, analyzer);
		Query query = parser.parse(queryString);
		
		// 读取n条记录，默认score高的在前
		TopDocs results = searcher.search(query, hitsPerPage); 
		System.out.println(results.totalHits + " total matching documents");
		
		for(ScoreDoc hit : results.scoreDocs) {
			Document doc = searcher.doc(hit.doc); // 这里就读取到索引时的Document了
			System.out.println("score:" +  hit.score + "=" + doc);
		}
		
		// 查找下一页
		results = searcher.searchAfter(results.scoreDocs[results.scoreDocs.length - 1],
				query, hitsPerPage);
		for(ScoreDoc hit : results.scoreDocs) {
			Document doc = searcher.doc(hit.doc); // 这里就读取到索引时的Document了
			System.out.println("score:" +  hit.score + "=" + doc);
		}
		
		reader.close();
	}
	
	
}
