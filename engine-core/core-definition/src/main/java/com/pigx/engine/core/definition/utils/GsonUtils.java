package com.pigx.engine.core.definition.utils;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/utils/GsonUtils.class */
public class GsonUtils {
    private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
    private static volatile Gson instance;

    static {
        GSON_BUILDER.enableComplexMapKeySerialization();
        GSON_BUILDER.serializeNulls();
        GSON_BUILDER.setDateFormat(SystemConstants.DATE_TIME_FORMAT);
        GSON_BUILDER.disableHtmlEscaping();
    }

    private GsonUtils() {
    }

    public static Gson getInstance() {
        if (ObjectUtils.isEmpty(instance)) {
            synchronized (GSON_BUILDER) {
                if (ObjectUtils.isEmpty(instance)) {
                    instance = GSON_BUILDER.create();
                }
            }
        }
        return instance;
    }

    public static JsonElement toJsonElement(String content) {
        return JsonParser.parseString(content);
    }

    public static JsonArray toJsonArray(String content) {
        return toJsonElement(content).getAsJsonArray();
    }

    public static JsonObject toJsonObject(String content) {
        return toJsonElement(content).getAsJsonObject();
    }

    public static <T> String toJson(T domain) {
        return getInstance().toJson(domain);
    }

    public static <T> T toObject(String str, Class<T> cls) {
        return (T) getInstance().fromJson(str, cls);
    }

    public static <T> T toObject(String str, Type type) {
        return (T) getInstance().fromJson(str, type);
    }

    public static <T> T toList(String str, Class<T> cls) {
        return (T) getInstance().fromJson(str, new TypeToken<List<T>>() { // from class: com.pigx.engine.core.definition.utils.GsonUtils.1
        }.getType());
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.pigx.engine.core.definition.utils.GsonUtils$2] */
    public static <T> List<Map<String, T>> toListMap(String content) {
        return (List) getInstance().fromJson(content, new TypeToken<List<Map<String, String>>>() { // from class: com.pigx.engine.core.definition.utils.GsonUtils.2
        }.getType());
    }

    public static <T> Map<String, T> toMaps(String str) {
        return (Map) getInstance().fromJson(str, new TypeToken<Map<String, T>>() { // from class: com.pigx.engine.core.definition.utils.GsonUtils.3
        }.getType());
    }
}
