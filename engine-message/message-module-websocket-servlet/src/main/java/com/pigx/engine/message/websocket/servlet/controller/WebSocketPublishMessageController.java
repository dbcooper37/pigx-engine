package com.pigx.engine.message.websocket.servlet.controller;

import com.pigx.engine.core.foundation.context.AbstractApplicationContextAware;
import com.pigx.engine.message.core.constants.MessageConstants;
import com.pigx.engine.message.core.domain.DialogueMessage;
import com.pigx.engine.message.core.domain.WebSocketMessage;
import com.pigx.engine.message.core.event.SendDialogueMessageEvent;
import com.pigx.engine.message.websocket.servlet.definition.WebSocketMessageSender;
import com.pigx.engine.message.websocket.servlet.domain.WebSocketPrincipal;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@RestController
/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/controller/WebSocketPublishMessageController.class */
public class WebSocketPublishMessageController extends AbstractApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(WebSocketPublishMessageController.class);
    private final WebSocketMessageSender webSocketMessageSender;

    public WebSocketPublishMessageController(WebSocketMessageSender webSocketMessageSender) {
        this.webSocketMessageSender = webSocketMessageSender;
    }

    @MessageMapping({"/public/notice"})
    @SendTo({MessageConstants.WEBSOCKET_DESTINATION_BROADCAST_NOTICE})
    public String notice(String message, StompHeaderAccessor headerAccessor) {
        System.out.println("---message---" + message);
        if (ObjectUtils.isNotEmpty(headerAccessor)) {
            System.out.println("---id---" + headerAccessor.getUser().getName());
        }
        return message;
    }

    @MessageMapping({"/private/message"})
    public void sendPrivateMessage(@Payload DialogueMessage detail, StompHeaderAccessor headerAccessor) {
        WebSocketMessage response = new WebSocketMessage();
        response.setUser(detail.getReceiverId());
        response.setDestination(MessageConstants.WEBSOCKET_DESTINATION_PERSONAL_MESSAGE);
        if (StringUtils.isNotBlank(detail.getReceiverId()) && StringUtils.isNotBlank(detail.getReceiverName())) {
            if (StringUtils.isBlank(detail.getSenderId()) && StringUtils.isBlank(detail.getSenderName())) {
                WebSocketPrincipal sender = (WebSocketPrincipal) headerAccessor.getUser();
                detail.setSenderId(sender.getUserId());
                detail.setSenderName(sender.getUsername());
                detail.setSenderAvatar(sender.getAvatar());
            }
            publishEvent(new SendDialogueMessageEvent(detail));
            response.setPayload("私信发送成功");
        } else {
            response.setPayload("私信发送失败，参数错误");
        }
        this.webSocketMessageSender.toUser(response);
    }
}
