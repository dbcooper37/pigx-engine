package com.pigx.engine.oss.dialect.minio.domain.policy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.pigx.engine.core.definition.domain.BaseEntity;

import java.util.List;


public class PrincipalDomain implements BaseEntity {

    @JsonProperty("AWS")
    private List<String> aws = Lists.newArrayList("*");

    public List<String> getAws() {
        return aws;
    }

    public void setAws(List<String> aws) {
        this.aws = aws;
    }
}
