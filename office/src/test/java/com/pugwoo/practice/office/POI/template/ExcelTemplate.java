package com.pugwoo.practice.office.POI.template;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import com.google.common.io.Closeables;

/**
 * 类ExcelTemplate.java的实现描述：渲染excel模版<br>
 * 语法详见：http://jxls.sourceforge.net/samples/tagsample.html
 * 
 * @author boyu 2014年12月22日 下午2:05:46
 */
public class ExcelTemplate extends AbstractTemplate {

    public ExcelTemplate(InputStream in){
        super(in);
    }

    public ExcelTemplate(InputStream in, Map<String, Object> options){
        super(in, options);
    }

    @Override
    public void merge(Map<String, Object> context, OutputStream out) throws Exception {
        try {
            new XLSTransformer().transformXLS(in, context).write(out);
        } finally {
            Closeables.closeQuietly(in);
            Closeables.close(out, true);
        }
    }

}
