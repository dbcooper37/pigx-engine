package com.pigx.engine.oss.dialect.minio.domain;

import com.pigx.engine.core.definition.domain.BaseEntity;


public class VersioningConfigurationDomain implements BaseEntity {

    private String status;

    private Boolean mfaDelete;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getMfaDelete() {
        return mfaDelete;
    }

    public void setMfaDelete(Boolean mfaDelete) {
        this.mfaDelete = mfaDelete;
    }
}
