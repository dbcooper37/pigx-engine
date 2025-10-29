package com.pigx.engine.oss.dialect.autoconfigure.properties;

import com.pigx.engine.oss.dialect.core.constants.OssConstants;
import com.pigx.engine.oss.dialect.core.enums.Dialect;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = OssConstants.PROPERTY_PREFIX_OSS)
public class OssProperties {

    /**
     * 是否启用代理，防止前端直接访问
     */
    private Boolean useProxy = true;

    /**
     * 代理请求发送源地址。例如：前端 http://localhost:3000。注意如果有前端有配置代理需要加上
     */
    private String proxySourceEndpoint;

    /**
     * 采用 Minio SDK 作为默认实现
     */
    private Dialect dialect = Dialect.MINIO;

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }
}
