package com.pigx.engine.logic.upms.entity.security;

import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.constants.LogicUpmsConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_ELEMENT)
@Cacheable
@Entity
@Table(name = "sys_element", indexes = {@Index(name = "sys_element_id_idx", columnList = "element_id")})
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/entity/security/SysElement.class */
public class SysElement extends AbstractSysEntity {

    @Id
    @Column(name = "element_id", length = 64)
    @UuidGenerator
    private String elementId;

    @Column(name = "parent_id", length = 64)
    private String parentId;

    @Column(name = "path", length = 512)
    private String path;

    @Column(name = "name", length = ErrorCodeMapperBuilderOrdered.MESSAGE)
    private String name;

    @Column(name = "component", length = 512)
    private String component;

    @Column(name = "redirect", length = 512)
    private String redirect;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "type", length = ErrorCodeMapperBuilderOrdered.MESSAGE)
    private String type;

    @Column(name = "icon", length = 100)
    private String icon;

    @JsonProperty("isHaveChild")
    @Column(name = "have_child")
    private Boolean haveChild = false;

    @JsonProperty("isNotKeepAlive")
    @Column(name = "not_keep_alive")
    private Boolean notKeepAlive = false;

    @JsonProperty("isHideAllChild")
    @Column(name = "hide_all_child")
    private Boolean hideAllChild = false;

    @JsonProperty("isDetailContent")
    @Column(name = "detail_content")
    private Boolean detailContent = false;

    @JsonProperty("isIgnoreAuth")
    @Column(name = "ignore_auth")
    private Boolean ignoreAuth = false;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_ROLE)
    @Schema(name = "元素角色")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_element_role", joinColumns = {@JoinColumn(name = "element_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"element_id", "role_id"})}, indexes = {@Index(name = "sys_element_role_eid_idx", columnList = "element_id"), @Index(name = "sys_element_role_rid_idx", columnList = "role_id")})
    @Fetch(FetchMode.SUBSELECT)
    private Set<SysRole> roles = new HashSet();

    public String getElementId() {
        return this.elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponent() {
        return this.component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return this.redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getHaveChild() {
        return this.haveChild;
    }

    public void setHaveChild(Boolean haveChild) {
        this.haveChild = haveChild;
    }

    public Boolean getNotKeepAlive() {
        return this.notKeepAlive;
    }

    public void setNotKeepAlive(Boolean notKeepAlive) {
        this.notKeepAlive = notKeepAlive;
    }

    public Boolean getHideAllChild() {
        return this.hideAllChild;
    }

    public void setHideAllChild(Boolean hideAllChild) {
        this.hideAllChild = hideAllChild;
    }

    public Boolean getDetailContent() {
        return this.detailContent;
    }

    public void setDetailContent(Boolean detailContent) {
        this.detailContent = detailContent;
    }

    public Boolean getIgnoreAuth() {
        return this.ignoreAuth;
    }

    public void setIgnoreAuth(Boolean ignoreAuth) {
        this.ignoreAuth = ignoreAuth;
    }

    public Set<SysRole> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysElement sysMenu = (SysElement) o;
        return Objects.equal(this.elementId, sysMenu.elementId);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.elementId});
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("elementId", this.elementId).add("parentId", this.parentId).add("path", this.path).add("name", this.name).add("component", this.component).add("redirect", this.redirect).add("title", this.title).add("type", this.type).add("icon", this.icon).add("haveChild", this.haveChild).add("notKeepAlive", this.notKeepAlive).add("hideAllChild", this.hideAllChild).add("detailContent", this.detailContent).add("ignoreAuth", this.ignoreAuth).add(SystemConstants.ROLES, this.roles).toString();
    }
}
