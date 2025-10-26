package com.pigx.engine.core.definition.constant;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/constant/RegexPool.class */
public interface RegexPool extends cn.hutool.v7.core.regex.RegexPool {
    public static final String BRACES_AND_CONTENT = "\\{([^}])*\\}";
    public static final String ALL_CHARACTERS = "(?!^)";
    public static final String SINGLE_QUOTE_STRING_EQUATION = "(\\w+)\\s*=\\s*'(.*?)'";
    public static final String DNS_COMPATIBLE = "^[a-z0-9][a-z0-9\\.\\-]+[a-z0-9]$";
}
