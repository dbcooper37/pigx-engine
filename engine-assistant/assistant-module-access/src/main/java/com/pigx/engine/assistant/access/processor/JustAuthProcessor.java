package com.pigx.engine.assistant.access.processor;

import com.pigx.engine.assistant.access.exception.AccessConfigErrorException;
import com.pigx.engine.assistant.access.exception.IllegalAccessSourceException;
import com.pigx.engine.assistant.access.properties.JustAuthProperties;
import com.pigx.engine.assistant.access.stamp.JustAuthStateStampManager;
import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.core.definition.constant.Jackson2CustomizerOrder;
import cn.hutool.v7.core.util.EnumUtil;
import java.util.Map;
import java.util.stream.Collectors;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthDefaultSource;
import me.zhyd.oauth.request.AuthAlipayRequest;
import me.zhyd.oauth.request.AuthAliyunRequest;
import me.zhyd.oauth.request.AuthBaiduRequest;
import me.zhyd.oauth.request.AuthCodingRequest;
import me.zhyd.oauth.request.AuthDingTalkRequest;
import me.zhyd.oauth.request.AuthDouyinRequest;
import me.zhyd.oauth.request.AuthElemeRequest;
import me.zhyd.oauth.request.AuthFacebookRequest;
import me.zhyd.oauth.request.AuthFeishuRequest;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthGitlabRequest;
import me.zhyd.oauth.request.AuthGoogleRequest;
import me.zhyd.oauth.request.AuthHuaweiV3Request;
import me.zhyd.oauth.request.AuthJdRequest;
import me.zhyd.oauth.request.AuthKujialeRequest;
import me.zhyd.oauth.request.AuthLinkedinRequest;
import me.zhyd.oauth.request.AuthMeituanRequest;
import me.zhyd.oauth.request.AuthMiRequest;
import me.zhyd.oauth.request.AuthMicrosoftRequest;
import me.zhyd.oauth.request.AuthOschinaRequest;
import me.zhyd.oauth.request.AuthPinterestRequest;
import me.zhyd.oauth.request.AuthQqRequest;
import me.zhyd.oauth.request.AuthRenrenRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.request.AuthStackOverflowRequest;
import me.zhyd.oauth.request.AuthTaobaoRequest;
import me.zhyd.oauth.request.AuthTeambitionRequest;
import me.zhyd.oauth.request.AuthToutiaoRequest;
import me.zhyd.oauth.request.AuthTwitterRequest;
import me.zhyd.oauth.request.AuthWeChatEnterpriseQrcodeRequest;
import me.zhyd.oauth.request.AuthWeChatEnterpriseWebRequest;
import me.zhyd.oauth.request.AuthWeChatMpRequest;
import me.zhyd.oauth.request.AuthWeChatOpenRequest;
import me.zhyd.oauth.request.AuthWeiboRequest;
import me.zhyd.oauth.request.AuthXmlyRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/processor/JustAuthProcessor.class */
public class JustAuthProcessor {
    private JustAuthProperties justAuthProperties;
    private JustAuthStateStampManager justAuthStateStampManager;

    public void setJustAuthProperties(JustAuthProperties justAuthProperties) {
        this.justAuthProperties = justAuthProperties;
    }

    public void setJustAuthStateRedisCache(JustAuthStateStampManager justAuthStateStampManager) {
        this.justAuthStateStampManager = justAuthStateStampManager;
    }

