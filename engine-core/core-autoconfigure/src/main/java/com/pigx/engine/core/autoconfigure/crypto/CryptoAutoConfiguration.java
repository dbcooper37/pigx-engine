package com.pigx.engine.autoconfigure.crypto;

import com.pigx.engine.core.definition.support.crypto.AsymmetricCryptoProcessor;
import com.pigx.engine.core.definition.support.crypto.SymmetricCryptoProcessor;
import com.pigx.engine.core.foundation.support.crypto.AESCryptoProcessor;
import com.pigx.engine.core.foundation.support.crypto.RSACryptoProcessor;
import com.pigx.engine.core.foundation.support.crypto.SM2CryptoProcessor;
import com.pigx.engine.core.foundation.support.crypto.SM4CryptoProcessor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({CryptoProperties.class})
@AutoConfiguration
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/crypto/CryptoAutoConfiguration.class */
public class CryptoAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(CryptoAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [Crypto] Configure.");
    }

    @ConditionalOnCryptoStrategy(CryptoStrategy.SM)
    @Configuration(proxyBeanMethods = false)
    /* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/crypto/CryptoAutoConfiguration$SMCryptoConfiguration.class */
    static class SMCryptoConfiguration {
        SMCryptoConfiguration() {
        }

        @ConditionalOnMissingBean
        @Bean
        public AsymmetricCryptoProcessor asymmetricCryptoProcessor() {
            SM2CryptoProcessor sm2CryptoProcessor = new SM2CryptoProcessor();
            CryptoAutoConfiguration.log.trace("[Herodotus] |- Strategy [SM Asymmetric SM2 Crypto Processor] Configure.");
            return sm2CryptoProcessor;
        }

        @ConditionalOnMissingBean
        @Bean
        public SymmetricCryptoProcessor symmetricCryptoProcessor() {
            SM4CryptoProcessor sm4CryptoProcessor = new SM4CryptoProcessor();
            CryptoAutoConfiguration.log.trace("[Herodotus] |- Strategy [SM Symmetric SM4 Crypto Processor] Configure.");
            return sm4CryptoProcessor;
        }
    }

    @ConditionalOnCryptoStrategy(CryptoStrategy.STANDARD)
    @Configuration(proxyBeanMethods = false)
    /* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/crypto/CryptoAutoConfiguration$StandardCryptoConfiguration.class */
    static class StandardCryptoConfiguration {
        StandardCryptoConfiguration() {
        }

        @ConditionalOnMissingBean
        @Bean
        public AsymmetricCryptoProcessor asymmetricCryptoProcessor() {
            RSACryptoProcessor rsaCryptoProcessor = new RSACryptoProcessor();
            CryptoAutoConfiguration.log.trace("[Herodotus] |- Strategy [Standard Asymmetric RSA Crypto Processor] Configure.");
            return rsaCryptoProcessor;
        }

        @ConditionalOnMissingBean
        @Bean
        public SymmetricCryptoProcessor symmetricCryptoProcessor() {
            AESCryptoProcessor aesCryptoProcessor = new AESCryptoProcessor();
            CryptoAutoConfiguration.log.trace("[Herodotus] |- Strategy [Standard Symmetric AES Crypto Processor] Configure.");
            return aesCryptoProcessor;
        }
    }
}
