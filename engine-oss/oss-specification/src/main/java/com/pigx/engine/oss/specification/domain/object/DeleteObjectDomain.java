package com.pigx.engine.oss.specification.domain.object;

import com.pigx.engine.oss.specification.arguments.object.DeletedObjectArguments;


public class DeleteObjectDomain extends DeletedObjectArguments {

    public DeleteObjectDomain() {
        super();
    }

    public DeleteObjectDomain(String objectName) {
        super(objectName);
    }
}
