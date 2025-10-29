package com.pigx.engine.core.definition.enums;


public enum Protocol {
    /**
     * 协议类型
     */
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
        return format;
    }

    public String getPrefix() {
        return prefix;
    }
}
