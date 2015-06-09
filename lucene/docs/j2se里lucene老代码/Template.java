package basic;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 该类演示Lucene的一般步骤
 */
public class Template {
	
	public static void main(String[] args) {

		//创建一个Analyzer，如果中文则用中文的
		//StandardAnalyzer的参数Version指示了默认的top_words，即一些普通没意义的英文单词，如of
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_29);
		
		//创建一个Directory，用于存放Index文件
		//如要要把Index放到磁盘上，则用
		Directory directory = null;
		try {
			directory = FSDirectory.open(new File("lucene_index"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Directory directory = new RAMDirectory();
		
		//创建IndexWriter
		//第三个参数true表示删除index_dir中文就，重新建立，否则是在它的基础上添加
		//Field的最大长度默认是10000
		IndexWriter indexWriter = null;
		try {
			indexWriter = new IndexWriter(directory, analyzer, true, new IndexWriter.MaxFieldLength(10000));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//创建一个Document
		Document doc = new Document();
		//创建若干个Field
		Field field1 = new Field("title", "The Three Pigs", Field.Store.YES, Field.Index.ANALYZED);
		Field field2 = new Field("content", "The first little pig built his house out of straw because it was the easiest thing to do.",
				Field.Store.YES, Field.Index.ANALYZED);
		doc.add(field1);
		doc.add(field2);
		
		//将包含Field的Document加入到索引中，可以再创建多几个Document并添加进去
		try {
			indexWriter.addDocument(doc);
			//优化
			indexWriter.optimize();
			//关闭
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		//接下来是查询
		
		//首先创建一个IndexSeacher对象，必须提供index存放位置对象directory
		//后面的true告知是只读read-only
		IndexSearcher indexSearcher = null;
		try {
			indexSearcher = new IndexSearcher(directory, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//建立一个QueryPaser，其中第二参数是field名称，第三个是Analyzer对象
		QueryParser queryParser = new QueryParser(Version.LUCENE_29, "content", analyzer);
		//建立Query对象，给它要查询的单词，注意有各种各样的Query
		Query query = null;
		try {
			query = queryParser.parse("pig");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//获得查询结果,取得前100条结果
		TopScoreDocCollector collector = TopScoreDocCollector.create(100, false);
		try {
			indexSearcher.search(query, collector);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		
		//查看匹配个数
		System.out.println(hits.length);
		for(int i=0; i<hits.length; i++){
			Document document = null;
			try {
				document = indexSearcher.doc(hits[i].doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(document.getField("content"));
		}
		
		//查看所有匹配的个数，注意前面只取出了前100个
		System.out.println("Total hits: " + collector.getTotalHits());
		
	}
	

}
