package com.pigx.engine.oss.specification.arguments.bucket;

import com.pigx.engine.oss.specification.arguments.base.BucketArguments;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "创建存储桶请求参数实体", title = "创建存储桶请求参数实体")
public class CreateBucketArguments extends BucketArguments {

    @Schema(name = "开启对象锁定", description = "仅在Minio环境下使用")
    private Boolean objectLock;

    public Boolean getObjectLock() {
        return objectLock;
    }

    public void setObjectLock(Boolean objectLock) {
        this.objectLock = objectLock;
    }
}
