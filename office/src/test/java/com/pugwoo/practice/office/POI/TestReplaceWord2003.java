package com.pugwoo.practice.office.POI;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

/**
 * 2014年12月17日 16:58:01
 * 
 * 经过测试，目前只适合2003的word的文件
 * 
 * http://stackoverflow.com/questions/22615594/replacing-variables-in-a-word-document-template-with-java
 * 
 */
public class TestReplaceWord2003 {

    public static void main(String[] args){
        String outputPath = "C:/output_2003.doc";
        
		String dataDOC = "/poi/a.doc";
		InputStream in = new BufferedInputStream(TestReplaceWord2007
				.class.getResourceAsStream(dataDOC));
        
        try {            
            HWPFDocument doc = new HWPFDocument(in);
            doc = replaceText(doc, "{XXXXX_NO}", "我的编号112233");
            saveWord(outputPath, doc);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private static HWPFDocument replaceText(HWPFDocument doc, String findText, String replaceText){
        Range r1 = doc.getRange();
        // 每一层都是可以替换的,另外一个程序就是这么做的
        r1.replaceText(findText, replaceText);

//        for (int i = 0; i < r1.numSections(); ++i ) { 
//            Section s = r1.getSection(i); 
//            for (int x = 0; x < s.numParagraphs(); x++) { 
//                Paragraph p = s.getParagraph(x);
//                for (int z = 0; z < p.numCharacterRuns(); z++) { 
//                    CharacterRun run = p.getCharacterRun(z); 
//                    String text = run.text();
//                    if(text.contains(findText)) {
//                        run.replaceText(findText, replaceText);
//                    } 
//                }
//            }
//        }
        
        return doc;
    }

    private static void saveWord(String filePath, HWPFDocument doc) throws FileNotFoundException, IOException{
        FileOutputStream out = null;
        try{
            out = new FileOutputStream(filePath);
            doc.write(out);
        }
        finally{
            out.close();
        }
    }
    
}
