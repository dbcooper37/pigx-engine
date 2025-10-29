package com.pigx.engine.oss.specification.domain.object;

import com.pigx.engine.oss.specification.domain.base.BaseDomain;

import java.io.InputStream;


public class GetObjectDomain extends BaseDomain {

    private InputStream objectContent;

    public InputStream getObjectContent() {
        return objectContent;
    }

    public void setObjectContent(InputStream objectContent) {
        this.objectContent = objectContent;
    }
}
