package com.pigx.engine.oauth2.authorization.matcher;

import com.pigx.engine.core.autoconfigure.oauth2.domain.HerodotusRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;


public interface HerodotusRequestMatcher extends RequestMatcher {

    /**
     * 判断策略所实施的规则是否与提供的请求相匹配
     *
     * @param request 自定义请求对象 {@link HerodotusRequest}
     * @return true 请求是否匹配，false 不匹配
     */
    boolean matches(HerodotusRequest request);

    /**
     * 判断策略所实施的规则是否与提供的请求相匹配
     *
     * @param request 自定义请求对象 {@link HerodotusRequest}
     * @return 匹配结果 {@link MatchResult}
     */
    default MatchResult matcher(HerodotusRequest request) {
        boolean match = matches(request);
        return match ? MatchResult.match() : MatchResult.notMatch();
    }
}
