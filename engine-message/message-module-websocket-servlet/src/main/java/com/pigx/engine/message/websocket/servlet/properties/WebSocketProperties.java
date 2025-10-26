package com.pigx.engine.message.websocket.servlet.properties;

import com.pigx.engine.core.definition.constant.HerodotusHeaders;
import com.pigx.engine.message.core.constants.MessageConstants;
import com.pigx.engine.message.websocket.servlet.enums.InstanceMode;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = MessageConstants.PROPERTY_MESSAGE_WEBSOCKET)
/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/properties/WebSocketProperties.class */
public class WebSocketProperties {
    private InstanceMode mode = InstanceMode.SINGLE;
    private String endpoint = "stomp/ws";
    private List<String> applicationDestinationPrefixes = Collections.singletonList("/app");
    private String userDestinationPrefix = "/user";
    private String topic = "ws";
    private String principalHeader = HerodotusHeaders.X_HERODOTUS_OPEN_ID;

    public InstanceMode getMode() {
        return this.mode;
    }

    public void setMode(InstanceMode mode) {
        this.mode = mode;
    }

    private String format(String endpoint) {
        if (StringUtils.isNotBlank(endpoint) && !Strings.CS.startsWith(endpoint, "/")) {
            return "/" + endpoint;
        }
        return endpoint;
    }

    public String getEndpoint() {
        return format(this.endpoint);
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public List<String> getApplicationDestinationPrefixes() {
        return this.applicationDestinationPrefixes;
    }

    public void setApplicationDestinationPrefixes(List<String> applicationDestinationPrefixes) {
        this.applicationDestinationPrefixes = applicationDestinationPrefixes;
    }

    public String[] getApplicationPrefixes() {
        List<String> prefixes = getApplicationDestinationPrefixes();
        if (CollectionUtils.isNotEmpty(prefixes)) {
            List<String> wellFormed = prefixes.stream().map(this::format).toList();
            String[] result = new String[wellFormed.size()];
            return (String[]) wellFormed.toArray(result);
        }
        return new String[0];
    }

    public String getUserDestinationPrefix() {
        return format(this.userDestinationPrefix);
    }

    public void setUserDestinationPrefix(String userDestinationPrefix) {
        this.userDestinationPrefix = userDestinationPrefix;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPrincipalHeader() {
        return this.principalHeader;
    }

    public void setPrincipalHeader(String principalHeader) {
        this.principalHeader = principalHeader;
    }
}
