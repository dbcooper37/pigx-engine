package com.pigx.engine.oss.dialect.s3.repository;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import com.pigx.engine.core.definition.support.AbstractObjectPool;
import com.pigx.engine.oss.dialect.core.exception.OssServerException;
import com.pigx.engine.oss.dialect.core.utils.ConverterUtils;
import com.pigx.engine.oss.dialect.s3.converter.arguments.ArgumentsToCreateBucketRequestConverter;
import com.pigx.engine.oss.dialect.s3.converter.arguments.ArgumentsToDeleteBucketRequestConverter;
import com.pigx.engine.oss.dialect.s3.converter.domain.BucketToDomainConverter;
import com.pigx.engine.oss.dialect.s3.definition.service.BaseS3Service;
import com.pigx.engine.oss.specification.arguments.bucket.CreateBucketArguments;
import com.pigx.engine.oss.specification.arguments.bucket.DeleteBucketArguments;
import com.pigx.engine.oss.specification.core.repository.OssBucketRepository;
import com.pigx.engine.oss.specification.domain.bucket.BucketDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class S3BucketRepository extends BaseS3Service implements OssBucketRepository {

    private static final Logger log = LoggerFactory.getLogger(S3BucketRepository.class);

    public S3BucketRepository(AbstractObjectPool<AmazonS3> ossClientObjectPool) {
        super(ossClientObjectPool);
    }

    @Override
    public boolean doesBucketExist(String bucketName) {
        String function = "doesBucketExistV2";

        AmazonS3 client = getClient();
        try {
            return client.doesBucketExistV2(bucketName);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public List<BucketDomain> listBuckets() {
        String function = "listBuckets";

        AmazonS3 client = getClient();
        try {
            return ConverterUtils.toDomains(client.listBuckets(), new BucketToDomainConverter());
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public BucketDomain createBucket(String bucketName) {
        String function = "createBucket";

        AmazonS3 client = getClient();
        try {
            return ConverterUtils.toDomain(client.createBucket(bucketName), new BucketToDomainConverter());
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public BucketDomain createBucket(CreateBucketArguments arguments) {
        String function = "createBucket";

        AmazonS3 client = getClient();
        try {
            Converter<CreateBucketArguments, CreateBucketRequest> toRequest = new ArgumentsToCreateBucketRequestConverter();
            return ConverterUtils.toDomain(client.createBucket(toRequest.convert(arguments)), new BucketToDomainConverter());
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public void deleteBucket(String bucketName) {
        String function = "deleteBucket";

        AmazonS3 client = getClient();
        try {
            client.deleteBucket(bucketName);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public void deleteBucket(DeleteBucketArguments arguments) {
        String function = "deleteBucket";

        AmazonS3 client = getClient();

        try {
            Converter<DeleteBucketArguments, DeleteBucketRequest> toRequest = new ArgumentsToDeleteBucketRequestConverter();
            client.deleteBucket(toRequest.convert(arguments));
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }
}
