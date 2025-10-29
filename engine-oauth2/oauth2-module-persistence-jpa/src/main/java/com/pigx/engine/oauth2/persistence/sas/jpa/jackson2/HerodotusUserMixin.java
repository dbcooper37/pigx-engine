package com.pigx.engine.oauth2.persistence.sas.jpa.jackson2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pigx.engine.core.identity.jackson2.HerodotusUserDeserializer;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonDeserialize(using = HerodotusUserDeserializer.class)
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class HerodotusUserMixin {
}
