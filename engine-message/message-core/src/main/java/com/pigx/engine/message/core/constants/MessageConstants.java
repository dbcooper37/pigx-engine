package com.pigx.engine.message.core.constants;

import com.pigx.engine.core.definition.constant.BaseConstants;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/constants/MessageConstants.class */
public interface MessageConstants extends BaseConstants {
    public static final String MESSAGE_AREA_PREFIX = "data:message:";
    public static final String PROPERTY_MESSAGE_WEBSOCKET = "herodotus.message.websocket";
    public static final String PROPERTY_MESSAGE_KAFKA = "herodotus.message.kafka";
    public static final String PROPERTY_MESSAGE_MQTT = "herodotus.message.mqtt";
    public static final String MULTIPLE_INSTANCE_INPUT = "webSocketMultipleInstanceSyncInput";
    public static final String MULTIPLE_INSTANCE_OUTPUT = "webSocketMultipleInstanceSyncOutput";
    public static final String MESSAGE_TO_ALL = "message_to_all";
    public static final String ITEM_KAFKA_ENABLED = "herodotus.message.kafka.enabled";
    public static final String ITEM_WEBSOCKET_MULTIPLE_INSTANCE = "herodotus.message.websocket.mode";
    public static final String ITEM_MQTT_USERNAME = "herodotus.message.mqtt.username";
    public static final String ITEM_MQTT_PASSWORD = "herodotus.message.mqtt.password";
    public static final String REDIS_CURRENT_ONLINE_USER = "data:message:online:user";
    public static final String WEBSOCKET_CHANNEL_PROXY_BROADCAST = "/broadcast";
    public static final String WEBSOCKET_CHANNEL_PROXY_PERSONAL = "/personal";
    public static final String WEBSOCKET_DESTINATION_BROADCAST_NOTICE = "/broadcast/notice";
    public static final String WEBSOCKET_DESTINATION_BROADCAST_ONLINE = "/broadcast/online";
    public static final String WEBSOCKET_DESTINATION_PERSONAL_MESSAGE = "/personal/message";
}
