package com.pigx.engine.core.foundation.enums;

import org.apache.commons.lang3.Strings;


public enum UrlCategory {

    /**
     * 含有通配符，含有 "*" 或 "?"
     */
    WILDCARD,
    /**
     * 含有占位符，含有 "{" 和 " } "
     */
    PLACEHOLDER,
    /**
     * 不含有任何特殊字符的完整路径
     */
    FULL_PATH;

    public static UrlCategory getCategory(String url) {

        if (Strings.CS.containsAny(url, new String[]{"*", "?"})) {
            return UrlCategory.WILDCARD;
        }

        if (Strings.CS.contains(url, "{")) {
            return UrlCategory.PLACEHOLDER;
        }

        return UrlCategory.FULL_PATH;
    }
}
