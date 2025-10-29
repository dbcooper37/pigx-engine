package com.pigx.engine.oss.rest.minio.request.object;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.oss.dialect.minio.enums.QuotaUnitEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


@Schema(name = "设置存储桶配额请求参数实体", title = "设置存储桶配额请求参数实体")
public class SetBucketQuotaRequest implements BaseEntity {

    @Schema(name = "存储桶名称")
    @NotBlank(message = "存储桶名称不能为空")
    private String bucketName;

    @Schema(name = "配额大小")
    @Min(value = 0, message = "配额大小不能小于 0")
    private Long size;

    @Schema(name = "配额单位", description = "配额单位目前支持 KB、MB、GB、TB")
    private QuotaUnitEnums unit = QuotaUnitEnums.MB;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public QuotaUnitEnums getUnit() {
        return unit;
    }

    public void setUnit(QuotaUnitEnums unit) {
        this.unit = unit;
    }
}
