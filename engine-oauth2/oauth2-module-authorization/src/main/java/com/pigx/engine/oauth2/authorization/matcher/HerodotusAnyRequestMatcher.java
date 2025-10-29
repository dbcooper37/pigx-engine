package com.pigx.engine.oauth2.authorization.matcher;

import com.pigx.engine.core.autoconfigure.oauth2.domain.HerodotusRequest;
import jakarta.servlet.http.HttpServletRequest;


public class HerodotusAnyRequestMatcher implements HerodotusRequestMatcher {

    public static final HerodotusRequestMatcher INSTANCE = new HerodotusAnyRequestMatcher();

    private HerodotusAnyRequestMatcher() {
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return true;
    }

    @Override
    public boolean matches(HerodotusRequest request) {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof HerodotusAnyRequestMatcher;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "any request";
    }


}
