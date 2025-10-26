package com.pigx.engine.core.foundation.enums;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import org.apache.commons.lang3.Strings;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/enums/UrlCategory.class */
public enum UrlCategory {
    WILDCARD,
    PLACEHOLDER,
    FULL_PATH;

    public static UrlCategory getCategory(String url) {
        if (Strings.CS.containsAny(url, new String[]{SymbolConstants.STAR, SymbolConstants.QUESTION})) {
            return WILDCARD;
        }
        if (Strings.CS.contains(url, SymbolConstants.OPEN_CURLY_BRACE)) {
            return PLACEHOLDER;
        }
        return FULL_PATH;
    }
}
