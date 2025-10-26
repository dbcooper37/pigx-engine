package com.pigx.engine.oauth2.authentication.utils;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.util.Map;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/utils/OAuth2ConfigurerUtils.class */
public final class OAuth2ConfigurerUtils {
    private OAuth2ConfigurerUtils() {
    }

    public static String withMultipleIssuersPattern(String endpointUri) {
        Assert.hasText(endpointUri, "endpointUri cannot be empty");
        return endpointUri.startsWith("/") ? "/**" + endpointUri : "/**/" + endpointUri;
    }

    public static RegisteredClientRepository getRegisteredClientRepository(HttpSecurity httpSecurity) {
        RegisteredClientRepository registeredClientRepository = (RegisteredClientRepository) httpSecurity.getSharedObject(RegisteredClientRepository.class);
        if (registeredClientRepository == null) {
            registeredClientRepository = (RegisteredClientRepository) getBean(httpSecurity, RegisteredClientRepository.class);
            httpSecurity.setSharedObject(RegisteredClientRepository.class, registeredClientRepository);
        }
        return registeredClientRepository;
    }

    public static OAuth2AuthorizationService getAuthorizationService(HttpSecurity httpSecurity) {
        InMemoryOAuth2AuthorizationService inMemoryOAuth2AuthorizationService = (OAuth2AuthorizationService) httpSecurity.getSharedObject(OAuth2AuthorizationService.class);
        if (inMemoryOAuth2AuthorizationService == null) {
            inMemoryOAuth2AuthorizationService = (OAuth2AuthorizationService) getOptionalBean(httpSecurity, OAuth2AuthorizationService.class);
            if (inMemoryOAuth2AuthorizationService == null) {
                inMemoryOAuth2AuthorizationService = new InMemoryOAuth2AuthorizationService();
            }
            httpSecurity.setSharedObject(OAuth2AuthorizationService.class, inMemoryOAuth2AuthorizationService);
        }
        return inMemoryOAuth2AuthorizationService;
    }

    public static OAuth2AuthorizationConsentService getAuthorizationConsentService(HttpSecurity httpSecurity) {
        InMemoryOAuth2AuthorizationConsentService inMemoryOAuth2AuthorizationConsentService = (OAuth2AuthorizationConsentService) httpSecurity.getSharedObject(OAuth2AuthorizationConsentService.class);
        if (inMemoryOAuth2AuthorizationConsentService == null) {
            inMemoryOAuth2AuthorizationConsentService = (OAuth2AuthorizationConsentService) getOptionalBean(httpSecurity, OAuth2AuthorizationConsentService.class);
            if (inMemoryOAuth2AuthorizationConsentService == null) {
                inMemoryOAuth2AuthorizationConsentService = new InMemoryOAuth2AuthorizationConsentService();
            }
            httpSecurity.setSharedObject(OAuth2AuthorizationConsentService.class, inMemoryOAuth2AuthorizationConsentService);
        }
        return inMemoryOAuth2AuthorizationConsentService;
    }

    public static OAuth2TokenGenerator<? extends OAuth2Token> getTokenGenerator(HttpSecurity httpSecurity) {
        DelegatingOAuth2TokenGenerator delegatingOAuth2TokenGenerator = (OAuth2TokenGenerator) httpSecurity.getSharedObject(OAuth2TokenGenerator.class);
        if (delegatingOAuth2TokenGenerator == null) {
            delegatingOAuth2TokenGenerator = (OAuth2TokenGenerator) getOptionalBean(httpSecurity, OAuth2TokenGenerator.class);
            if (delegatingOAuth2TokenGenerator == null) {
                OAuth2TokenGenerator jwtGenerator = getJwtGenerator(httpSecurity);
                OAuth2TokenGenerator oAuth2AccessTokenGenerator = new OAuth2AccessTokenGenerator();
                oAuth2AccessTokenGenerator.setAccessTokenCustomizer(getAccessTokenCustomizer(httpSecurity));
                OAuth2TokenGenerator oAuth2RefreshTokenGenerator = new OAuth2RefreshTokenGenerator();
                if (jwtGenerator != null) {
                    delegatingOAuth2TokenGenerator = new DelegatingOAuth2TokenGenerator(new OAuth2TokenGenerator[]{jwtGenerator, oAuth2AccessTokenGenerator, oAuth2RefreshTokenGenerator});
                } else {
                    delegatingOAuth2TokenGenerator = new DelegatingOAuth2TokenGenerator(new OAuth2TokenGenerator[]{oAuth2AccessTokenGenerator, oAuth2RefreshTokenGenerator});
                }
            }
            httpSecurity.setSharedObject(OAuth2TokenGenerator.class, delegatingOAuth2TokenGenerator);
        }
        return delegatingOAuth2TokenGenerator;
    }

