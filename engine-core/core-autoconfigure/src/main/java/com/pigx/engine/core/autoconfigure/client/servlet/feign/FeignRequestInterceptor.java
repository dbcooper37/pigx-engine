package com.pigx.engine.autoconfigure.client.servlet.feign;

import com.pigx.engine.core.definition.constant.HerodotusHeaders;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.foundation.context.TenantContextHolder;
import cn.hutool.v7.http.server.servlet.ServletUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/client/servlet/feign/FeignRequestInterceptor.class */
public class FeignRequestInterceptor implements RequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(FeignRequestInterceptor.class);

    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        if (httpServletRequest != null) {
            Map<String, String> headers = ServletUtil.getHeaderMap(httpServletRequest);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!Strings.CI.equals(key, "Content-Length")) {
                    if (Strings.CI.equals(key, "Content-Type")) {
                        Map<String, Collection<String>> requestHeaders = requestTemplate.headers();
                        if (requestHeaders.containsKey("Content-Type")) {
                        }
                    }
                    if (Strings.CI.equals(key, "User-Agent")) {
                        value = Strings.CS.replace(value, SymbolConstants.NEW_LINE, SymbolConstants.BLANK);
                        entry.setValue(value);
                    }
                    if (Strings.CI.equals(key, "Sec-CH-UA")) {
                        value = Strings.CS.replace(value, SymbolConstants.NEW_LINE, SymbolConstants.BLANK);
                        entry.setValue(value);
                    }
                    requestTemplate.header(key, new String[]{value});
                }
            }
            log.debug("[Herodotus] |- Feign Request Interceptor copy all need transfer header!");
            if (!headers.containsKey(HerodotusHeaders.X_HERODOTUS_TENANT_ID)) {
                String tenantId = TenantContextHolder.getTenantId();
                if (StringUtils.isNotBlank(tenantId)) {
                    log.info("[Herodotus] |- Feign Request Interceptor Tenant is : {}", tenantId);
                    requestTemplate.header(HerodotusHeaders.X_HERODOTUS_TENANT_ID, new String[]{tenantId});
                }
            }
            if (headers.containsKey("X-Request-ID")) {
                String traceId = headers.get("X-Request-ID");
                MDC.put("traceId", traceId);
                log.info("[Herodotus] |- Feign Request Interceptor Trace: {}", traceId);
            }
        }
        log.trace("[Herodotus] |- Feign Request Interceptor [{}]", requestTemplate.toString());
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return RequestContextHolder.getRequestAttributes().getRequest();
        } catch (Exception e) {
            log.error("[Herodotus] |- Feign Request Interceptor can not get Request.");
            return null;
        }
    }
}
