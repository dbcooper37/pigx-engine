package com.pigx.engine.logic.upms.entity.security;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.domain.generator.SysInterfaceIdGenerator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;


@Schema(name = "系统应用接口")
@Entity
@Table(name = "sys_interface", indexes = {@Index(name = "sys_interface_id_idx", columnList = "interface_id")})
public class SysInterface extends AbstractSysEntity {

    @Schema(name = "接口ID")
    @Id
    @SysInterfaceIdGenerator
    @Column(name = "interface_id", length = 64)
    private String interfaceId;

    @Schema(name = "接口代码")
    @Column(name = "interface_code", length = 128)
    private String interfaceCode;

    @Schema(name = "请求方法")
    @Column(name = "request_method", length = 20)
    private String requestMethod;

    @Schema(name = "服务ID")
    @Column(name = "service_id", length = 128)
    private String serviceId;

    @Schema(name = "接口所在类")
    @Column(name = "class_name", length = 512)
    private String className;

    @Schema(name = "接口对应方法")
    @Column(name = "method_name", length = 128)
    private String methodName;

    @Schema(name = "请求URL")
    @Column(name = "url", length = 2048)
    private String url;

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysInterface that = (SysInterface) o;
        return Objects.equal(interfaceId, that.interfaceId) && Objects.equal(serviceId, that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(interfaceId, serviceId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("interfaceId", interfaceId)
                .add("interfaceCode", interfaceCode)
                .add("requestMethod", requestMethod)
                .add("serviceId", serviceId)
                .add("className", className)
                .add("methodName", methodName)
                .add("url", url)
                .toString();
    }
}