    private JustAuthStateStampManager getJustAuthStateRedisCache() {
        return this.justAuthStateStampManager;
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

    public String getAuthorizeUrl(String source) {
        AuthRequest authRequest = getAuthRequest(source);
        return authRequest.authorize(AuthStateUtils.createState());
    }

    public String getAuthorizeUrl(String source, AuthConfig authConfig) {
        AuthRequest authRequest = getAuthRequest(source, authConfig);
        return authRequest.authorize(AuthStateUtils.createState());
    }

    public Map<String, String> getAuthorizeUrls() {
        Map<String, AuthConfig> configs = getConfigs();
        return (Map) configs.entrySet().stream().collect(Collectors.toMap((v0) -> {
            return v0.getKey();
        }, entry -> {
            return getAuthorizeUrl((String) entry.getKey(), (AuthConfig) entry.getValue());
        }));
    }

    @NotNull
    private Map<String, AuthConfig> getConfigs() {
        Map<String, AuthConfig> configs = this.justAuthProperties.getConfigs();
        if (MapUtils.isEmpty(configs)) {
            throw new AccessConfigErrorException();
        }
        return configs;
    }

    @NotNull
    private AuthConfig getAuthConfig(AuthDefaultSource authDefaultSource) {
        Map<String, AuthConfig> configs = getConfigs();
        AuthConfig authConfig = configs.get(authDefaultSource.name());
        if (ObjectUtils.isEmpty(authConfig)) {
            throw new AccessConfigErrorException();
        }
        return authConfig;
    }

    private static AuthDefaultSource parseAuthDefaultSource(String source) {
        try {
            AuthDefaultSource authDefaultSource = EnumUtil.fromString(AuthDefaultSource.class, source.toUpperCase());
            return authDefaultSource;
        } catch (IllegalArgumentException e) {
            throw new IllegalAccessSourceException();
        }
    }

    /* renamed from: com.pigx.engine.assistant.access.processor.JustAuthProcessor$1, reason: invalid class name */
    /* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/processor/JustAuthProcessor$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource = new int[AuthDefaultSource.values().length];

        static {
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.GITHUB.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.WEIBO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.GITEE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.DINGTALK.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.BAIDU.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.CODING.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.OSCHINA.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.ALIPAY.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.QQ.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.WECHAT_MP.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.WECHAT_OPEN.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.WECHAT_ENTERPRISE.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.WECHAT_ENTERPRISE_WEB.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.TAOBAO.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.GOOGLE.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.FACEBOOK.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.DOUYIN.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.LINKEDIN.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.MICROSOFT.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.MI.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.TOUTIAO.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.TEAMBITION.ordinal()] = 22;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.RENREN.ordinal()] = 23;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.PINTEREST.ordinal()] = 24;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.STACK_OVERFLOW.ordinal()] = 25;
            } catch (NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.HUAWEI_V3.ordinal()] = 26;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.GITLAB.ordinal()] = 27;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.KUJIALE.ordinal()] = 28;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.ELEME.ordinal()] = 29;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.MEITUAN.ordinal()] = 30;
            } catch (NoSuchFieldError e30) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.TWITTER.ordinal()] = 31;
            } catch (NoSuchFieldError e31) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.FEISHU.ordinal()] = 32;
            } catch (NoSuchFieldError e32) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.JD.ordinal()] = 33;
            } catch (NoSuchFieldError e33) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.ALIYUN.ordinal()] = 34;
            } catch (NoSuchFieldError e34) {
            }
            try {
                $SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[AuthDefaultSource.XMLY.ordinal()] = 35;
            } catch (NoSuchFieldError e35) {
            }
        }
    }

    private AuthRequest getAuthRequest(AuthDefaultSource authDefaultSource, AuthConfig authConfig) {
        switch (AnonymousClass1.$SwitchMap$me$zhyd$oauth$config$AuthDefaultSource[authDefaultSource.ordinal()]) {
            case Jackson2CustomizerOrder.CUSTOMIZER_DEFAULT /* 1 */:
                return new AuthGithubRequest(authConfig, getJustAuthStateRedisCache());
            case Jackson2CustomizerOrder.CUSTOMIZER_XSS /* 2 */:
                return new AuthWeiboRequest(authConfig, getJustAuthStateRedisCache());
            case 3:
                return new AuthGiteeRequest(authConfig, getJustAuthStateRedisCache());
            case 4:
                return new AuthDingTalkRequest(authConfig, getJustAuthStateRedisCache());
            case 5:
                return new AuthBaiduRequest(authConfig, getJustAuthStateRedisCache());
            case 6:
                return new AuthCodingRequest(authConfig, getJustAuthStateRedisCache());
            case 7:
                return new AuthOschinaRequest(authConfig, getJustAuthStateRedisCache());
            case 8:
                return new AuthAlipayRequest(authConfig, this.justAuthProperties.getAlipayPublicKey(), getJustAuthStateRedisCache());
            case 9:
                return new AuthQqRequest(authConfig, getJustAuthStateRedisCache());
            case 10:
                return new AuthWeChatMpRequest(authConfig, getJustAuthStateRedisCache());
            case 11:
                return new AuthWeChatOpenRequest(authConfig, getJustAuthStateRedisCache());
            case 12:
                return new AuthWeChatEnterpriseQrcodeRequest(authConfig, getJustAuthStateRedisCache());
            case 13:
                return new AuthWeChatEnterpriseWebRequest(authConfig, getJustAuthStateRedisCache());
            case 14:
                return new AuthTaobaoRequest(authConfig, getJustAuthStateRedisCache());
            case 15:
                return new AuthGoogleRequest(authConfig, getJustAuthStateRedisCache());
            case 16:
                return new AuthFacebookRequest(authConfig, getJustAuthStateRedisCache());
            case 17:
                return new AuthDouyinRequest(authConfig, getJustAuthStateRedisCache());
            case 18:
                return new AuthLinkedinRequest(authConfig, getJustAuthStateRedisCache());
            case 19:
                return new AuthMicrosoftRequest(authConfig, getJustAuthStateRedisCache());
            case ErrorCodeMapperBuilderOrdered.CAPTCHA /* 20 */:
                return new AuthMiRequest(authConfig, getJustAuthStateRedisCache());
            case 21:
                return new AuthToutiaoRequest(authConfig, getJustAuthStateRedisCache());
            case 22:
                return new AuthTeambitionRequest(authConfig, getJustAuthStateRedisCache());
            case 23:
                return new AuthRenrenRequest(authConfig, getJustAuthStateRedisCache());
            case 24:
                return new AuthPinterestRequest(authConfig, getJustAuthStateRedisCache());
            case 25:
                return new AuthStackOverflowRequest(authConfig, getJustAuthStateRedisCache());
            case 26:
                return new AuthHuaweiV3Request(authConfig, getJustAuthStateRedisCache());
            case 27:
                return new AuthGitlabRequest(authConfig, getJustAuthStateRedisCache());
            case 28:
                return new AuthKujialeRequest(authConfig, getJustAuthStateRedisCache());
            case 29:
                return new AuthElemeRequest(authConfig, getJustAuthStateRedisCache());
            case ErrorCodeMapperBuilderOrdered.OAUTH2 /* 30 */:
                return new AuthMeituanRequest(authConfig, getJustAuthStateRedisCache());
            case 31:
                return new AuthTwitterRequest(authConfig, getJustAuthStateRedisCache());
            case 32:
                return new AuthFeishuRequest(authConfig, getJustAuthStateRedisCache());
            case 33:
                return new AuthJdRequest(authConfig, getJustAuthStateRedisCache());
            case 34:
                return new AuthAliyunRequest(authConfig, getJustAuthStateRedisCache());
            case 35:
                return new AuthXmlyRequest(authConfig, getJustAuthStateRedisCache());
            default:
                return null;
        }
    }
}
