package com.pigx.engine.oss.solution.service;

import com.pigx.engine.oss.solution.business.CreateMultipartUploadBusiness;
import com.pigx.engine.oss.solution.properties.OssProxyProperties;
import com.pigx.engine.oss.solution.proxy.OssProxyAddressConverter;
import com.pigx.engine.oss.specification.arguments.object.GeneratePresignedUrlArguments;
import com.pigx.engine.oss.specification.core.repository.OssMultipartUploadRepository;
import com.pigx.engine.oss.specification.core.repository.OssObjectRepository;
import com.pigx.engine.oss.specification.domain.base.ObjectWriteDomain;
import com.pigx.engine.oss.specification.domain.multipart.CompleteMultipartUploadDomain;
import com.pigx.engine.oss.specification.domain.multipart.ListPartsDomain;
import com.pigx.engine.oss.specification.domain.multipart.PartSummaryDomain;
import com.pigx.engine.oss.specification.enums.HttpMethod;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OssMultipartUploadService {

    private final OssObjectRepository ossObjectRepository;
    private final OssMultipartUploadRepository ossMultipartUploadRepository;
    private final Converter<String, String> converter;

    public OssMultipartUploadService(OssObjectRepository ossObjectRepository, OssMultipartUploadRepository ossMultipartUploadRepository, OssProxyProperties ossProxyProperties) {
        this.ossObjectRepository = ossObjectRepository;
        this.ossMultipartUploadRepository = ossMultipartUploadRepository;
        this.converter = new OssProxyAddressConverter(ossProxyProperties);
    }

    /**
     * 第一步：创建分片上传请求, 返回 UploadId
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return 大文件分片上传 UploadId
     */
    private String createUploadId(String bucketName, String objectName) {
        return ossMultipartUploadRepository.initiateMultipartUpload(bucketName, objectName);
    }

    /**
     * 第二步：创建文件预上传地址
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param uploadId   第一步中创建的 UploadId
     * @param partNumber 分片号
     * @return 预上传地址
     */
    private String createPresignedObjectUrl(String bucketName, String objectName, String uploadId, int partNumber) {
        Map<String, String> extraQueryParams = new HashMap<>();
        extraQueryParams.put("partNumber", String.valueOf(partNumber));
        extraQueryParams.put("uploadId", uploadId);

        GeneratePresignedUrlArguments arguments = new GeneratePresignedUrlArguments();
        arguments.setBucketName(bucketName);
        arguments.setObjectName(objectName);
        arguments.setMethod(HttpMethod.PUT);
        arguments.setExtraQueryParams(extraQueryParams);
        arguments.setExpiration(Duration.ofHours(1));
        return ossObjectRepository.generatePresignedUrl(arguments);
    }

    /**
     * 第三步：获取指定 uploadId 下所有的分片文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param uploadId   第一步中创建的 UploadId
     * @return uploadId 对应的所有分片
     */
    private List<PartSummaryDomain> listParts(String bucketName, String objectName, String uploadId) {
        ListPartsDomain domain = ossMultipartUploadRepository.listParts(bucketName, objectName, uploadId);
        return domain.getParts();
    }

    /**
     * 创建大文件分片上传
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param totalParts 分片总数
     * @return {@link CreateMultipartUploadBusiness}
     */
    public CreateMultipartUploadBusiness createMultipartUpload(String bucketName, String objectName, int totalParts) {
        String uploadId = createUploadId(bucketName, objectName);
        CreateMultipartUploadBusiness entity = new CreateMultipartUploadBusiness(uploadId);

        // 从 1 开始才能保证 Minio 正确上传。
        for (int i = 1; i <= totalParts; i++) {
            String uploadUrl = createPresignedObjectUrl(bucketName, objectName, uploadId, i);
            entity.append(converter.convert(uploadUrl));
        }
        return entity;
    }

    /**
     * 合并已经上传完成的分片
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param uploadId   第一步中创建的 UploadId
     * @return {@link ObjectWriteDomain}
     */
    public CompleteMultipartUploadDomain completeMultipartUpload(String bucketName, String objectName, String uploadId) {
        List<PartSummaryDomain> parts = listParts(bucketName, objectName, uploadId);
        if (CollectionUtils.isNotEmpty(parts)) {
            return ossMultipartUploadRepository.completeMultipartUpload(bucketName, objectName, uploadId, parts);
        }

        return null;
    }
}
