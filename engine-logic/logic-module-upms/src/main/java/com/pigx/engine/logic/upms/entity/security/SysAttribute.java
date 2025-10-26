package com.pigx.engine.logic.upms.entity.security;

import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.domain.generator.SysAttributeIdGenerator;
import com.pigx.engine.logic.upms.domain.listener.SysAttributeEntityListener;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Schema(name = "系统安全属性数据")
@Entity
@EntityListeners({SysAttributeEntityListener.class})
@Table(name = "sys_attribute", indexes = {@Index(name = "sys_attribute_id_idx", columnList = "attribute_id")})
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/entity/security/SysAttribute.class */
public class SysAttribute extends AbstractSysEntity {

    @Id
    @Schema(name = "元数据ID")
    @Column(name = "attribute_id", length = 64)
    @SysAttributeIdGenerator
    private String attributeId;

    @Column(name = "attribute_code", length = 128)
    @Schema(name = "默认权限代码")
    private String attributeCode;

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

    @Column(name = "web_expression", length = 128)
    @Schema(name = "表达式", description = "Security表达式字符串，通过该值设置动态权限")
    private String webExpression;

    @Schema(name = "属性对应权限", title = "根据属性关联权限数据")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_attribute_permission", joinColumns = {@JoinColumn(name = "attribute_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"attribute_id", "permission_id"})}, indexes = {@Index(name = "sys_attribute_permission_aid_idx", columnList = "attribute_id"), @Index(name = "sys_attribute_permission_pid_idx", columnList = "permission_id")})
    @Fetch(FetchMode.SUBSELECT)
    private Set<SysPermission> permissions = new HashSet();

    public String getAttributeId() {
        return this.attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeCode() {
        return this.attributeCode;
    }

    public void setAttributeCode(String attributeCode) {
        this.attributeCode = attributeCode;
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

    public String getWebExpression() {
        return this.webExpression;
    }

    public void setWebExpression(String webExpression) {
        this.webExpression = webExpression;
    }

    public Set<SysPermission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Set<SysPermission> permissions) {
        this.permissions = permissions;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysAttribute that = (SysAttribute) o;
        return Objects.equal(this.attributeId, that.attributeId);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.attributeId});
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("attributeId", this.attributeId).add("attributeCode", this.attributeCode).add("requestMethod", this.requestMethod).add("serviceId", this.serviceId).add("className", this.className).add("methodName", this.methodName).add("url", this.url).add("webExpression", this.webExpression).toString();
    }
}
