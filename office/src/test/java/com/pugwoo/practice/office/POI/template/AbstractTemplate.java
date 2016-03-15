package com.pugwoo.practice.office.POI.template;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

/**
 * 抽象模版，只需要模版的输入流信息
 */
public abstract class AbstractTemplate implements Template {

    protected InputStream in;

    protected final Map<String, Object> options;

    public AbstractTemplate(InputStream in) {
        this.in = in;
        this.options = Collections.emptyMap();
    }

    public AbstractTemplate(InputStream in, Map<String, Object> options) {
        this.in = in;
        this.options = options;
    }
}
