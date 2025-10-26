package com.pigx.engine.assistant.access.processor;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaMediaAsyncCheckResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.pigx.engine.assistant.access.properties.WxappProperties;
import com.google.common.collect.Maps;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.error.WxRuntimeException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/processor/WxappProcessor.class */
public class WxappProcessor {
    private static final Logger log = LoggerFactory.getLogger(WxappProcessor.class);
    private final WxappProperties wxappProperties;
    private final WxappLogHandler wxappLogHandler = new WxappLogHandler();
    private final Map<String, WxMaMessageRouter> wxMaMessageRouters = Maps.newHashMap();
    private final Map<String, WxMaService> wxMaServices;

    public WxappProcessor(WxappProperties wxappProperties) {
        this.wxappProperties = wxappProperties;
        this.wxMaServices = initWxMaServices(this.wxappProperties, this.wxMaMessageRouters);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: me.chanjar.weixin.common.error.WxRuntimeException */
    private Map<String, WxMaService> initWxMaServices(WxappProperties wxappProperties, Map<String, WxMaMessageRouter> wxMaMessageRouters) throws WxRuntimeException {
        List<WxappProperties.Config> configs = wxappProperties.getConfigs();
        if (CollectionUtils.isNotEmpty(configs)) {
            log.info("[Herodotus] |- Bean [Weixin Mini App] Configure.");
            return (Map) configs.stream().map(a -> {
                WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
                config.setAppid(a.getAppId());
                config.setSecret(a.getSecret());
                config.setToken(a.getToken());
                config.setAesKey(a.getAesKey());
                config.setMsgDataFormat(a.getMessageDataFormat());
                WxMaServiceImpl wxMaServiceImpl = new WxMaServiceImpl();
                wxMaServiceImpl.setWxMaConfig(config);
                wxMaMessageRouters.put(a.getAppId(), newRouter(wxMaServiceImpl));
                return wxMaServiceImpl;
            }).collect(Collectors.toMap(s -> {
                return s.getWxMaConfig().getAppid();
            }, a2 -> {
                return a2;
            }));
        }
        throw new WxRuntimeException("Weixin Mini App Configuraiton is not setting!");
    }

    private WxMaMessageRouter newRouter(WxMaService wxMaService) {
        WxMaMessageRouter router = new WxMaMessageRouter(wxMaService);
        router.rule().handler(this.wxappLogHandler).next();
        return router;
    }

    public WxMaService getWxMaService(String appid) {
        WxMaService wxMaService = this.wxMaServices.get(appid);
        if (ObjectUtils.isEmpty(wxMaService)) {
            throw new IllegalArgumentException(String.format("Cannot find the configuration of appid=[%s], please check!", appid));
        }
        return wxMaService;
    }

    public WxMaMessageRouter getWxMaMessageRouter(String appid) {
        return this.wxMaMessageRouters.get(appid);
    }

    public WxMaService getWxMaService() {
        String appId = this.wxappProperties.getDefaultAppId();
        if (StringUtils.isBlank(appId)) {
            log.error("[Herodotus] |- Must set [herodotus.platform.social.wxapp.default-app-id] property, or use getWxMaService(String appid)!");
            throw new IllegalArgumentException("Must set [herodotus.platform.social.wxapp.default-app-id] property");
        }
        return getWxMaService(appId);
    }

    private WxMaJscode2SessionResult getSessionInfo(String code, WxMaService wxMaService) {
        try {
            WxMaJscode2SessionResult sessionResult = wxMaService.getUserService().getSessionInfo(code);
            log.debug("[Herodotus] |- Weixin Mini App login successfully!");
            return sessionResult;
        } catch (WxErrorException e) {
            log.error("[Herodotus] |- Weixin Mini App login failed! For reason: {}", e.getMessage());
            return null;
        }
    }

    public WxMaJscode2SessionResult login(String code, String appId) {
        WxMaService wxMaService = getWxMaService(appId);
        if (StringUtils.isNotBlank(code) && ObjectUtils.isNotEmpty(wxMaService)) {
            return getSessionInfo(code, wxMaService);
        }
        log.error("[Herodotus] |- Weixin Mini App login failed, please check code param!");
        return null;
    }

    private boolean checkUserInfo(String sessionKey, String rawData, String signature, WxMaService wxMaService) {
        if (wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            log.debug("[Herodotus] |- Weixin Mini App user info is valid!");
            return true;
        }
        log.warn("[Herodotus] |- Weixin Mini App user check failed!");
        return false;
    }

    private boolean checkUserInfo(String rawData, String signature) {
        return StringUtils.isNotBlank(rawData) && StringUtils.isNotBlank(signature);
    }

    private WxMaUserInfo getUserInfo(String sessionKey, String encryptedData, String iv, WxMaService wxMaService) {
        WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        log.debug("[Herodotus] |- Weixin Mini App get user info successfully!");
        return wxMaUserInfo;
    }

    public WxMaUserInfo getUserInfo(String appId, String sessionKey, String encryptedData, String iv) {
        return getUserInfo(appId, sessionKey, encryptedData, iv, null, null);
    }

    public WxMaUserInfo getUserInfo(String appId, String sessionKey, String encryptedData, String iv, String rawData, String signature) {
        WxMaService wxMaService = getWxMaService(appId);
        if (ObjectUtils.isNotEmpty(wxMaService)) {
            if (checkUserInfo(rawData, signature) && checkUserInfo(sessionKey, rawData, signature, wxMaService)) {
                return null;
            }
            return getUserInfo(sessionKey, encryptedData, iv, wxMaService);
        }
        log.error("[Herodotus] |- Weixin Mini App get user info failed!");
        return null;
    }

    private WxMaPhoneNumberInfo getPhoneNumberInfo(String code, WxMaService wxMaService) {
        log.info("[Herodotus] |- Weixin Mini App get code： {}", code);
        try {
            WxMaPhoneNumberInfo wxMaPhoneNumberInfo = wxMaService.getUserService().getPhoneNumber(code);
            log.debug("[Herodotus] |- Weixin Mini App get phone number successfully! WxMaPhoneNumberInfo : {}", wxMaPhoneNumberInfo.toString());
            return wxMaPhoneNumberInfo;
        } catch (Exception e) {
            log.error("[Herodotus] |- Weixin Mini App get phone number failed!");
            return null;
        }
    }

    public WxMaPhoneNumberInfo getPhoneNumberInfo(String appId, String sessionKey, String rawData, String signature, String code) {
        WxMaService wxMaService = getWxMaService(appId);
        if (ObjectUtils.isNotEmpty(wxMaService)) {
            if (checkUserInfo(rawData, signature) && checkUserInfo(sessionKey, rawData, signature, wxMaService)) {
                return null;
            }
            return getPhoneNumberInfo(code, wxMaService);
        }
        log.error("[Herodotus] |- Weixin Mini App get phone number info failed!");
        return null;
    }

    public boolean sendSubscribeMessage(String appId, WxMaSubscribeMessage subscribeMessage) {
        try {
            getWxMaService(appId).getMsgService().sendSubscribeMsg(subscribeMessage);
            log.debug("[Herodotus] |- Send Subscribe Message Successfully!");
            return true;
        } catch (WxErrorException e) {
            log.debug("[Herodotus] |- Send Subscribe Message Failed!", e);
            return false;
        }
    }

    public boolean checkMessage(String appId, String message) {
        try {
            getWxMaService(appId).getSecurityService().checkMessage(message);
            log.debug("[Herodotus] |- Check Message Successfully!");
            return true;
        } catch (WxErrorException e) {
            log.debug("[Herodotus] |- Check Message Failed!", e);
            return false;
        }
    }

    public boolean checkImage(String appId, String fileUrl) {
        try {
            getWxMaService(appId).getSecurityService().checkImage(fileUrl);
            log.debug("[Herodotus] |- Check Image use fileUrl Successfully!");
            return true;
        } catch (WxErrorException e) {
            log.debug("[Herodotus] |- Check Image use fileUrl Failed! Detail is ：{}", e.getMessage());
            return false;
        }
    }

    public boolean checkImage(String appId, File file) {
        try {
            getWxMaService(appId).getSecurityService().checkImage(file);
            log.debug("[Herodotus] |- Check Image use file Successfully!");
            return true;
        } catch (WxErrorException e) {
            log.debug("[Herodotus] |- Check Image use file Failed! Detail is ：{}", e.getMessage());
            return false;
        }
    }

    public WxMaMediaAsyncCheckResult mediaAsyncCheck(String appId, String mediaUrl, int mediaType) {
        WxMaMediaAsyncCheckResult wxMaMediaAsyncCheckResult = null;
        try {
            wxMaMediaAsyncCheckResult = getWxMaService(appId).getSecurityService().mediaCheckAsync(mediaUrl, mediaType);
            log.debug("[Herodotus] |- Media Async Check Successfully!");
        } catch (WxErrorException e) {
            log.debug("[Herodotus] |- Media Async Check Failed! Detail is ：{}", e.getMessage());
        }
        return wxMaMediaAsyncCheckResult;
    }
}
