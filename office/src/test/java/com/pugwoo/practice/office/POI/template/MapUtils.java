package com.pugwoo.practice.office.POI.template;


import com.google.common.base.Function;

import java.util.Map;

/**
 *         Date: 1/5/15
 *         Time: 3:07 PM
 */
public class MapUtils {

    public static Boolean getBoolean(final Map<String, Object> context, final String key) {
        return getBoolean(context, key, null);
    }

    public static Boolean getBoolean(final Map<String, Object> context, final String key, Boolean defaultValue) {
        Object answer = getValue(context, key, Object.class);
        if (answer != null) {
            if (answer instanceof Boolean) {
                return (Boolean) answer;

            } else if (answer instanceof String) {
                return Boolean.valueOf((String)answer);

            } else if (answer instanceof Number) {
                Number n = (Number) answer;
                return (n.intValue() != 0) ? Boolean.TRUE : Boolean.FALSE;
            }
        }
        return defaultValue;
    }

    public static Double getDouble(Map<String, Object> context, String key) {
        return getNumber(context, key, NUMBER2DOUBLE);
    }

    public static Integer getInteger(Map<String, Object> context, String key) {
        return getNumber(context, key, NUMBER2INTEGER);
    }

    public static Integer getInteger(Map<String, Object> context, String key, int defaultValue) {
        return getValue(context, key, Number.class, defaultValue).intValue();
    }

    public static Long getLong(Map<String, Object> context, String key) {
        return getNumber(context, key, NUMBER2LONG);
    }

    public static Long getLong(Map<String, Object> context, String key, long defaultValue) {
        return getValue(context, key, Number.class, defaultValue).longValue();
    }

    private static <T extends Number> T getNumber(Map<String, Object> context, String key, Function<Number, T> transformer) {
        Number number = getValue(context, key, Number.class);
        if (number != null) {
            return transformer.apply(number);
        } else {
            return null;
        }
    }

    public static String getAsString(Map<String, Object> context, String key) {
        if (context != null) {
            Object obj = context.get(key);
            if (obj != null) {
                return obj.toString();
            }
        }
        return null;
    }

    public static String getString(Map<String, Object> context, String key) {
        return getValue(context, key, String.class);
    }

    public static <T> T getValue(Map<String, Object> context, String key, Class<T> targetClass) {
        return getValue(context, key, targetClass, null);
    }

    public static <T> T getValue(Map<String, Object> context, String key, Class<T> targetClass, T defaultValue) {
        if (context != null) {
            Object value = context.get(key);
            if (value != null && targetClass != null) {
                if (targetClass.isAssignableFrom(value.getClass())) {
                    return targetClass.cast(value);
                }
            }
        }
        return defaultValue;
    }

    public static final Function<Number, Long> NUMBER2LONG = new Function<Number, Long>() {
        @Override public Long apply(Number number) {
            return number.longValue();
        }
    };

    public static final Function<Number, Integer> NUMBER2INTEGER = new Function<Number, Integer>() {
        @Override public Integer apply(Number number) {
            return number.intValue();
        }
    };

    public static final Function<Number, Double> NUMBER2DOUBLE= new Function<Number, Double>() {
        @Override public Double apply(Number number) {
            return number.doubleValue();
        }
    };

}
