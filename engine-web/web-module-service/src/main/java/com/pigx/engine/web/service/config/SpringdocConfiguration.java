package com.pigx.engine.web.service.config;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.web.core.condition.ConditionalOnSwaggerEnabled;
import com.pigx.engine.web.core.definition.OpenApiServerResolver;
import com.google.common.collect.ImmutableList;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnSwaggerEnabled
@Configuration(proxyBeanMethods = false)
@SecuritySchemes({@SecurityScheme(name = SystemConstants.OPEN_API_SECURITY_SCHEME_BEARER_NAME, type = SecuritySchemeType.OAUTH2, bearerFormat = "JWT", scheme = "bearer", flows = @OAuthFlows(password = @OAuthFlow(authorizationUrl = "${herodotus.endpoint.authorization-uri}", tokenUrl = "${herodotus.endpoint.access-token-uri}", refreshUrl = "${herodotus.endpoint.access-token-uri}", scopes = {@OAuthScope(name = "all")}), clientCredentials = @OAuthFlow(authorizationUrl = "${herodotus.endpoint.authorization-uri}", tokenUrl = "${herodotus.endpoint.access-token-uri}", refreshUrl = "${herodotus.endpoint.access-token-uri}", scopes = {@OAuthScope(name = "all")})))})
/* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/config/SpringdocConfiguration.class */
public class SpringdocConfiguration {
    private static final Logger log = LoggerFactory.getLogger(SpringdocConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Open Api] Configure.");
    }

    @ConditionalOnMissingBean
    @Bean
    public OpenApiServerResolver openApiServerResolver() {
        OpenApiServerResolver resolver = () -> {
            Server server = new Server();
            server.setUrl(ServiceContextHolder.getUrl());
            return ImmutableList.of(server);
        };
        log.trace("[Herodotus] |- Bean [Open Api Server Resolver] Configure.");
        return resolver;
    }

    @ConditionalOnMissingBean
    @Bean
    public OpenAPI createOpenApi(OpenApiServerResolver openApiServerResolver) {
        return new OpenAPI().servers(openApiServerResolver.getServers()).info(new Info().title("Herodotus Cloud").description("Herodotus Cloud Microservices Architecture").version("Swagger V3").license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/"))).externalDocs(new ExternalDocumentation().description("Herodotus Cloud Documentation").url(SystemConstants.WEBSITE));
    }
}
