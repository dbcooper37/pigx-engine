package com.pigx.engine.web.servlet.config;

import com.pigx.engine.core.definition.support.crypto.AsymmetricCryptoProcessor;
import com.pigx.engine.core.definition.support.crypto.SymmetricCryptoProcessor;
import com.pigx.engine.web.servlet.crypto.DecryptRequestBodyAdvice;
import com.pigx.engine.web.servlet.crypto.DecryptRequestParamMapResolver;
import com.pigx.engine.web.servlet.crypto.DecryptRequestParamResolver;
import com.pigx.engine.web.servlet.crypto.EncryptResponseBodyAdvice;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
/* loaded from: web-module-servlet-3.5.7.0.jar:cn/herodotus/engine/web/servlet/config/HttpCryptoConfiguration.class */
public class HttpCryptoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(HttpCryptoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Protect Http Crypto] Configure.");
    }

    @ConditionalOnMissingBean
    @Bean
    public HttpCryptoProcessor httpCryptoProcessor(AsymmetricCryptoProcessor asymmetricCryptoProcessor, SymmetricCryptoProcessor symmetricCryptoProcessor, ServerProperties serverProperties) {
        HttpCryptoProcessor httpCryptoProcessor = new HttpCryptoProcessor(asymmetricCryptoProcessor, symmetricCryptoProcessor, serverProperties);
        log.trace("[Herodotus] |- Bean [Interface Crypto Processor] Configure.");
        return httpCryptoProcessor;
    }

    @ConditionalOnMissingBean
    @ConditionalOnClass({HttpCryptoProcessor.class})
    @Bean
    public DecryptRequestBodyAdvice decryptRequestBodyAdvice(HttpCryptoProcessor httpCryptoProcessor) {
        DecryptRequestBodyAdvice decryptRequestBodyAdvice = new DecryptRequestBodyAdvice();
        decryptRequestBodyAdvice.setInterfaceCryptoProcessor(httpCryptoProcessor);
        log.trace("[Herodotus] |- Bean [Decrypt Request Body Advice] Configure.");
        return decryptRequestBodyAdvice;
    }

    @ConditionalOnMissingBean
    @ConditionalOnClass({HttpCryptoProcessor.class})
    @Bean
    public EncryptResponseBodyAdvice encryptResponseBodyAdvice(HttpCryptoProcessor httpCryptoProcessor) {
        EncryptResponseBodyAdvice encryptResponseBodyAdvice = new EncryptResponseBodyAdvice();
        encryptResponseBodyAdvice.setInterfaceCryptoProcessor(httpCryptoProcessor);
        log.trace("[Herodotus] |- Bean [Encrypt Response Body Advice] Configure.");
        return encryptResponseBodyAdvice;
    }

    @ConditionalOnMissingBean
    @ConditionalOnClass({HttpCryptoProcessor.class})
    @Bean
    public DecryptRequestParamMapResolver decryptRequestParamStringResolver(HttpCryptoProcessor httpCryptoProcessor) {
        DecryptRequestParamMapResolver decryptRequestParamMapResolver = new DecryptRequestParamMapResolver();
        decryptRequestParamMapResolver.setInterfaceCryptoProcessor(httpCryptoProcessor);
        log.trace("[Herodotus] |- Bean [Decrypt Request Param Map Resolver] Configure.");
        return decryptRequestParamMapResolver;
    }

    @ConditionalOnMissingBean
    @ConditionalOnClass({HttpCryptoProcessor.class})
    @Bean
    public DecryptRequestParamResolver decryptRequestParamResolver(HttpCryptoProcessor httpCryptoProcessor) {
        DecryptRequestParamResolver decryptRequestParamResolver = new DecryptRequestParamResolver();
        decryptRequestParamResolver.setInterfaceCryptoProcessor(httpCryptoProcessor);
        log.trace("[Herodotus] |- Bean [Decrypt Request Param Resolver] Configure.");
        return decryptRequestParamResolver;
    }
}
