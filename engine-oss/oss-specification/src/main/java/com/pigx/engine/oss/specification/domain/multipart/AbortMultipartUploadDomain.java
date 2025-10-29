package com.pigx.engine.oss.specification.domain.multipart;

import com.pigx.engine.oss.specification.domain.base.MultipartUploadDomain;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "中止分片上传返回结果", title = "中止分片上传返回结果")
public class AbortMultipartUploadDomain extends MultipartUploadDomain {
}
