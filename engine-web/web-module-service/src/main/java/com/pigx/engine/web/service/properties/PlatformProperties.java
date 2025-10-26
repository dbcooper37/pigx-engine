package com.pigx.engine.web.service.properties;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.definition.enums.Protocol;
import com.pigx.engine.core.foundation.enums.Architecture;
import com.pigx.engine.core.foundation.enums.DataAccessStrategy;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = BaseConstants.PROPERTY_PREFIX_PLATFORM)
/* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/properties/PlatformProperties.class */
public class PlatformProperties {
    private Architecture architecture = Architecture.MONOCOQUE;
    private DataAccessStrategy dataAccessStrategy = DataAccessStrategy.REMOTE;
    private Protocol protocol = Protocol.HTTP;
    private Swagger swagger = new Swagger();

    public Architecture getArchitecture() {
        return this.architecture;
    }

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }

    public DataAccessStrategy getDataAccessStrategy() {
        return this.dataAccessStrategy;
    }

    public void setDataAccessStrategy(DataAccessStrategy dataAccessStrategy) {
        this.dataAccessStrategy = dataAccessStrategy;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Swagger getSwagger() {
        return this.swagger;
    }

    public void setSwagger(Swagger swagger) {
        this.swagger = swagger;
    }

    /* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/properties/PlatformProperties$Swagger.class */
    public static class Swagger {
        private Boolean enabled;

        public Boolean getEnabled() {
            return this.enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add(BaseConstants.PROPERTY_NAME_ENABLED, this.enabled).toString();
        }
    }
}
