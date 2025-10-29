package com.pigx.engine.web.service.properties;

import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.enums.Protocol;
import com.pigx.engine.core.foundation.enums.Architecture;
import com.pigx.engine.core.foundation.enums.DataAccessStrategy;
import com.pigx.engine.web.core.constant.WebConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = WebConstants.PROPERTY_PREFIX_PLATFORM)
public class PlatformProperties {

    /**
     * 平台架构类型，默认：MONOCOQUE（单体架构）
     */
    private Architecture architecture = Architecture.MONOCOQUE;
    /**
     * 数据访问策略，默认：远程
     */
    private DataAccessStrategy dataAccessStrategy = DataAccessStrategy.REMOTE;
    /**
     * 接口地址默认采用的Http协议类型
     */
    private Protocol protocol = Protocol.HTTP;
    /**
     * Swagger API 文档配置
     */
    private Swagger swagger = new Swagger();

    public Architecture getArchitecture() {
        return architecture;
    }

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }

    public DataAccessStrategy getDataAccessStrategy() {
        return dataAccessStrategy;
    }

    public void setDataAccessStrategy(DataAccessStrategy dataAccessStrategy) {
        this.dataAccessStrategy = dataAccessStrategy;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Swagger getSwagger() {
        return swagger;
    }

    public void setSwagger(Swagger swagger) {
        this.swagger = swagger;
    }

    public static class Swagger {

        /**
         * 是否开启Swagger
         */
        private Boolean enabled;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("enabled", enabled)
                    .toString();
        }
    }
}
