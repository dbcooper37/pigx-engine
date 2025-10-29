package com.pigx.engine.oss.solution.properties;

import com.pigx.engine.oss.solution.constants.OssSolutionConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = OssSolutionConstants.PROPERTY_OSS_PROXY)
public class OssProxyProperties {

    /**
     * 是否开启。默认开水器
     */
    private Boolean enabled = true;

    /**
     * 代理请求转发源地址。例如：前端 http://localhost:3000。注意如果有前端有配置代理需要加上
     */
    private String source;

    /**
     * 代理请求转发目的地址
     */
    private String destination;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
