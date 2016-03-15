package com.pugwoo.practice.office.POI.template;

import com.google.common.io.Closeables;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 */
public class WordPdfTemplate extends WordTemplate {

    public WordPdfTemplate(InputStream in) {
        super(in);
    }

    public WordPdfTemplate(InputStream in, Map<String, Object> options) {
        super(in, options);
    }

    @Override
    public void merge(Map<String, Object> context, OutputStream out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            doMerge(context, baos);
            LOGGER.info("doc size is {}.", baos.size());
            XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(baos.toByteArray()));
            //PdfOptions options = PDFViaITextOptions.create().fontEncoding( "windows-1250" );
            PdfConverter.getInstance().convert(document, out, null);
        } finally {
            Closeables.closeQuietly(in);
            Closeables.close(out, true);
        }
    }
}
