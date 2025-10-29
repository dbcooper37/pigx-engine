package com.pigx.engine.oss.specification.arguments.object;

import com.pigx.engine.oss.specification.arguments.base.ObjectVersionArguments;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "删除对象请求参数实体", title = "删除对象请求参数实体")
public class DeleteObjectArguments extends ObjectVersionArguments {

    @Schema(name = "使用治理模式进行删除", description = "治理模式用户不能覆盖或删除对象版本或更改其锁定设置，可通过设置该参数进行强制操作")
    private Boolean bypassGovernanceMode;

    public Boolean getBypassGovernanceMode() {
        return bypassGovernanceMode;
    }

    public void setBypassGovernanceMode(Boolean bypassGovernanceMode) {
        this.bypassGovernanceMode = bypassGovernanceMode;
    }

}
