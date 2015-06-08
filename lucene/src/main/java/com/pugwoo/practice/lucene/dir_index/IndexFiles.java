package com.pugwoo.practice.lucene.dir_index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * 2015年6月8日 17:46:39
 * 索引文件夹里所有的文件
 * 
 * 将指定文档的文件夹 索引到 index目录
 */
public class IndexFiles {

	public static void main(String[] args) throws IOException {
		
		String indexPath = "C:/lucene/index"; // 索引存放的位置
		String docsPath = "C:/lucene/document"; // 要索引的文档位置
		boolean create = true; // true为新建index，false为更新index
		
		// 1. 准备好Directory索引目录，Analyzer分词器，IndexWriter索引写入
		Directory dir = FSDirectory.open(Paths.get(indexPath));
		Analyzer analyzer = new StandardAnalyzer();
		
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		iwc.setOpenMode(create ? OpenMode.CREATE : OpenMode.CREATE_OR_APPEND);
		// iwc.setRAMBufferSizeMB(256.0); // 大量索引时可提高内存用量
		IndexWriter writer = new IndexWriter(dir, iwc);
		
		indexDocs(writer, new File(docsPath));
		
		// writer.forceMerge(1); // 当你全部索引都完成之后，可以调这个优化索引，但这个方法消耗很大
		writer.close();
	}
	
	private static void indexDocs(final IndexWriter writer, File docsFile) throws IOException {
		if(docsFile.isDirectory()) {
			for(File file : docsFile.listFiles()) {
				indexDocs(writer, file);
			}
		} else {
			Document doc = new Document();
			Field pathField = new StringField("path", docsFile.getPath(),
					Field.Store.YES); // Store.YES不索引，仅存储文件path
			doc.add(pathField);
			
			doc.add(new LongField("modified", docsFile.lastModified(),
					Field.Store.NO)); // LongField默认会索引，索引的就不Store了
			
			doc.add(new TextField("contents", new BufferedReader(new
					FileReader(docsFile))));
			
			if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
				writer.addDocument(doc);
			} else {
				writer.updateDocument(new Term("path", docsFile.getPath()),
						doc); // 指定path，是为了代替掉该path已存在的索引
				// 注意这个path和上面doc的path是一致的
			}
		}
	}
}
