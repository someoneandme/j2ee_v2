package com.pugwoo.practice.office.POI.template;

import java.io.InputStream;
import java.util.Map;

/**
 *         Date: 1/5/15
 *         Time: 2:55 PM
 */
public abstract class TemplateUtils {
    private TemplateUtils(){}

    public static Template createTemplate(String format, InputStream in, Map<String, Object> options) {
        TemplateType type = TemplateType.getByFormat(format);
        Template template;
        switch (type) {
            case EXCEL:
                template = new ExcelTemplate(in, options);
                break;
            case WORD:
                template = new WordTemplate(in, options);
                break;
            case WORD_PDF:
                template = new WordPdfTemplate(in, options);
                break;
            default:
                throw new IllegalArgumentException("Unknown template type for " + format);
        }
        return template;
    }
}
