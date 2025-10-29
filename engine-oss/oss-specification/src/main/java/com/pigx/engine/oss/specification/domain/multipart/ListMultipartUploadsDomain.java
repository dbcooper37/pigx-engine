package com.pigx.engine.oss.specification.domain.multipart;

import com.pigx.engine.oss.specification.arguments.multipart.ListMultipartUploadsArguments;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;


@Schema(name = "分片上传列表返回结果域对象", title = "分片上传列表返回结果域对象")
public class ListMultipartUploadsDomain extends ListMultipartUploadsArguments {

    private boolean isTruncated;

    private String nextKeyMarker;

    private String nextUploadIdMarker;

    private List<UploadDomain> multipartUploads = new ArrayList<>();

    private List<String> commonPrefixes = new ArrayList<>();

    public boolean isTruncated() {
        return isTruncated;
    }

    public void setTruncated(boolean truncated) {
        isTruncated = truncated;
    }

    public String getNextKeyMarker() {
        return nextKeyMarker;
    }

    public void setNextKeyMarker(String nextKeyMarker) {
        this.nextKeyMarker = nextKeyMarker;
    }

    public String getNextUploadIdMarker() {
        return nextUploadIdMarker;
    }

    public void setNextUploadIdMarker(String nextUploadIdMarker) {
        this.nextUploadIdMarker = nextUploadIdMarker;
    }

    public List<UploadDomain> getMultipartUploads() {
        return multipartUploads;
    }

    public void setMultipartUploads(List<UploadDomain> multipartUploads) {
        this.multipartUploads = multipartUploads;
    }

    public List<String> getCommonPrefixes() {
        return commonPrefixes;
    }

    public void setCommonPrefixes(List<String> commonPrefixes) {
        this.commonPrefixes = commonPrefixes;
    }
}