    private static JwtGenerator getJwtGenerator(HttpSecurity httpSecurity) {
        JwtEncoder jwtEncoder;
        JwtGenerator jwtGenerator = (JwtGenerator) httpSecurity.getSharedObject(JwtGenerator.class);
        if (jwtGenerator == null && (jwtEncoder = getJwtEncoder(httpSecurity)) != null) {
            jwtGenerator = new JwtGenerator(jwtEncoder);
            jwtGenerator.setJwtCustomizer(getJwtCustomizer(httpSecurity));
            httpSecurity.setSharedObject(JwtGenerator.class, jwtGenerator);
        }
        return jwtGenerator;
    }

    private static JwtEncoder getJwtEncoder(HttpSecurity httpSecurity) {
        JWKSource<SecurityContext> jwkSource;
        NimbusJwtEncoder nimbusJwtEncoder = (JwtEncoder) httpSecurity.getSharedObject(JwtEncoder.class);
        if (nimbusJwtEncoder == null) {
            nimbusJwtEncoder = (JwtEncoder) getOptionalBean(httpSecurity, JwtEncoder.class);
            if (nimbusJwtEncoder == null && (jwkSource = getJwkSource(httpSecurity)) != null) {
                nimbusJwtEncoder = new NimbusJwtEncoder(jwkSource);
            }
            if (nimbusJwtEncoder != null) {
                httpSecurity.setSharedObject(JwtEncoder.class, nimbusJwtEncoder);
            }
        }
        return nimbusJwtEncoder;
    }

    public static JWKSource<SecurityContext> getJwkSource(HttpSecurity httpSecurity) {
        JWKSource<SecurityContext> jwkSource = (JWKSource) httpSecurity.getSharedObject(JWKSource.class);
        if (jwkSource == null) {
            ResolvableType type = ResolvableType.forClassWithGenerics(JWKSource.class, new Class[]{SecurityContext.class});
            jwkSource = (JWKSource) getOptionalBean(httpSecurity, type);
            if (jwkSource != null) {
                httpSecurity.setSharedObject(JWKSource.class, jwkSource);
            }
        }
        return jwkSource;
    }

    private static OAuth2TokenCustomizer<JwtEncodingContext> getJwtCustomizer(HttpSecurity httpSecurity) {
        OAuth2TokenCustomizer<JwtEncodingContext> defaultJwtCustomizer = DefaultOAuth2TokenCustomizers.jwtCustomizer();
        ResolvableType type = ResolvableType.forClassWithGenerics(OAuth2TokenCustomizer.class, new Class[]{JwtEncodingContext.class});
        OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer = (OAuth2TokenCustomizer) getOptionalBean(httpSecurity, type);
        if (jwtCustomizer == null) {
            return defaultJwtCustomizer;
        }
        return context -> {
            defaultJwtCustomizer.customize(context);
            jwtCustomizer.customize(context);
        };
    }

