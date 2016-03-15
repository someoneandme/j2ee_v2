package com.pugwoo.practice.office.poi.template;

import java.io.InputStream;
import java.util.Map;

/**
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
