package com.pigx.engine.oss.solution.constants;


import com.pigx.engine.core.definition.constant.BaseConstants;


public interface OssSolutionConstants extends BaseConstants {

    String PROPERTY_OSS_PROXY = PROPERTY_PREFIX_OSS + ".proxy";

    String OSS_MULTIPART_UPLOAD_REQUEST_MAPPING = "/oss/multipart-upload";
    String OSS_PRESIGNED_OBJECT_REQUEST_MAPPING = "/presigned";
    String OSS_PRESIGNED_OBJECT_PROXY_REQUEST_MAPPING = OSS_PRESIGNED_OBJECT_REQUEST_MAPPING + "/*/*";
    String PRESIGNED_OBJECT_URL_PROXY = OSS_MULTIPART_UPLOAD_REQUEST_MAPPING + OSS_PRESIGNED_OBJECT_REQUEST_MAPPING;
}
