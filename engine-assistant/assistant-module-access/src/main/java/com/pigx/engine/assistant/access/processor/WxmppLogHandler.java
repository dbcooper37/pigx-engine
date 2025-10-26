package com.pigx.engine.assistant.access.processor;

import com.pigx.engine.core.definition.utils.Jackson2Utils;
import java.util.Map;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/processor/WxmppLogHandler.class */
public class WxmppLogHandler implements WxMpMessageHandler {
    private static final Logger log = LoggerFactory.getLogger(WxmppLogHandler.class);

    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        log.info("\n接收到请求消息，内容：{}", Jackson2Utils.toJson(wxMpXmlMessage));
        return null;
    }
}
