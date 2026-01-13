package com.pigx.engine.web.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.web.core.constant.WebErrorCodes;


/**
 * Exception thrown when cryptographic operations fail.
 * <p>
 * This exception is used for encryption/decryption failures in the HTTP crypto layer,
 * providing proper error feedback to clients instead of silently returning unprocessed data.
 * </p>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
public class CryptoException extends PlatformRuntimeException {

    private final Feedback feedback;

    public CryptoException() {
        super();
        this.feedback = WebErrorCodes.CRYPTO_DECRYPT_FAILED;
    }

    public CryptoException(String message) {
        super(message);
        this.feedback = WebErrorCodes.CRYPTO_DECRYPT_FAILED;
    }

    public CryptoException(String message, Feedback feedback) {
        super(message);
        this.feedback = feedback;
    }

    public CryptoException(String message, Throwable cause) {
        super(message, cause);
        this.feedback = WebErrorCodes.CRYPTO_DECRYPT_FAILED;
    }

    public CryptoException(String message, Throwable cause, Feedback feedback) {
        super(message, cause);
        this.feedback = feedback;
    }

    public CryptoException(Throwable cause) {
        super(cause);
        this.feedback = WebErrorCodes.CRYPTO_DECRYPT_FAILED;
    }

    public CryptoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.feedback = WebErrorCodes.CRYPTO_DECRYPT_FAILED;
    }

    @Override
    public Feedback getFeedback() {
        return feedback;
    }
}
