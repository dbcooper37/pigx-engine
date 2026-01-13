package com.pigx.engine.core.foundation.support;

import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.domain.Feedback;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * A container object that represents either a successful result or a failure.
 *
 * <p>This class provides a functional approach to error handling, avoiding the
 * use of exceptions for control flow and making error states explicit in the
 * return type.</p>
 *
 * <p><b>Usage Examples:</b></p>
 * <pre>{@code
 * // Successful result
 * Result<User> success = Result.success(user);
 * 
 * // Failure result
 * Result<User> failure = Result.failure("User not found", ErrorCodes.NOT_FOUND);
 * 
 * // Chaining operations
 * Result<String> name = userResult
 *     .map(User::getName)
 *     .orElse("Unknown");
 * 
 * // Pattern matching style
 * String message = result
 *     .fold(
 *         error -> "Error: " + error.getMessage(),
 *         user -> "Welcome, " + user.getName()
 *     );
 * }</pre>
 *
 * <p><b>Thread Safety:</b> This class is immutable and thread-safe.</p>
 *
 * @param <T> the type of the value contained in a successful result
 * @author PigX Engine Team
 * @since 1.0.0
 */
public final class Result<T> implements Serializable {

    private final T value;
    private final String errorMessage;
    private final Feedback errorCode;
    private final boolean success;

    private Result(T value, String errorMessage, Feedback errorCode, boolean success) {
        this.value = value;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.success = success;
    }

    /**
     * Creates a successful result containing the given value.
     *
     * @param value the value to wrap
     * @param <T>   the type of the value
     * @return a successful Result containing the value
     */
    public static <T> Result<T> success(T value) {
        return new Result<>(value, null, null, true);
    }

    /**
     * Creates a successful result with no value (for void operations).
     *
     * @param <T> the type parameter (typically Void)
     * @return a successful Result with null value
     */
    public static <T> Result<T> success() {
        return new Result<>(null, null, null, true);
    }

    /**
     * Creates a failure result with an error message.
     *
     * @param errorMessage the error message
     * @param <T>          the type parameter
     * @return a failure Result
     */
    public static <T> Result<T> failure(String errorMessage) {
        return new Result<>(null, errorMessage, null, false);
    }

    /**
     * Creates a failure result with an error code.
     *
     * @param errorCode the error code
     * @param <T>       the type parameter
     * @return a failure Result
     */
    public static <T> Result<T> failure(Feedback errorCode) {
        return new Result<>(null, errorCode.getMessage(), errorCode, false);
    }

    /**
     * Creates a failure result with both message and error code.
     *
     * @param errorMessage the error message
     * @param errorCode    the error code
     * @param <T>          the type parameter
     * @return a failure Result
     */
    public static <T> Result<T> failure(String errorMessage, Feedback errorCode) {
        return new Result<>(null, errorMessage, errorCode, false);
    }

    /**
     * Creates a failure result from an exception.
     *
     * @param exception the exception
     * @param <T>       the type parameter
     * @return a failure Result
     */
    public static <T> Result<T> failure(Throwable exception) {
        return new Result<>(null, exception.getMessage(), null, false);
    }

    /**
     * Wraps a supplier that may throw an exception.
     *
     * @param supplier the supplier to execute
     * @param <T>      the type of the result
     * @return a Result containing the value or the exception
     */
    public static <T> Result<T> of(Supplier<T> supplier) {
        try {
            return success(supplier.get());
        } catch (Exception e) {
            return failure(e);
        }
    }

    /**
     * Returns whether this result represents a success.
     *
     * @return true if successful
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns whether this result represents a failure.
     *
     * @return true if failed
     */
    public boolean isFailure() {
        return !success;
    }

    /**
     * Returns the value if successful, throws exception if failed.
     *
     * @return the value
     * @throws IllegalStateException if this is a failure result
     */
    public T get() {
        if (isFailure()) {
            throw new IllegalStateException("Cannot get value from failed Result: " + errorMessage);
        }
        return value;
    }

    /**
     * Returns the value if successful, or the given default if failed.
     *
     * @param defaultValue the default value
     * @return the value or default
     */
    public T orElse(T defaultValue) {
        return isSuccess() ? value : defaultValue;
    }

    /**
     * Returns the value if successful, or gets a default from the supplier.
     *
     * @param supplier the supplier for default value
     * @return the value or default from supplier
     */
    public T orElseGet(Supplier<T> supplier) {
        return isSuccess() ? value : supplier.get();
    }

    /**
     * Returns the value if successful, or throws the given exception.
     *
     * @param exceptionSupplier supplier for the exception to throw
     * @param <X>               the type of exception
     * @return the value if successful
     * @throws X if this is a failure result
     */
    public <X extends Throwable> T orElseThrow(Supplier<X> exceptionSupplier) throws X {
        if (isSuccess()) {
            return value;
        }
        throw exceptionSupplier.get();
    }

    /**
     * Transforms the value if successful.
     *
     * @param mapper the transformation function
     * @param <U>    the new type
     * @return a new Result with the transformed value, or the original failure
     */
    public <U> Result<U> map(Function<T, U> mapper) {
        if (isSuccess()) {
            return success(mapper.apply(value));
        }
        return failure(errorMessage, errorCode);
    }

    /**
     * Transforms the value to another Result if successful.
     *
     * @param mapper the transformation function
     * @param <U>    the new type
     * @return the transformed Result, or the original failure
     */
    public <U> Result<U> flatMap(Function<T, Result<U>> mapper) {
        if (isSuccess()) {
            return mapper.apply(value);
        }
        return failure(errorMessage, errorCode);
    }

    /**
     * Applies one of two functions depending on success or failure.
     *
     * @param failureMapper function to apply if failed
     * @param successMapper function to apply if successful
     * @param <U>           the result type
     * @return the result of applying the appropriate function
     */
    public <U> U fold(Function<Result<T>, U> failureMapper, Function<T, U> successMapper) {
        return isSuccess() ? successMapper.apply(value) : failureMapper.apply(this);
    }

    /**
     * Converts this Result to an Optional.
     *
     * @return Optional containing the value if successful, empty otherwise
     */
    public Optional<T> toOptional() {
        return isSuccess() ? Optional.ofNullable(value) : Optional.empty();
    }

    /**
     * Returns the error message if this is a failure result.
     *
     * @return the error message, or null if successful
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Returns the error code if this is a failure result.
     *
     * @return the error code, or null if successful
     */
    public Feedback getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result<?> result)) return false;
        return success == result.success &&
                Objects.equals(value, result.value) &&
                Objects.equals(errorMessage, result.errorMessage) &&
                Objects.equals(errorCode, result.errorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, errorMessage, errorCode, success);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("success", success)
                .add("value", value)
                .add("errorMessage", errorMessage)
                .add("errorCode", errorCode)
                .toString();
    }
}
