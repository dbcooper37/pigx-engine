package com.pigx.engine.assistant.access.processor;

import com.pigx.engine.assistant.access.properties.WxmppProperties;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/processor/WxmppProcessor.class */
public class WxmppProcessor implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(WxmppProcessor.class);
    private WxMpService wxMpService;
    private WxmppProperties wxmppProperties;
    private WxmppLogHandler wxmppLogHandler;
    private StringRedisTemplate stringRedisTemplate;

    public void setWxmppProperties(WxmppProperties wxmppProperties) {
        this.wxmppProperties = wxmppProperties;
    }

    public void setWxmppLogHandler(WxmppLogHandler wxmppLogHandler) {
        this.wxmppLogHandler = wxmppLogHandler;
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void afterPropertiesSet() throws Exception {
        List<WxmppProperties.MpConfig> configs = this.wxmppProperties.getConfigs();
        if (configs == null) {
            throw new RuntimeException("大哥，拜托先看下项目首页的说明（readme文件），添加下相关配置，注意别配错了！");
        }
        WxMpServiceImpl wxMpServiceImpl = new WxMpServiceImpl();
        wxMpServiceImpl.setMultiConfigStorages((Map) configs.stream().map(a -> {
            WxMpRedisConfigImpl wxMpDefaultConfigImpl;
            if (this.wxmppProperties.isUseRedis()) {
                this.wxmppProperties.getRedis();
                wxMpDefaultConfigImpl = new WxMpRedisConfigImpl(new RedisTemplateWxRedisOps(this.stringRedisTemplate), a.getAppId());
            } else {
                wxMpDefaultConfigImpl = new WxMpDefaultConfigImpl();
            }
            wxMpDefaultConfigImpl.setAppId(a.getAppId());
            wxMpDefaultConfigImpl.setSecret(a.getSecret());
            wxMpDefaultConfigImpl.setToken(a.getToken());
            wxMpDefaultConfigImpl.setAesKey(a.getAesKey());
            return wxMpDefaultConfigImpl;
        }).collect(Collectors.toMap((v0) -> {
            return v0.getAppId();
        }, a2 -> {
            return a2;
        }, (o, n) -> {
            return o;
        })));
        log.info("[Herodotus] |- Bean [Herodotus Weixin Micro Message Public Platform] Configure.");
        this.wxMpService = wxMpServiceImpl;
    }

    public WxMpService getWxMpService() {
        if (ObjectUtils.isEmpty(this.wxMpService)) {
            throw new IllegalArgumentException(String.format("Cannot find the configuration for wechat official accounts, please check!", new Object[0]));
        }
        return this.wxMpService;
    }

    public WxMpMessageRouter getWxMpMessageRouter() {
        WxMpMessageRouter newRouter = new WxMpMessageRouter(getWxMpService());
        newRouter.rule().handler(this.wxmppLogHandler).next();
        return newRouter;
    }
}
