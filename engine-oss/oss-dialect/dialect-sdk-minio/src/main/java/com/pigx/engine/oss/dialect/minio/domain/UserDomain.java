package com.pigx.engine.oss.dialect.minio.domain;

import com.pigx.engine.core.definition.domain.BaseEntity;
import io.minio.admin.Status;

import java.util.List;


public class UserDomain implements BaseEntity {

    private String accessKey;

    private String secretKey;

    private String policyName;

    private List<String> memberOf;

    private Status status;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public List<String> getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(List<String> memberOf) {
        this.memberOf = memberOf;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
