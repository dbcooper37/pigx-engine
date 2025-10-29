package com.pigx.engine.core.definition.utils;


public class NumberUtils {

    /**
     * long 转 int
     *
     * @param value long 型数值
     * @return int 型数值
     */
    public static int longToInt(long value) {
        return Long.valueOf(value).intValue();
    }
}