    private static OAuth2TokenCustomizer<OAuth2TokenClaimsContext> getAccessTokenCustomizer(HttpSecurity httpSecurity) {
        OAuth2TokenCustomizer<OAuth2TokenClaimsContext> defaultAccessTokenCustomizer = DefaultOAuth2TokenCustomizers.accessTokenCustomizer();
        ResolvableType type = ResolvableType.forClassWithGenerics(OAuth2TokenCustomizer.class, new Class[]{OAuth2TokenClaimsContext.class});
        OAuth2TokenCustomizer<OAuth2TokenClaimsContext> accessTokenCustomizer = (OAuth2TokenCustomizer) getOptionalBean(httpSecurity, type);
        if (accessTokenCustomizer == null) {
            return defaultAccessTokenCustomizer;
        }
        return context -> {
            defaultAccessTokenCustomizer.customize(context);
            accessTokenCustomizer.customize(context);
        };
    }

    public static AuthorizationServerSettings getAuthorizationServerSettings(HttpSecurity httpSecurity) {
        AuthorizationServerSettings authorizationServerSettings = (AuthorizationServerSettings) httpSecurity.getSharedObject(AuthorizationServerSettings.class);
        if (authorizationServerSettings == null) {
            authorizationServerSettings = (AuthorizationServerSettings) getBean(httpSecurity, AuthorizationServerSettings.class);
            httpSecurity.setSharedObject(AuthorizationServerSettings.class, authorizationServerSettings);
        }
        return authorizationServerSettings;
    }

    public static <T> T getBean(HttpSecurity httpSecurity, Class<T> cls) {
        return (T) ((ApplicationContext) httpSecurity.getSharedObject(ApplicationContext.class)).getBean(cls);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.beans.factory.NoSuchBeanDefinitionException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.beans.factory.NoUniqueBeanDefinitionException */
    public static <T> T getBean(HttpSecurity httpSecurity, ResolvableType resolvableType) throws NoSuchBeanDefinitionException, NoUniqueBeanDefinitionException {
        ApplicationContext applicationContext = (ApplicationContext) httpSecurity.getSharedObject(ApplicationContext.class);
        String[] beanNamesForType = applicationContext.getBeanNamesForType(resolvableType);
        if (beanNamesForType.length == 1) {
            return (T) applicationContext.getBean(beanNamesForType[0]);
        }
        if (beanNamesForType.length > 1) {
            throw new NoUniqueBeanDefinitionException(resolvableType, beanNamesForType);
        }
        throw new NoSuchBeanDefinitionException(resolvableType);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.beans.factory.NoUniqueBeanDefinitionException */
    public static <T> T getOptionalBean(HttpSecurity httpSecurity, Class<T> type) throws NoUniqueBeanDefinitionException {
        Map<String, T> beansMap = BeanFactoryUtils.beansOfTypeIncludingAncestors((ListableBeanFactory) httpSecurity.getSharedObject(ApplicationContext.class), type);
        if (beansMap.size() > 1) {
            throw new NoUniqueBeanDefinitionException(type, beansMap.size(), "Expected single matching bean of type '" + type.getName() + "' but found " + beansMap.size() + ": " + StringUtils.collectionToCommaDelimitedString(beansMap.keySet()));
        }
        if (beansMap.isEmpty()) {
            return null;
        }
        return beansMap.values().iterator().next();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.beans.factory.NoUniqueBeanDefinitionException */
    public static <T> T getOptionalBean(HttpSecurity httpSecurity, ResolvableType resolvableType) throws NoUniqueBeanDefinitionException {
        ApplicationContext applicationContext = (ApplicationContext) httpSecurity.getSharedObject(ApplicationContext.class);
        String[] beanNamesForType = applicationContext.getBeanNamesForType(resolvableType);
        if (beanNamesForType.length > 1) {
            throw new NoUniqueBeanDefinitionException(resolvableType, beanNamesForType);
        }
        if (beanNamesForType.length == 1) {
            return (T) applicationContext.getBean(beanNamesForType[0]);
        }
        return null;
    }
}
