package com.pigx.engine.oss.dialect.minio.definition.arguments;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.utils.DateTimeUtils;
import com.pigx.engine.oss.specification.arguments.base.ObjectConditionalReadArguments;
import io.minio.ObjectConditionalReadArgs;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;


public abstract class ArgumentsToObjectConditionalReadConverter<S extends ObjectConditionalReadArguments, T extends ObjectConditionalReadArgs, B extends ObjectConditionalReadArgs.Builder<B, T>> extends ArgumentsToObjectReadConverter<S, T, B> {

    @Override
    public void prepare(S arguments, B builder) {

        if (ObjectUtils.isNotEmpty(arguments.getLength()) && arguments.getLength() >= 0) {
            builder.length(arguments.getLength());
        }

        if (ObjectUtils.isNotEmpty(arguments.getOffset()) && arguments.getOffset() >= 0) {
            builder.offset(arguments.getOffset());
        }

        if (CollectionUtils.isNotEmpty(arguments.getMatchETag())) {
            builder.matchETag(StringUtils.join(arguments.getMatchETag(), SymbolConstants.COMMA));
        }

        if (CollectionUtils.isNotEmpty(arguments.getNotMatchEtag())) {
            builder.notMatchETag(StringUtils.join(arguments.getNotMatchEtag(), SymbolConstants.COMMA));
        }

        if (ObjectUtils.isNotEmpty(arguments.getModifiedSince())) {
            builder.modifiedSince(DateTimeUtils.dateToZonedDateTime(arguments.getModifiedSince()));
        }

        if (ObjectUtils.isNotEmpty(arguments.getUnmodifiedSince())) {
            builder.unmodifiedSince(DateTimeUtils.dateToZonedDateTime(arguments.getUnmodifiedSince()));
        }

        super.prepare(arguments, builder);
    }
}
