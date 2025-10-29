package com.pigx.engine.message.autoconfigure.stream;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.stream.function.FunctionConfiguration;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = {FunctionConfiguration.class})
@ConditionalOnBean({StreamBridge.class})
public class StreamAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(StreamAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD-ENGINE] |- Auto [Stream] Configure.");
    }

    @Bean
    public StreamMessageSendingAdapter streamMessageSendingAdapter(StreamBridge streamBridge) {
        StreamMessageSendingAdapter adapter = new StreamMessageSendingAdapter(streamBridge);
        log.trace("[PIGXD-ENGINE] |- Bean [Stream Message Sending Adapter] Configure.");
        return adapter;
    }
}
