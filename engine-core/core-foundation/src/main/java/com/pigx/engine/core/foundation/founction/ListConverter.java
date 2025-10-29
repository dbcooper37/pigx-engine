package com.pigx.engine.core.foundation.founction;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.List;


@FunctionalInterface
public interface ListConverter<S, T> extends Converter<List<S>, List<T>> {

    @Override
    @Nullable
    default List<T> convert(List<S> source) {
        return source.stream()
                .map(this::from)
                .toList();
    }

    T from(S source);
}
