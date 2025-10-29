package com.pigx.engine.oss.dialect.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;


public class OssXmlParserException extends PlatformRuntimeException {

    public OssXmlParserException() {
        super();
    }

    public OssXmlParserException(String message) {
        super(message);
    }

    public OssXmlParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssXmlParserException(Throwable cause) {
        super(cause);
    }

    protected OssXmlParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return OssErrorCodes.OSS_XML_PARSER;
    }
}
