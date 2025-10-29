package com.pigx.engine.core.autoconfigure.crypto;

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


@AutoConfiguration
@EnableConfigurationProperties(CryptoProperties.class)
public class CryptoAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CryptoAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [Crypto] Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnCryptoStrategy(CryptoStrategy.SM)
    static class SMCryptoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public AsymmetricCryptoProcessor asymmetricCryptoProcessor() {
            SM2CryptoProcessor sm2CryptoProcessor = new SM2CryptoProcessor();
            log.trace("[PIGXD] |- Strategy [SM Asymmetric SM2 Crypto Processor] Configure.");
            return sm2CryptoProcessor;
        }

        @Bean
        @ConditionalOnMissingBean
        public SymmetricCryptoProcessor symmetricCryptoProcessor() {
            SM4CryptoProcessor sm4CryptoProcessor = new SM4CryptoProcessor();
            log.trace("[PIGXD] |- Strategy [SM Symmetric SM4 Crypto Processor] Configure.");
            return sm4CryptoProcessor;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnCryptoStrategy(CryptoStrategy.STANDARD)
    static class StandardCryptoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public AsymmetricCryptoProcessor asymmetricCryptoProcessor() {
            RSACryptoProcessor rsaCryptoProcessor = new RSACryptoProcessor();
            log.trace("[PIGXD] |- Strategy [Standard Asymmetric RSA Crypto Processor] Configure.");
            return rsaCryptoProcessor;
        }

        @Bean
        @ConditionalOnMissingBean
        public SymmetricCryptoProcessor symmetricCryptoProcessor() {
            AESCryptoProcessor aesCryptoProcessor = new AESCryptoProcessor();
            log.trace("[PIGXD] |- Strategy [Standard Symmetric AES Crypto Processor] Configure.");
            return aesCryptoProcessor;
        }
    }
}
