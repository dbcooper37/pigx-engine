package com.pigx.engine.oauth2.extension.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;


@Entity
@Table(name = "oauth2_user_logging", indexes = {
        @Index(name = "oauth2_user_logging_id_idx", columnList = "logging_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_COMPLIANCE)
public class OAuth2UserLogging extends AbstractAuditEntity {

    @Id
    @UuidGenerator
    @Column(name = "logging_id", length = 64)
    private String loggingId;

    @Column(name = "principal_name", length = 128)
    private String principalName;

    @Column(name = "client_id", length = 100)
    private String clientId;

    @Column(name = "ip_address", length = 50)
    private String ip;

    @Column(name = "is_mobile")
    private Boolean mobile = false;

    @Column(name = "os_name", length = 200)
    private String osName;

    @Column(name = "browser_name", length = 50)
    private String browserName;
    @Column(name = "is_mobile_browser")
    private Boolean mobileBrowser = false;

    @Column(name = "engine_name", length = 50)
    private String engineName;

    @Column(name = "is_mobile_platform")
    private Boolean mobilePlatform = false;

    @Column(name = "is_iphone_or_ipod")
    private Boolean iphoneOrIpod = false;

    @Column(name = "is_ipad")
    private Boolean ipad = false;

    @Column(name = "is_ios")
    private Boolean ios = false;

    @Column(name = "is_android")
    private Boolean android = false;

    @Column(name = "operation")
    private String operation;

    public String getLoggingId() {
        return loggingId;
    }

    public void setLoggingId(String complianceId) {
        this.loggingId = complianceId;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getMobile() {
        return mobile;
    }

    public void setMobile(Boolean mobile) {
        this.mobile = mobile;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public Boolean getMobileBrowser() {
        return mobileBrowser;
    }

    public void setMobileBrowser(Boolean mobileBrowser) {
        this.mobileBrowser = mobileBrowser;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public Boolean getMobilePlatform() {
        return mobilePlatform;
    }

    public void setMobilePlatform(Boolean mobilePlatform) {
        this.mobilePlatform = mobilePlatform;
    }

    public Boolean getIphoneOrIpod() {
        return iphoneOrIpod;
    }

    public void setIphoneOrIpod(Boolean iphoneOrIpod) {
        this.iphoneOrIpod = iphoneOrIpod;
    }

    public Boolean getIpad() {
        return ipad;
    }

    public void setIpad(Boolean ipad) {
        this.ipad = ipad;
    }

    public Boolean getIos() {
        return ios;
    }

    public void setIos(Boolean ios) {
        this.ios = ios;
    }

    public Boolean getAndroid() {
        return android;
    }

    public void setAndroid(Boolean android) {
        this.android = android;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OAuth2UserLogging that = (OAuth2UserLogging) o;
        return Objects.equal(loggingId, that.loggingId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(loggingId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("complianceId", loggingId)
                .add("principalName", principalName)
                .add("clientId", clientId)
                .add("ip", ip)
                .add("mobile", mobile)
                .add("osName", osName)
                .add("browserName", browserName)
                .add("mobileBrowser", mobileBrowser)
                .add("engineName", engineName)
                .add("mobilePlatform", mobilePlatform)
                .add("iphoneOrIpod", iphoneOrIpod)
                .add("ipad", ipad)
                .add("ios", ios)
                .add("android", android)
                .add("operation", operation)
                .toString();
    }
}
