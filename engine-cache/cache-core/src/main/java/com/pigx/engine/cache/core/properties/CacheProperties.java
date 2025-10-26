package com.pigx.engine.core.properties;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.google.common.base.MoreObjects;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = BaseConstants.PROPERTY_PREFIX_CACHE)
/* loaded from: cache-core-3.5.7.0.jar:cn/herodotus/engine/cache/core/properties/CacheProperties.class */
public class CacheProperties extends CacheSetting {
    private Boolean allowNullValues = true;
    private String separator = "-";
    private Map<String, CacheSetting> instances = new HashMap();

    public Boolean getAllowNullValues() {
        return this.allowNullValues;
    }

    public void setAllowNullValues(Boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }

    public Map<String, CacheSetting> getInstances() {
        return this.instances;
    }

    public void setInstances(Map<String, CacheSetting> instances) {
        this.instances = instances;
    }

    public String getSeparator() {
        return this.separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    @Override // com.pigx.engine.cache.core.properties.CacheSetting
    public String toString() {
        return MoreObjects.toStringHelper(this).add("allowNullValues", this.allowNullValues).add("separator", this.separator).toString();
    }
}
