package com.pigx.engine.oss.dialect.minio.domain.policy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pigx.engine.core.definition.domain.BaseEntity;

import java.util.List;


public class StatementDomain implements BaseEntity {

    @JsonProperty("Effect")
    private String effect = "Allow";

    @JsonProperty("Action")
    private List<String> actions;

    @JsonProperty("Resource")
    private List<String> resources;

    @JsonProperty("Principal")
    private PrincipalDomain principal = new PrincipalDomain();

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public PrincipalDomain getPrincipal() {
        return principal;
    }

    public void setPrincipal(PrincipalDomain principalDomain) {
        this.principal = principalDomain;
    }
}
