package com.pigx.engine.core.definition.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/Pagination.class */
public interface Pagination extends BaseDomain {
    default <D extends BaseDomain> Map<String, Object> with(List<D> content, int totalPages, long totalElements) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("content", content);
        result.put("totalPages", Integer.valueOf(totalPages));
        result.put("totalElements", Long.valueOf(totalElements));
        return result;
    }

    default <D extends BaseDomain> Map<String, Object> with(List<D> content, boolean hasNext, boolean hasPrevious) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("content", content);
        result.put("hasNext", Boolean.valueOf(hasNext));
        result.put("hasPrevious", Boolean.valueOf(hasPrevious));
        return result;
    }
}
