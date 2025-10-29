package com.pigx.engine.facility.kafka.autoconfigure;

import com.pigx.engine.facility.kafka.autoconfigure.annotation.ConditionalOnKafkaEnabled;
import com.pigx.engine.facility.kafka.autoconfigure.properties.KafkaProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;


@AutoConfiguration
@ConditionalOnKafkaEnabled
@EnableConfigurationProperties({KafkaProperties.class})
public class FacilityKafkaAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(FacilityKafkaAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Starter [Facility Kafka] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean(ConcurrentKafkaListenerContainerFactory.class)
    public ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory(KafkaProperties kafkaProperties, ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
        concurrentKafkaListenerContainerFactory.setAutoStartup(kafkaProperties.getEnabled());
        log.trace("[PIGXD] |- Bean [Concurrent Kafka Listener ContainerFactory] Configure.");
        return concurrentKafkaListenerContainerFactory;
    }
}
