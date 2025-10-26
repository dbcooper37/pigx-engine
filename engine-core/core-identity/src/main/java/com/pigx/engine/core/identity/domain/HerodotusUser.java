package com.pigx.engine.core.identity.domain;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.jackson2.HerodotusUserDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

@JsonDeserialize(using = HerodotusUserDeserializer.class)
/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/domain/HerodotusUser.class */
public class HerodotusUser implements UserDetails, CredentialsContainer {
    private String userId;
    private String password;
    private String username;
    private Set<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Set<String> roles;
    private String employeeId;
    private String avatar;

    public HerodotusUser() {
    }

    public HerodotusUser(String userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(userId, username, password, authorities, null);
    }

    public HerodotusUser(String userId, String username, String password, Collection<? extends GrantedAuthority> authorities, Set<String> roles) {
        this(userId, username, password, true, true, true, true, authorities, roles, null, null);
    }

    public HerodotusUser(String userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Set<String> roles, String employeeId, String avatar) {
        Assert.isTrue((username == null || SymbolConstants.BLANK.equals(username) || password == null) ? false : true, "Cannot pass null or empty values to constructor");
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        this.roles = CollectionUtils.isNotEmpty(roles) ? roles : new HashSet<>();
        this.employeeId = employeeId;
        this.avatar = avatar;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());
        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }
        return sortedAuthorities;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public void eraseCredentials() {
        this.password = null;
    }

    public String getUserId() {
        return this.userId;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusUser that = (HerodotusUser) o;
        return Objects.equal(this.userId, that.userId) && Objects.equal(this.username, that.username);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.userId, this.username});
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("userId", this.userId).add(SystemConstants.PASSWORD, "[PROTECTED]").add(SystemConstants.USERNAME, this.username).add("accountNonExpired", this.accountNonExpired).add("accountNonLocked", this.accountNonLocked).add("credentialsNonExpired", this.credentialsNonExpired).add(BaseConstants.PROPERTY_NAME_ENABLED, this.enabled).add(SystemConstants.EMPLOYEE_ID, this.employeeId).add(SystemConstants.AVATAR, this.avatar).toString();
    }

    /* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/domain/HerodotusUser$AuthorityComparator.class */
    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = 620;

        private AuthorityComparator() {
        }

        @Override // java.util.Comparator
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            }
            if (g1.getAuthority() == null) {
                return 1;
            }
            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }
}
