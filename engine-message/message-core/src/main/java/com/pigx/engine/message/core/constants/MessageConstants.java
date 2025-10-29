package com.pigx.engine.message.core.constants;

import com.pigx.engine.core.definition.constant.BaseConstants;


public interface MessageConstants extends BaseConstants {

    String MESSAGE_AREA_PREFIX = AREA_PREFIX + "message:";

    String PROPERTY_MESSAGE_WEBSOCKET = PROPERTY_PREFIX_MESSAGE + ".websocket";
    String PROPERTY_MESSAGE_KAFKA = PROPERTY_PREFIX_MESSAGE + ".kafka";
    String PROPERTY_MESSAGE_MQTT = PROPERTY_PREFIX_MESSAGE + ".mqtt";

    String MULTIPLE_INSTANCE_INPUT = "webSocketMultipleInstanceSyncInput";
    String MULTIPLE_INSTANCE_OUTPUT = "webSocketMultipleInstanceSyncOutput";

    /**
     * 消息类型判断值。
     */
    String MESSAGE_TO_ALL = "message_to_all";

    String ITEM_KAFKA_ENABLED = PROPERTY_MESSAGE_KAFKA + PROPERTY_ENABLED;
    String ITEM_WEBSOCKET_MULTIPLE_INSTANCE = PROPERTY_MESSAGE_WEBSOCKET + ".mode";
    String ITEM_MQTT_USERNAME = PROPERTY_MESSAGE_MQTT + ".username";
    String ITEM_MQTT_PASSWORD = PROPERTY_MESSAGE_MQTT + ".password";

    String REDIS_CURRENT_ONLINE_USER = MESSAGE_AREA_PREFIX + "online:user";

    String WEBSOCKET_CHANNEL_PROXY_BROADCAST = "/broadcast";
    String WEBSOCKET_CHANNEL_PROXY_PERSONAL = "/personal";
    String WEBSOCKET_DESTINATION_BROADCAST_NOTICE = WEBSOCKET_CHANNEL_PROXY_BROADCAST + "/notice";
    String WEBSOCKET_DESTINATION_BROADCAST_ONLINE = WEBSOCKET_CHANNEL_PROXY_BROADCAST + "/online";
    String WEBSOCKET_DESTINATION_PERSONAL_MESSAGE = WEBSOCKET_CHANNEL_PROXY_PERSONAL + "/message";
}
