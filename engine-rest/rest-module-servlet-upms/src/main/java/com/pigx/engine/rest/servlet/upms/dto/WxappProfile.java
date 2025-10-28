package com.pigx.engine.rest.servlet.upms.dto;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.AbstractDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "微信小程序登录请求实体", title = "根据code和appid返回微信小程序session信息")
/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/dto/WxappProfile.class */
public class WxappProfile extends AbstractDto {

    @NotBlank(message = "微信小程序code参数不能为空")
    @Schema(name = SystemConstants.CODE, title = "前端调用小程序自己的方法返回的code")
    private String code;

    @NotBlank(message = "微信小程序appId参数不能为空")
    @Schema(name = "appId", title = "需要前端返回给后端appId，以支持多个小程序")
    private String appId;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
