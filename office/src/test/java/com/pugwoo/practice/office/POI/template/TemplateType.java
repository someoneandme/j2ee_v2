package com.pugwoo.practice.office.POI.template;

import java.util.HashMap;
import java.util.Map;

/**
 *         Date: 12/22/14
 *         Time: 10:58 AM
 */
public enum TemplateType {
    PDF,
    EXCEL,
    WORD,
    WORD_PDF,
    UNKNOWN;

    protected static final Map<String, TemplateType> FORMAT_TO_TYPE = new HashMap<String, TemplateType>();
    static {
        FORMAT_TO_TYPE.put("pdf", PDF);
        FORMAT_TO_TYPE.put("doc", WORD);
        FORMAT_TO_TYPE.put("docx", WORD);
        FORMAT_TO_TYPE.put("word", WORD);
        FORMAT_TO_TYPE.put("xls", EXCEL);
        FORMAT_TO_TYPE.put("xlsx", EXCEL);
        FORMAT_TO_TYPE.put("word_pdf", WORD_PDF);
    }

    public static TemplateType getByFormat(String format) {
        TemplateType type = FORMAT_TO_TYPE.get(format);
        if (type != null) {
            return type;
        }
        return UNKNOWN;
    }
}
