package com.pigx.engine.oauth2.authorization.config;

import com.pigx.engine.oauth2.authorization.servlet.OAuth2SessionManagementConfigurerCustomer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;


@Configuration(proxyBeanMethods = false)
public class OAuth2ServletSessionConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2ServletSessionConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [OAuth2 Servlet Session Sharing] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public <S extends Session> SessionRegistry sessionRegistry(FindByIndexNameSessionRepository<S> sessionRepository) {
        SpringSessionBackedSessionRegistry<S> springSessionBackedSessionRegistry = new SpringSessionBackedSessionRegistry<>(sessionRepository);
        log.trace("[PIGXD] |- Bean [Spring Session Backed Session Registry] Configure.");
        return springSessionBackedSessionRegistry;
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2SessionManagementConfigurerCustomer sessionManagementConfigurerCustomer(SessionRegistry sessionRegistry) {
        return new OAuth2SessionManagementConfigurerCustomer(sessionRegistry);
    }

    /**
     * If a SessionRegistry @Bean is registered and is an instance of SessionRegistryImpl,
     * a HttpSessionEventPublisher @Bean SHOULD also be registered as it’s responsible for notifying SessionRegistryImpl of session lifecycle events, f
     * or example, SessionDestroyedEvent, to provide the ability to remove the SessionInformation instance.
     *
     * @return {@link HttpSessionEventPublisher}
     */
    @Bean
    @ConditionalOnMissingBean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
