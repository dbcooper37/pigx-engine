package com.pigx.engine.core.identity.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.zhyd.oauth.config.AuthDefaultSource;

@Schema(
        name = "账号类型"
)
@JsonFormat(
        shape = JsonFormat.Shape.OBJECT
)
public enum AccountCategory {
    INSTITUTION("INSTITUTION", "", "机构人员"),
    SMS("SMS", "PHONE_NUMBER", "手机验证码"),
    WXAPP("WXAPP", "WECHAT_MINI_APP", "微信小程序"),
    QQ(AuthDefaultSource.QQ.name(), "JUST_AUTH", "QQ"),
    WEIBO(AuthDefaultSource.WEIBO.name(), "JUST_AUTH", "微博"),
    BAIDU(AuthDefaultSource.BAIDU.name(), "JUST_AUTH", "百度"),
    WECHAT_OPEN(AuthDefaultSource.WECHAT_OPEN.name(), "JUST_AUTH", "微信开放平台"),
    WECHAT_MP(AuthDefaultSource.WECHAT_MP.name(), "JUST_AUTH", "微信公众号"),
    WECHAT_ENTERPRISE(AuthDefaultSource.WECHAT_ENTERPRISE.name(), "JUST_AUTH", "企业微信二维码"),
    WECHAT_ENTERPRISE_WEB(AuthDefaultSource.WECHAT_ENTERPRISE_WEB.name(), "JUST_AUTH", "企业微信网页"),
    DINGTALK(AuthDefaultSource.DINGTALK.name(), "JUST_AUTH", "钉钉"),
    DINGTALK_ACCOUNT(AuthDefaultSource.DINGTALK_ACCOUNT.name(), "JUST_AUTH", "钉钉账号"),
    ALIYUN(AuthDefaultSource.ALIYUN.name(), "JUST_AUTH", "阿里云"),
    TAOBAO(AuthDefaultSource.TAOBAO.name(), "JUST_AUTH", "淘宝"),
    ALIPAY(AuthDefaultSource.ALIPAY.name(), "JUST_AUTH", "支付宝"),
    TEAMBITION(AuthDefaultSource.TEAMBITION.name(), "JUST_AUTH", "Teambition"),
    HUAWEI_V3(AuthDefaultSource.HUAWEI_V3.name(), "JUST_AUTH", "华为"),
    FEISHU(AuthDefaultSource.FEISHU.name(), "JUST_AUTH", "飞书"),
    JD(AuthDefaultSource.JD.name(), "JUST_AUTH", "京东"),
    DOUYIN(AuthDefaultSource.DOUYIN.name(), "JUST_AUTH", "抖音"),
    TOUTIAO(AuthDefaultSource.TOUTIAO.name(), "JUST_AUTH", "今日头条"),
    MI(AuthDefaultSource.MI.name(), "JUST_AUTH", "小米"),
    RENREN(AuthDefaultSource.RENREN.name(), "JUST_AUTH", "人人"),
    MEITUAN(AuthDefaultSource.MEITUAN.name(), "JUST_AUTH", "美团"),
    ELEME(AuthDefaultSource.ELEME.name(), "JUST_AUTH", "饿了么"),
    KUJIALE(AuthDefaultSource.KUJIALE.name(), "JUST_AUTH", "酷家乐"),
    XMLY(AuthDefaultSource.XMLY.name(), "JUST_AUTH", "喜马拉雅"),
    GITEE(AuthDefaultSource.GITEE.name(), "JUST_AUTH", "码云"),
    OSCHINA(AuthDefaultSource.OSCHINA.name(), "JUST_AUTH", "开源中国"),
    CSDN(AuthDefaultSource.CSDN.name(), "JUST_AUTH", "CSDN"),
    GITHUB(AuthDefaultSource.GITHUB.name(), "JUST_AUTH", "Github"),
    GITLAB(AuthDefaultSource.GITLAB.name(), "JUST_AUTH", "Gitlab"),
    STACK_OVERFLOW(AuthDefaultSource.STACK_OVERFLOW.name(), "JUST_AUTH", "Stackoverflow"),
    CODING(AuthDefaultSource.CODING.name(), "JUST_AUTH", "Coding"),
    GOOGLE(AuthDefaultSource.GOOGLE.name(), "JUST_AUTH", "谷歌"),
    MICROSOFT(AuthDefaultSource.MICROSOFT.name(), "JUST_AUTH", "微软"),
    FACEBOOK(AuthDefaultSource.FACEBOOK.name(), "JUST_AUTH", "脸书"),
    LINKEDIN(AuthDefaultSource.LINKEDIN.name(), "JUST_AUTH", "领英"),
    TWITTER(AuthDefaultSource.TWITTER.name(), "JUST_AUTH", "推特"),
    AMAZON(AuthDefaultSource.AMAZON.name(), "JUST_AUTH", "亚马逊"),
    SLACK(AuthDefaultSource.SLACK.name(), "JUST_AUTH", "Slack"),
    LINE(AuthDefaultSource.LINE.name(), "JUST_AUTH", "Line"),
    OKTA(AuthDefaultSource.OKTA.name(), "JUST_AUTH", "Okta"),
    PINTEREST(AuthDefaultSource.PINTEREST.name(), "JUST_AUTH", "Pinterest");

    public static final String JUST_AUTH_HANDLER = "JUST_AUTH";
    public static final String PHONE_NUMBER_HANDLER = "PHONE_NUMBER";
    public static final String WECHAT_MINI_APP_HANDLER = "WECHAT_MINI_APP";
    private static final Map<String, AccountCategory> INDEX_MAP = new HashMap();
    private static final List<ImmutableMap<Object, Object>> JSON_STRUCT = new ArrayList();
    @Schema(
            name = "枚举值"
    )
    private final String key;
    @Schema(
            name = "处理器"
    )
    private final String handler;
    @Schema(
            name = "文字"
    )
    private final String description;

    private AccountCategory(String key, String handler, String description) {
        this.key = key;
        this.handler = handler;
        this.description = description;
    }

    public static AccountCategory getAccountType(String key) {
        return (AccountCategory)INDEX_MAP.get(key);
    }

    public static List<ImmutableMap<Object, Object>> getJsonStruct() {
        return JSON_STRUCT;
    }

    @JsonValue
    public String getKey() {
        return this.key;
    }

    public String getDescription() {
        return this.description;
    }

    public String getHandler() {
        return this.handler;
    }

    static {
        for(AccountCategory accountCategory : values()) {
            INDEX_MAP.put(accountCategory.getKey(), accountCategory);
            JSON_STRUCT.add(accountCategory.ordinal(), ImmutableMap.builder().put("value", accountCategory.ordinal()).put("key", accountCategory.name()).put("text", accountCategory.getDescription()).build());
        }

    }
}