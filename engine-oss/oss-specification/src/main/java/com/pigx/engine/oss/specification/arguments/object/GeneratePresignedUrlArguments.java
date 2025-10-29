package com.pigx.engine.oss.specification.arguments.object;

import com.pigx.engine.oss.specification.arguments.base.ObjectVersionArguments;
import com.pigx.engine.oss.specification.enums.HttpMethod;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Duration;


@Schema(name = "生成预签名URL请求参数实体", title = "生成预签名URL请求参数实体")
public class GeneratePresignedUrlArguments extends ObjectVersionArguments {

    @Schema(name = "对象保留模式", title = "存储模式的值只能是大写 GOVERNANCE 或者 COMPLIANCE")
    private HttpMethod method = HttpMethod.PUT;

    @Schema(name = "过期时间", type = "integer", title = "单位为秒，默认值为 7 天")
    private Duration expiration = Duration.ofDays(7);

    /**
     * Content-Type to url sign
     */
    private String contentType;

    /**
     * Content-MD5
     */
    private String contentMD5;

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Duration getExpiration() {
        return expiration;
    }

    public void setExpiration(Duration expiration) {
        this.expiration = expiration;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentMD5() {
        return contentMD5;
    }

    public void setContentMD5(String contentMD5) {
        this.contentMD5 = contentMD5;
    }
}
