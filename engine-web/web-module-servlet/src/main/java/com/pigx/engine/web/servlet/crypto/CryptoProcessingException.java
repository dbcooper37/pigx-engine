package com.pigx.engine.web.servlet.crypto;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.web.core.constant.WebErrorCodes;


/**
 * Exception thrown when cryptographic processing fails during HTTP request/response handling.
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
public class CryptoProcessingException extends PlatformRuntimeException {

    private final Feedback feedback;

    public CryptoProcessingException() {
        super();
        this.feedback = WebErrorCodes.CRYPTO_DECRYPT_FAILED;
    }

    public CryptoProcessingException(String message) {
        super(message);
        this.feedback = WebErrorCodes.CRYPTO_DECRYPT_FAILED;
    }

    public CryptoProcessingException(String message, Feedback feedback) {
        super(message);
        this.feedback = feedback;
    }

    public CryptoProcessingException(String message, Throwable cause) {
        super(message, cause);
        this.feedback = WebErrorCodes.CRYPTO_DECRYPT_FAILED;
    }

    public CryptoProcessingException(Throwable cause) {
        super(cause);
        this.feedback = WebErrorCodes.CRYPTO_DECRYPT_FAILED;
    }

    @Override
    public Feedback getFeedback() {
        return feedback;
    }
}
