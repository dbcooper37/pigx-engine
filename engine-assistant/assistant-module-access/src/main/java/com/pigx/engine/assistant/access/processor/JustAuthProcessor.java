package com.pigx.engine.assistant.access.processor;

import cn.hutool.v7.core.util.EnumUtil;
import com.pigx.engine.assistant.access.exception.AccessConfigErrorException;
import com.pigx.engine.assistant.access.exception.IllegalAccessSourceException;
import com.pigx.engine.assistant.access.properties.JustAuthProperties;
import com.pigx.engine.assistant.access.stamp.JustAuthStateStampManager;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthDefaultSource;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;


public class JustAuthProcessor {

    private JustAuthProperties justAuthProperties;
    private JustAuthStateStampManager justAuthStateStampManager;

    public void setJustAuthProperties(JustAuthProperties justAuthProperties) {
        this.justAuthProperties = justAuthProperties;
    }

    public void setJustAuthStateRedisCache(JustAuthStateStampManager justAuthStateStampManager) {
        this.justAuthStateStampManager = justAuthStateStampManager;
    }

    private static AuthDefaultSource parseAuthDefaultSource(String source) {
        AuthDefaultSource authDefaultSource;

        try {
            authDefaultSource = EnumUtil.fromString(AuthDefaultSource.class, source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalAccessSourceException();
        }
        return authDefaultSource;
    }

    public AuthRequest getAuthRequest(String source) {
        AuthDefaultSource authDefaultSource = parseAuthDefaultSource(source);
        AuthConfig authConfig = getAuthConfig(authDefaultSource);
        return getAuthRequest(authDefaultSource, authConfig);
    }

    public AuthRequest getAuthRequest(String source, AuthConfig authConfig) {
        AuthDefaultSource authDefaultSource = parseAuthDefaultSource(source);
        return getAuthRequest(authDefaultSource, authConfig);
    }

    private JustAuthStateStampManager getJustAuthStateRedisCache() {
        return justAuthStateStampManager;
    }

    /**
     * 返回带state参数的授权url，授权回调时会带上这个state
     *
     * @param source 第三方登录的类别 {@link AuthDefaultSource}
     * @return 返回授权地址
     */
    public String getAuthorizeUrl(String source) {
        AuthRequest authRequest = this.getAuthRequest(source);
        return authRequest.authorize(AuthStateUtils.createState());
    }

    public String getAuthorizeUrl(String source, AuthConfig authConfig) {
        AuthRequest authRequest = this.getAuthRequest(source, authConfig);
        return authRequest.authorize(AuthStateUtils.createState());
    }

    public Map<String, String> getAuthorizeUrls() {
        Map<String, AuthConfig> configs = getConfigs();
        return configs.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> getAuthorizeUrl(entry.getKey(), entry.getValue())));
    }

    @NotNull
    private Map<String, AuthConfig> getConfigs() {
        Map<String, AuthConfig> configs = justAuthProperties.getConfigs();
        if (MapUtils.isEmpty(configs)) {
            throw new AccessConfigErrorException();
        }
        return configs;
    }

    @NotNull
    private AuthConfig getAuthConfig(AuthDefaultSource authDefaultSource) {
        Map<String, AuthConfig> configs = getConfigs();

        AuthConfig authConfig = configs.get(authDefaultSource.name());
        // 找不到对应关系，直接返回空
        if (ObjectUtils.isEmpty(authConfig)) {
            throw new AccessConfigErrorException();
        }
        return authConfig;
    }

    private AuthRequest getAuthRequest(AuthDefaultSource authDefaultSource, AuthConfig authConfig) {
        return switch (authDefaultSource) {
            case GITHUB -> new AuthGithubRequest(authConfig, this.getJustAuthStateRedisCache());
            case WEIBO -> new AuthWeiboRequest(authConfig, this.getJustAuthStateRedisCache());
            case GITEE -> new AuthGiteeRequest(authConfig, this.getJustAuthStateRedisCache());
            case DINGTALK -> new AuthDingTalkRequest(authConfig, this.getJustAuthStateRedisCache());
            case BAIDU -> new AuthBaiduRequest(authConfig, this.getJustAuthStateRedisCache());
//            case CSDN -> new AuthCsdnRequest(authConfig, this.getJustAuthStateRedisCache());
            case CODING -> new AuthCodingRequest(authConfig, this.getJustAuthStateRedisCache());
            case OSCHINA -> new AuthOschinaRequest(authConfig, this.getJustAuthStateRedisCache());
            case ALIPAY ->
                    new AuthAlipayRequest(authConfig, justAuthProperties.getAlipayPublicKey(), this.getJustAuthStateRedisCache());
            case QQ -> new AuthQqRequest(authConfig, this.getJustAuthStateRedisCache());
            case WECHAT_MP -> new AuthWeChatMpRequest(authConfig, this.getJustAuthStateRedisCache());
            case WECHAT_OPEN -> new AuthWeChatOpenRequest(authConfig, this.getJustAuthStateRedisCache());
            case WECHAT_ENTERPRISE ->
                    new AuthWeChatEnterpriseQrcodeRequest(authConfig, this.getJustAuthStateRedisCache());
            case WECHAT_ENTERPRISE_WEB ->
                    new AuthWeChatEnterpriseWebRequest(authConfig, this.getJustAuthStateRedisCache());
            case TAOBAO -> new AuthTaobaoRequest(authConfig, this.getJustAuthStateRedisCache());
            case GOOGLE -> new AuthGoogleRequest(authConfig, this.getJustAuthStateRedisCache());
            case FACEBOOK -> new AuthFacebookRequest(authConfig, this.getJustAuthStateRedisCache());
            case DOUYIN -> new AuthDouyinRequest(authConfig, this.getJustAuthStateRedisCache());
            case LINKEDIN -> new AuthLinkedinRequest(authConfig, this.getJustAuthStateRedisCache());
            case MICROSOFT -> new AuthMicrosoftRequest(authConfig, this.getJustAuthStateRedisCache());
            case MI -> new AuthMiRequest(authConfig, this.getJustAuthStateRedisCache());
            case TOUTIAO -> new AuthToutiaoRequest(authConfig, this.getJustAuthStateRedisCache());
            case TEAMBITION -> new AuthTeambitionRequest(authConfig, this.getJustAuthStateRedisCache());
            case RENREN -> new AuthRenrenRequest(authConfig, this.getJustAuthStateRedisCache());
            case PINTEREST -> new AuthPinterestRequest(authConfig, this.getJustAuthStateRedisCache());
            case STACK_OVERFLOW -> new AuthStackOverflowRequest(authConfig, this.getJustAuthStateRedisCache());
            case HUAWEI_V3 -> new AuthHuaweiV3Request(authConfig, this.getJustAuthStateRedisCache());
            case GITLAB -> new AuthGitlabRequest(authConfig, this.getJustAuthStateRedisCache());
            case KUJIALE -> new AuthKujialeRequest(authConfig, this.getJustAuthStateRedisCache());
            case ELEME -> new AuthElemeRequest(authConfig, this.getJustAuthStateRedisCache());
            case MEITUAN -> new AuthMeituanRequest(authConfig, this.getJustAuthStateRedisCache());
            case TWITTER -> new AuthTwitterRequest(authConfig, this.getJustAuthStateRedisCache());
            case FEISHU -> new AuthFeishuRequest(authConfig, this.getJustAuthStateRedisCache());
            case JD -> new AuthJdRequest(authConfig, this.getJustAuthStateRedisCache());
            case ALIYUN -> new AuthAliyunRequest(authConfig, this.getJustAuthStateRedisCache());
            case XMLY -> new AuthXmlyRequest(authConfig, this.getJustAuthStateRedisCache());
            default -> null;
        };
    }
}
