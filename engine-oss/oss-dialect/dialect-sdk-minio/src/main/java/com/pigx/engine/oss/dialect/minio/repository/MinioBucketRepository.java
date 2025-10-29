package com.pigx.engine.oss.dialect.minio.repository;

import com.pigx.engine.core.definition.support.AbstractObjectPool;
import com.pigx.engine.oss.dialect.minio.converter.arguments.ArgumentsToMakeBucketArgsConverter;
import com.pigx.engine.oss.dialect.minio.converter.arguments.ArgumentsToRemoveBucketArgsConverter;
import com.pigx.engine.oss.dialect.minio.converter.domain.BucketToDomainConverter;
import com.pigx.engine.oss.dialect.minio.definition.service.BaseMinioService;
import com.pigx.engine.oss.dialect.minio.service.MinioBucketService;
import com.pigx.engine.oss.dialect.minio.utils.ConverterUtils;
import com.pigx.engine.oss.specification.arguments.bucket.CreateBucketArguments;
import com.pigx.engine.oss.specification.arguments.bucket.DeleteBucketArguments;
import com.pigx.engine.oss.specification.core.repository.OssBucketRepository;
import com.pigx.engine.oss.specification.domain.bucket.BucketDomain;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MinioBucketRepository extends BaseMinioService implements OssBucketRepository {

    private static final Logger log = LoggerFactory.getLogger(MinioBucketRepository.class);

    private final MinioBucketService minioBucketService;

    public MinioBucketRepository(AbstractObjectPool<MinioClient> ossClientObjectPool, MinioBucketService minioBucketService) {
        super(ossClientObjectPool);
        this.minioBucketService = minioBucketService;
    }

    @Override
    public boolean doesBucketExist(String bucketName) {
        return minioBucketService.bucketExists(bucketName);
    }

    @Override
    public List<BucketDomain> listBuckets() {
        return ConverterUtils.toDomains(minioBucketService.listBuckets(), new BucketToDomainConverter());
    }

    @Override
    public BucketDomain createBucket(String bucketName) {
        minioBucketService.makeBucket(bucketName);
        return null;
    }

    @Override
    public BucketDomain createBucket(CreateBucketArguments arguments) {
        Converter<CreateBucketArguments, MakeBucketArgs> toArgs = new ArgumentsToMakeBucketArgsConverter();
        minioBucketService.makeBucket(toArgs.convert(arguments));
        return null;
    }

    @Override
    public void deleteBucket(String bucketName) {
        minioBucketService.removeBucket(bucketName);
    }

    @Override
    public void deleteBucket(DeleteBucketArguments arguments) {
        Converter<DeleteBucketArguments, RemoveBucketArgs> toArgs = new ArgumentsToRemoveBucketArgsConverter();
        minioBucketService.removeBucket(toArgs.convert(arguments));
    }


}
