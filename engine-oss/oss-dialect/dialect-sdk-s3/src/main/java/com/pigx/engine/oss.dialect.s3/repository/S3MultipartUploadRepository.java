package com.pigx.engine.oss.dialect.s3.repository;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.pigx.engine.core.definition.support.AbstractObjectPool;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.s3.converter.arguments.*;
import com.pigx.engine.oss.dialect.s3.converter.domain.*;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import com.pigx.engine.oss.specification.arguments.multipart.*;
import com.pigx.engine.oss.specification.core.repository.OssMultipartUploadRepository;
import com.pigx.engine.oss.specification.domain.multipart.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;


public class S3MultipartUploadRepository extends BaseS3Service implements OssMultipartUploadRepository {

    private static final Logger log = LoggerFactory.getLogger(S3MultipartUploadRepository.class);

    public S3MultipartUploadRepository(AbstractObjectPool<AmazonS3> ossClientObjectPool) {
        super(ossClientObjectPool);
    }

    @Override
    public InitiateMultipartUploadDomain initiateMultipartUpload(InitiateMultipartUploadArguments arguments) {

        String function = "initiateMultipartUpload";

        Converter<InitiateMultipartUploadArguments, InitiateMultipartUploadRequest> toRequest = new ArgumentsToInitiateMultipartUploadRequestConverter();
        Converter<InitiateMultipartUploadResult, InitiateMultipartUploadDomain> toDomain = new InitiateMultipartUploadResultToDomainConverter();

        AmazonS3 client = getClient();
        try {
            InitiateMultipartUploadResult result = client.initiateMultipartUpload(toRequest.convert(arguments));
            return toDomain.convert(result);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public UploadPartDomain uploadPart(UploadPartArguments arguments) {

        String function = "uploadPart";

        Converter<UploadPartArguments, UploadPartRequest> toRequest = new ArgumentsToUploadPartRequestConverter();
        Converter<UploadPartResult, UploadPartDomain> toDomain = new UploadPartResultToDomainConverter();

        AmazonS3 client = getClient();

        try {
            UploadPartResult result = client.uploadPart(toRequest.convert(arguments));
            return toDomain.convert(result);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public UploadPartCopyDomain uploadPartCopy(UploadPartCopyArguments arguments) {
        String function = "uploadPartCopy";

        Converter<UploadPartCopyArguments, CopyPartRequest> toRequest = new ArgumentsToCopyPartRequestConverter();
        Converter<CopyPartResult, UploadPartCopyDomain> toDomain = new CopyPartResultToDomainConverter();

        AmazonS3 client = getClient();
        try {
            CopyPartResult result = client.copyPart(toRequest.convert(arguments));
            return toDomain.convert(result);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public CompleteMultipartUploadDomain completeMultipartUpload(CompleteMultipartUploadArguments arguments) {
        String function = "completeMultipartUpload";

        Converter<CompleteMultipartUploadArguments, CompleteMultipartUploadRequest> toRequest = new ArgumentsToCompleteMultipartUploadRequestConverter();
        Converter<CompleteMultipartUploadResult, CompleteMultipartUploadDomain> toDomain = new CompleteMultipartUploadResultToDomainConverter();

        AmazonS3 client = getClient();

        try {
            CompleteMultipartUploadResult result = client.completeMultipartUpload(toRequest.convert(arguments));
            return toDomain.convert(result);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public AbortMultipartUploadDomain abortMultipartUpload(AbortMultipartUploadArguments arguments) {
        String function = "abortMultipartUpload";

        Converter<AbortMultipartUploadArguments, AbortMultipartUploadRequest> toRequest = new ArgumentsToAbortMultipartUploadRequestConverter();

        AmazonS3 client = getClient();

        try {
            client.abortMultipartUpload(toRequest.convert(arguments));
            AbortMultipartUploadDomain domain = new AbortMultipartUploadDomain();
            domain.setUploadId(arguments.getUploadId());
            domain.setBucketName(arguments.getBucketName());
            domain.setObjectName(arguments.getObjectName());
            return domain;
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public ListPartsDomain listParts(ListPartsArguments arguments) {

        String function = "listParts";

        Converter<ListPartsArguments, ListPartsRequest> toRequest = new ArgumentsToListPartsRequestConverter();
        Converter<PartListing, ListPartsDomain> toDomain = new PartListingToDomainConverter();

        AmazonS3 client = getClient();

        try {
            PartListing partListing = client.listParts(toRequest.convert(arguments));
            return toDomain.convert(partListing);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public ListMultipartUploadsDomain listMultipartUploads(ListMultipartUploadsArguments arguments) {
        String function = "listMultipartUploads";

        Converter<ListMultipartUploadsArguments, ListMultipartUploadsRequest> toRequest = new ArgumentsToListMultipartUploadsRequest();
        Converter<MultipartUploadListing, ListMultipartUploadsDomain> toDomain = new MultipartUploadListingToDomainConverter();

        AmazonS3 client = getClient();

        try {
            MultipartUploadListing multipartUploadListing = client.listMultipartUploads(toRequest.convert(arguments));
            return toDomain.convert(multipartUploadListing);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }
}
