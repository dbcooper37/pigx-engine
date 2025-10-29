package com.pigx.engine.oss.solution.business;

import com.pigx.engine.oss.solution.definition.OssBusiness;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;


@Schema(name = "创建分片上传返回结果业务对象", title = "创建分片上传返回结果业务对象")
public class CreateMultipartUploadBusiness implements OssBusiness {

    @Schema(name = "上传ID")
    private String uploadId;

    @Schema(name = "分片上传URL", description = "分片上传所有分片对相应的预签名地址")
    private List<String> uploadUrls;

    public CreateMultipartUploadBusiness(String uploadId) {
        this.uploadId = uploadId;
        this.uploadUrls = new ArrayList<>();
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public List<String> getUploadUrls() {
        return uploadUrls;
    }

    public void setUploadUrls(List<String> uploadUrls) {
        this.uploadUrls = uploadUrls;
    }

    public void append(String uploadUrl) {
        uploadUrls.add(uploadUrls.size(), uploadUrl);
    }
}
