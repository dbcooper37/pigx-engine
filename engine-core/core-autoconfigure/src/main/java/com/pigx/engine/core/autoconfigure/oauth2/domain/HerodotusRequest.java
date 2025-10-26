package com.pigx.engine.autoconfigure.oauth2.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/domain/HerodotusRequest.class */
public final class HerodotusRequest implements Serializable {
    private String pattern;
    private String httpMethod;

    public HerodotusRequest() {
    }

    public HerodotusRequest(String pattern) {
        this(pattern, null);
    }

    public HerodotusRequest(String pattern, String httpMethod) {
        Assert.hasText(pattern, "Pattern cannot be null or empty");
        this.pattern = pattern;
        this.httpMethod = checkHttpMethod(httpMethod);
    }

    public String getPattern() {
        return this.pattern;
    }

    public String getHttpMethod() {
        return this.httpMethod;
    }

    private String checkHttpMethod(String method) {
        if (StringUtils.isNotBlank(method)) {
            HttpMethod httpMethod = HttpMethod.valueOf(method);
            if (ObjectUtils.isNotEmpty(httpMethod)) {
                return httpMethod.name();
            }
            return null;
        }
        return null;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusRequest that = (HerodotusRequest) o;
        return Objects.equal(this.pattern, that.pattern) && Objects.equal(this.httpMethod, that.httpMethod);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.pattern, this.httpMethod});
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("pattern", this.pattern).add("httpMethod", this.httpMethod).toString();
    }
}
