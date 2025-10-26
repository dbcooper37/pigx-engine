package com.pigx.engine.core.foundation.founction;

import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@FunctionalInterface
/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/founction/ListConverter.class */
public interface ListConverter<S, T> extends Converter<List<S>, List<T>> {
    T from(S source);

    @Nullable
    default List<T> convert(List<S> source) {
        return source.stream().map(this::from).toList();
    }
}
