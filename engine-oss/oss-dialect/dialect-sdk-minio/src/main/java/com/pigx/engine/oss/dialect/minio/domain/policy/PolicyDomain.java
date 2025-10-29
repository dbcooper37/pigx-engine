package com.pigx.engine.oss.dialect.minio.domain.policy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pigx.engine.core.definition.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;


public class PolicyDomain implements BaseEntity {

    @JsonProperty("Version")
    private String version = "2012-10-17";

    @JsonProperty("Statement")
    private List<StatementDomain> statements = new ArrayList<>();

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<StatementDomain> getStatements() {
        return statements;
    }

    public void setStatements(List<StatementDomain> statements) {
        this.statements = statements;
    }
}
