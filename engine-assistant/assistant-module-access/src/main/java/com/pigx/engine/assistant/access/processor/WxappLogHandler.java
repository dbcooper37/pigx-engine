package com.pigx.engine.assistant.access.processor;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.builder.TextMessageBuilder;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaXmlOutMessage;
import java.util.Map;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/processor/WxappLogHandler.class */
public class WxappLogHandler implements WxMaMessageHandler {
    private static final Logger log = LoggerFactory.getLogger(WxappLogHandler.class);

    public WxMaXmlOutMessage handle(WxMaMessage wxMaMessage, Map<String, Object> context, WxMaService wxMaService, WxSessionManager sessionManager) throws WxErrorException {
        log.info("收到消息：" + wxMaMessage.toString());
        wxMaService.getMsgService().sendKefuMsg(((TextMessageBuilder) WxMaKefuMessage.newTextBuilder().content("收到信息为：" + wxMaMessage.toJson()).toUser(wxMaMessage.getFromUser())).build());
        return null;
    }
}
