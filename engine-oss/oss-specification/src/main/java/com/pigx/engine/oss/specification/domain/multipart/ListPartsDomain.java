package com.pigx.engine.oss.specification.domain.multipart;

import com.pigx.engine.oss.specification.domain.base.MultipartUploadDomain;
import com.pigx.engine.oss.specification.domain.base.OwnerDomain;

import java.util.List;


public class ListPartsDomain extends MultipartUploadDomain {

    private OwnerDomain owner;

    private OwnerDomain initiator;

    private String storageClass;

    private Integer maxParts;

    private Integer partNumberMarker;

    private Integer nextPartNumberMarker;

    private Boolean isTruncated;

    private List<PartSummaryDomain> parts;

    public OwnerDomain getOwner() {
        return owner;
    }

    public void setOwner(OwnerDomain owner) {
        this.owner = owner;
    }

    public OwnerDomain getInitiator() {
        return initiator;
    }

    public void setInitiator(OwnerDomain initiator) {
        this.initiator = initiator;
    }

    public String getStorageClass() {
        return storageClass;
    }

    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }

    public Integer getMaxParts() {
        return maxParts;
    }

    public void setMaxParts(Integer maxParts) {
        this.maxParts = maxParts;
    }

    public Integer getPartNumberMarker() {
        return partNumberMarker;
    }

    public void setPartNumberMarker(Integer partNumberMarker) {
        this.partNumberMarker = partNumberMarker;
    }

    public Integer getNextPartNumberMarker() {
        return nextPartNumberMarker;
    }

    public void setNextPartNumberMarker(Integer nextPartNumberMarker) {
        this.nextPartNumberMarker = nextPartNumberMarker;
    }

    public Boolean getTruncated() {
        return isTruncated;
    }

    public void setTruncated(Boolean truncated) {
        isTruncated = truncated;
    }

    public List<PartSummaryDomain> getParts() {
        return parts;
    }

    public void setParts(List<PartSummaryDomain> parts) {
        this.parts = parts;
    }
}
