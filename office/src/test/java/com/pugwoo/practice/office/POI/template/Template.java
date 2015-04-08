package com.pugwoo.practice.office.POI.template;

import java.io.OutputStream;
import java.util.Map;

/**
 *         Date: 12/22/14
 *         Time: 11:07 AM
 */
public interface Template {

    String SOURCE_FORMAT = "SOURCE_FORMAT";
    String TARGET_FORMAT = "TARGET_FORMAT";
    String FONT_NAME = "FONT_NAME";
    String FONT_ENCODING = "FONT_ENCODING";
    String FONT_EMBEDDED = "FONT_EMBEDDED";

    void merge(Map<String, Object> context, OutputStream out) throws Exception;

}
