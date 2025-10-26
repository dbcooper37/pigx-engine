package com.pigx.engine.logic.upms.entity.security;

import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.domain.generator.SysInterfaceIdGenerator;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Table(name = "sys_interface", indexes = {@Index(name = "sys_interface_id_idx", columnList = "interface_id")})
@Schema(name = "系统应用接口")
@Entity
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/entity/security/SysInterface.class */
public class SysInterface extends AbstractSysEntity {

    @Id
    @Schema(name = "接口ID")
    @SysInterfaceIdGenerator
    @Column(name = "interface_id", length = 64)
    private String interfaceId;

    @Column(name = "interface_code", length = 128)
    @Schema(name = "接口代码")
    private String interfaceCode;

    @Column(name = "request_method", length = ErrorCodeMapperBuilderOrdered.CAPTCHA)
    @Schema(name = "请求方法")
    private String requestMethod;

    @Column(name = "service_id", length = 128)
    @Schema(name = "服务ID")
    private String serviceId;

    @Column(name = "class_name", length = 512)
    @Schema(name = "接口所在类")
    private String className;

    @Column(name = "method_name", length = 128)
    @Schema(name = "接口对应方法")
    private String methodName;

    @Column(name = "url", length = 2048)
    @Schema(name = "请求URL")
    private String url;

    public String getInterfaceId() {
        return this.interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceCode() {
        return this.interfaceCode;
    }

    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    public String getRequestMethod() {
        return this.requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysInterface that = (SysInterface) o;
        return Objects.equal(this.interfaceId, that.interfaceId) && Objects.equal(this.serviceId, that.serviceId);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.interfaceId, this.serviceId});
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("interfaceId", this.interfaceId).add("interfaceCode", this.interfaceCode).add("requestMethod", this.requestMethod).add("serviceId", this.serviceId).add("className", this.className).add("methodName", this.methodName).add("url", this.url).toString();
    }
}
