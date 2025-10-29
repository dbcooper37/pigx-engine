package com.pigx.engine.oss.dialect.minio.domain;

import com.pigx.engine.core.definition.domain.BaseEntity;
import io.minio.admin.Status;

import java.util.List;


public class GroupDomain implements BaseEntity {

    private String name;

    private Status status;

    private List<String> members;

    private String policy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }
}
