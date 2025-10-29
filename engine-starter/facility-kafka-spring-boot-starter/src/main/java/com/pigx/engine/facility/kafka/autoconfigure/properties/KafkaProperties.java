package com.pigx.engine.facility.kafka.autoconfigure.properties;

import com.pigx.engine.message.core.constants.MessageConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = MessageConstants.PROPERTY_MESSAGE_KAFKA)
public class KafkaProperties {

    /**
     * Kakfa监听是否自动启动
     */
    private Boolean enabled = false;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
