package com.pugwoo.practice.lucene.index_in_mem;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * 2015年6月9日 11:47:11
 * 使用RAMDirectory
 * 索引存放在内存中，非常方便小测试，特别是做成页面的方式调试
 */
public class Classroom {

	public static void main(String[] args) throws Exception{
		
		Analyzer analyzer = new StandardAnalyzer();
		Directory directory = new RAMDirectory();
		
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		iwc.setOpenMode(OpenMode.CREATE);
		// iwc.setRAMBufferSizeMB(256.0); // 大量索引时可提高内存用量
		IndexWriter indexWriter = new IndexWriter(directory, iwc);
		
		indexWriter.addDocument(getActivity("23", "上课:计算机网络",
				"教师:吴红,班级:数学与计算科学学院信息与计算科学2006级B班"));
		indexWriter.addDocument(getActivity("26", "讲座:计算机概念设计",
		        "演讲:王五,主办方:数学与计算科学学院团委"));
		
		indexWriter.close();
		
		//索引
		query(directory, analyzer, "吴红");

	}
	
	private static Document getActivity(String id, String name, String user){
		Document document = new Document();
		Field f_id = new Field("id",id,Field.Store.YES, Field.Index.NOT_ANALYZED);
		Field f_name = new Field("name", name, Field.Store.YES, Field.Index.ANALYZED);
		Field f_user = new Field("user", user, Field.Store.YES, Field.Index.ANALYZED);
		
		document.add(f_id);
		document.add(f_name);
		document.add(f_user);
		
		return document;
	}
	
	private static void query(Directory directory, Analyzer analyzer, String queryString)
	        throws Exception{
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(reader);
		
		QueryParser queryParser = new QueryParser("user", analyzer);
		Query query = queryParser.parse(queryString);
		
		// 读取n条记录，默认score高的在前
		TopDocs results = indexSearcher.search(query, 100); 
		System.out.println(results.totalHits + " total matching documents");
		
		for(ScoreDoc hit : results.scoreDocs) {
			Document doc = indexSearcher.doc(hit.doc); // 这里就读取到索引时的Document了
			System.out.println("score:" +  hit.score + "=" + doc);
		}
		
	}

}
