package com.pigx.engine.oauth2.authentication.response;

import com.pigx.engine.core.autoconfigure.oauth2.definition.SecurityGlobalExceptionHandler;
import com.pigx.engine.oauth2.authentication.utils.OAuth2EndpointUtils;
import com.pigx.engine.web.core.servlet.template.AbstractResponseHandler;
import com.pigx.engine.web.core.servlet.template.ThymeleafTemplateHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2ErrorHttpMessageConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.MultiValueMap;

import java.io.IOException;


public class OAuth2AuthenticationFailureHandler extends AbstractResponseHandler implements AuthenticationFailureHandler {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthenticationFailureHandler.class);

    private final HttpMessageConverter<OAuth2Error> errorHttpMessageConverter = new OAuth2ErrorHttpMessageConverter();

    public OAuth2AuthenticationFailureHandler(ThymeleafTemplateHandler templateHandler) {
        super(templateHandler);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.warn("[PIGXD] |- Authentication operation failure!");

        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);
        String deviceCode = parameters.getFirst(OAuth2ParameterNames.DEVICE_CODE);
        // 兼容 Device Grant 错误处理
        // Device Grant 需要 SAS 原始出错信息，如果采用原有 SecurityGlobalExceptionHandler 处理方式，将导致前端获取到错误的错误信息
        if (exception instanceof OAuth2AuthenticationException oauth2Exception && StringUtils.isNotBlank(deviceCode)) {
            OAuth2Error error = oauth2Exception.getError();
            ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
            httpResponse.setStatusCode(HttpStatus.BAD_REQUEST);
            this.errorHttpMessageConverter.write(error, MediaType.APPLICATION_JSON, httpResponse);
        } else {
            process(request, response, () -> SecurityGlobalExceptionHandler.resolveSecurityException(exception, request.getRequestURI()));
        }
    }
}
