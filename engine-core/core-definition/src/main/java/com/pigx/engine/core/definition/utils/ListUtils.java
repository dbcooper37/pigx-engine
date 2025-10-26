package com.pigx.engine.core.definition.utils;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/utils/ListUtils.class */
public class ListUtils {
    public static List<String> merge(List<String> appendResources, List<String> defaultResources) {
        if (CollectionUtils.isEmpty(appendResources)) {
            return defaultResources;
        }
        return CollectionUtils.collate(defaultResources, appendResources);
    }

    public static String[] toStringArray(List<String> resources) {
        if (CollectionUtils.isNotEmpty(resources)) {
            String[] result = new String[resources.size()];
            return (String[]) resources.toArray(result);
        }
        return new String[0];
    }
}
