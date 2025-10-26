package com.pigx.engine.data.p6spy.jpa;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/* loaded from: data-module-hibernate-3.5.7.0.jar:cn/herodotus/engine/data/p6spy/P6SpyMessageFormatting.class */
public class P6SpyMessageFormatting implements MessageFormattingStrategy {
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        String str = this.format.format(new Date());
        String builder = str + " | took " + elapsed + "ms | " + str + " | connection " + category + " | url " + connectionId + "\n------------------------| " + url + ";";
        return StringUtils.isNotEmpty(sql.trim()) ? String.valueOf(builder) : SymbolConstants.BLANK;
    }
}
