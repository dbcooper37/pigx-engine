package com.pigx.engine.oss.dialect.core.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;


public class ConverterUtils {

    public static <T, R> List<R> toDomains(List<T> items, Converter<T, R> toDomain) {
        if (CollectionUtils.isNotEmpty(items)) {
            return items.stream().map(toDomain::convert).toList();
        }
        return new ArrayList<>();
    }

    public static <T, R> R toDomain(T object, Converter<T, R> toDomain) {
        return toDomain.convert(object);
    }
}
