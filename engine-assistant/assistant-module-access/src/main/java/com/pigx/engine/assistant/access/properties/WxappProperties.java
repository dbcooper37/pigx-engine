package com.pigx.engine.assistant.access.properties;

import com.pigx.engine.assistant.access.constant.AccessConstants;
import com.pigx.engine.assistant.access.definition.enums.MiniProgramState;
import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = AccessConstants.PROPERTY_ACCESS_WXAPP)
/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/properties/WxappProperties.class */
public class WxappProperties implements Serializable {
    private Boolean enabled;
    private String defaultAppId;
    private List<Config> configs;
    private List<Subscribe> subscribes;

    public String getDefaultAppId() {
        return this.defaultAppId;
    }

    public void setDefaultAppId(String defaultAppId) {
        this.defaultAppId = defaultAppId;
    }

    public List<Config> getConfigs() {
        return this.configs;
    }

    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }

    public List<Subscribe> getSubscribes() {
        return this.subscribes;
    }

    public void setSubscribes(List<Subscribe> subscribes) {
        this.subscribes = subscribes;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/properties/WxappProperties$Config.class */
    public static class Config {
        private String appId;
        private String secret;
        private String token;
        private String aesKey;
        private String messageDataFormat;

        public String getAppId() {
            return this.appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getSecret() {
            return this.secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getToken() {
            return this.token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAesKey() {
            return this.aesKey;
        }

        public void setAesKey(String aesKey) {
            this.aesKey = aesKey;
        }

        public String getMessageDataFormat() {
            return this.messageDataFormat;
        }

        public void setMessageDataFormat(String messageDataFormat) {
            this.messageDataFormat = messageDataFormat;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("appid", this.appId).add("secret", this.secret).add("token", this.token).add("aesKey", this.aesKey).add("messageDataFormat", this.messageDataFormat).toString();
        }
    }

    /* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/properties/WxappProperties$Subscribe.class */
    public static class Subscribe {
        private String redirectPage;
        private String templateId;
        private String subscribeId;
        private MiniProgramState miniProgramState = MiniProgramState.formal;

        public String getRedirectPage() {
            return this.redirectPage;
        }

        public void setRedirectPage(String redirectPage) {
            this.redirectPage = redirectPage;
        }

        public String getTemplateId() {
            return this.templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

        public MiniProgramState getMiniProgramState() {
            return this.miniProgramState;
        }

        public void setMiniProgramState(MiniProgramState miniProgramState) {
            this.miniProgramState = miniProgramState;
        }

        public String getSubscribeId() {
            return this.subscribeId;
        }

        public void setSubscribeId(String subscribeId) {
            this.subscribeId = subscribeId;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("redirectPage", this.redirectPage).add("templateId", this.templateId).add("subscribeId", this.subscribeId).add("miniProgramState", this.miniProgramState).toString();
        }
    }
}
