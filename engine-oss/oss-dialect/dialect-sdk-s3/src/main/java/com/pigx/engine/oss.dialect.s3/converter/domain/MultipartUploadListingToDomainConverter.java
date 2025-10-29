package com.pigx.engine.oss.dialect.s3.converter.domain;

import com.amazonaws.services.s3.model.MultipartUpload;
import com.amazonaws.services.s3.model.MultipartUploadListing;
import com.pigx.engine.oss.specification.domain.multipart.ListMultipartUploadsDomain;
import com.pigx.engine.oss.specification.domain.multipart.UploadDomain;
import org.springframework.core.convert.converter.Converter;

import java.util.List;


public class MultipartUploadListingToDomainConverter implements Converter<MultipartUploadListing, ListMultipartUploadsDomain> {

    private final Converter<List<MultipartUpload>, List<UploadDomain>> toDomain = new MultipartUploadToDomainConverter();

    @Override
    public ListMultipartUploadsDomain convert(MultipartUploadListing source) {

        ListMultipartUploadsDomain domain = new ListMultipartUploadsDomain();
        domain.setTruncated(source.isTruncated());
        domain.setNextKeyMarker(source.getNextKeyMarker());
        domain.setNextUploadIdMarker(source.getNextUploadIdMarker());
        domain.setMultipartUploads(toDomain.convert(source.getMultipartUploads()));
        domain.setCommonPrefixes(source.getCommonPrefixes());
        domain.setDelimiter(source.getDelimiter());
        domain.setPrefix(source.getPrefix());
        domain.setMaxUploads(source.getMaxUploads());
        domain.setKeyMarker(source.getKeyMarker());
        domain.setUploadIdMarker(source.getUploadIdMarker());
        domain.setEncodingType(source.getEncodingType());
        domain.setBucketName(source.getBucketName());
        return domain;
    }
}
