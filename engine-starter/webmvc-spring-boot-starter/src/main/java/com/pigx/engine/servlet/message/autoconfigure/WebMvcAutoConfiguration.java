package com.pigx.engine.servlet.message.autoconfigure;

import com.pigx.engine.core.definition.constant.SecurityResources;
import com.pigx.engine.web.service.config.WebServiceConfiguration;
import com.pigx.engine.web.servlet.config.SecureConfiguration;
import com.pigx.engine.web.servlet.config.TemplateConfiguration;
import com.pigx.engine.web.servlet.config.TenantConfiguration;
import com.pigx.engine.web.servlet.secure.AccessLimitedInterceptor;
import com.pigx.engine.web.servlet.secure.IdempotentInterceptor;
import com.pigx.engine.web.servlet.tenant.MultiTenantInterceptor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.LiteWebJarsResourceResolver;


@AutoConfiguration
@Import({
        WebServiceConfiguration.class,
        SecureConfiguration.class,
        TenantConfiguration.class,
        TemplateConfiguration.class,
})
@EnableWebMvc
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebMvcAutoConfiguration.class);

    private final ObjectProvider<IdempotentInterceptor> idempotentInterceptor;
    private final ObjectProvider<AccessLimitedInterceptor> accessLimitedInterceptor;
    private final ObjectProvider<MultiTenantInterceptor> multiTenantInterceptor;

    public WebMvcAutoConfiguration(ObjectProvider<IdempotentInterceptor> idempotentInterceptor, ObjectProvider<AccessLimitedInterceptor> accessLimitedInterceptor, ObjectProvider<MultiTenantInterceptor> multiTenantInterceptor) {
        this.idempotentInterceptor = idempotentInterceptor;
        this.accessLimitedInterceptor = accessLimitedInterceptor;
        this.multiTenantInterceptor = multiTenantInterceptor;
    }


    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Starter [Web Servlet] Configure.");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        accessLimitedInterceptor.ifAvailable(registry::addInterceptor);
        idempotentInterceptor.ifAvailable(registry::addInterceptor);
        multiTenantInterceptor.ifAvailable(registry::addInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(SecurityResources.MATCHER_STATIC).addResourceLocations("classpath:/static/");
        registry.addResourceHandler(SecurityResources.MATCHER_WEBJARS)
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .resourceChain(false)
                .addResolver(new LiteWebJarsResourceResolver());
    }
}
