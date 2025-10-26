package com.pigx.engine.core.definition.domain;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/ErrorCodeMapper.class */
public class ErrorCodeMapper {
    private static volatile ErrorCodeMapper instance;
    private final Map<Feedback, Integer> dictionary = new LinkedHashMap<Feedback, Integer>() { // from class: com.pigx.engine.core.definition.domain.ErrorCodeMapper.1
        {
            put(ErrorCodes.OK, Integer.valueOf(ErrorCodes.OK.getSequence()));
            put(ErrorCodes.NO_CONTENT, Integer.valueOf(ErrorCodes.NO_CONTENT.getSequence()));
        }
    };

    private ErrorCodeMapper() {
    }

    public static ErrorCodeMapper getInstance() {
        if (ObjectUtils.isEmpty(instance)) {
            synchronized (ErrorCodeMapper.class) {
                if (ObjectUtils.isEmpty(instance)) {
                    instance = new ErrorCodeMapper();
                }
            }
        }
        return instance;
    }

    private Integer getErrorCode(Feedback feedback) {
        return this.dictionary.get(feedback);
    }

    public void append(Map<Feedback, Integer> indexes) {
        if (MapUtils.isNotEmpty(indexes)) {
            this.dictionary.putAll(indexes);
        }
    }

    public static Integer get(Feedback feedback) {
        return getInstance().getErrorCode(feedback);
    }
}
