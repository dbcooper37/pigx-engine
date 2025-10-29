package com.pigx.engine.oss.solution.proxy;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.oss.solution.constants.OssSolutionConstants;
import com.pigx.engine.oss.solution.properties.OssProxyProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;


public class OssProxyAddressFunction implements Function<HttpServletRequest, String> {

    private static final Logger log = LoggerFactory.getLogger(OssProxyAddressFunction.class);

    private final OssProxyProperties ossProxyProperties;

    public OssProxyAddressFunction(OssProxyProperties ossProxyProperties) {
        this.ossProxyProperties = ossProxyProperties;
    }

    @Override
    public String apply(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String path = uri.replace(OssSolutionConstants.PRESIGNED_OBJECT_URL_PROXY, SymbolConstants.BLANK);

        String queryString = request.getQueryString();
        String params = queryString != null ? SymbolConstants.QUESTION + queryString : SymbolConstants.BLANK;

        String target = ossProxyProperties.getDestination() + path + params;
        log.debug("[Herodotus] |- Convert request [{}] to [{}].", uri, target);
        return target;
    }
}
