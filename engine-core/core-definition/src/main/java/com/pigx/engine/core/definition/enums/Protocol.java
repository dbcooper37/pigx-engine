package com.pigx.engine.core.definition.enums;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/enums/Protocol.class */
public enum Protocol {
    HTTP("http://", "http"),
    HTTPS("https://", "https"),
    REDIS("redis://", "redis"),
    REDISS("rediss://", "rediss");

    private final String format;
    private final String prefix;

    Protocol(String format, String prefix) {
        this.format = format;
        this.prefix = prefix;
    }

    public String getFormat() {
        return this.format;
    }

    public String getPrefix() {
        return this.prefix;
    }
}
