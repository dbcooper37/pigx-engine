package com.pigx.engine.web.core.validation;

import com.pigx.engine.web.core.annotation.EnumeratedValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/validation/EnumeratedValueValidator.class */
public class EnumeratedValueValidator implements ConstraintValidator<EnumeratedValue, Object> {
    private String[] names;
    private int[] ordinals;

    public void initialize(EnumeratedValue constraintAnnotation) {
        this.names = constraintAnnotation.names();
        this.ordinals = constraintAnnotation.ordinals();
    }

    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value instanceof String) {
            for (String name : this.names) {
                if (name.equals(value)) {
                    return true;
                }
            }
            return false;
        }
        if (value instanceof Integer) {
            for (int ordinal : this.ordinals) {
                if (ordinal == ((Integer) value).intValue()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
