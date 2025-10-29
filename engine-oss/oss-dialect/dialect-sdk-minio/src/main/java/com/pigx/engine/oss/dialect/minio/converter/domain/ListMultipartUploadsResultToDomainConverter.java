package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.specification.arguments.multipart.ListMultipartUploadsArguments;
import com.pigx.engine.oss.specification.domain.multipart.ListMultipartUploadsDomain;
import com.pigx.engine.oss.specification.domain.multipart.UploadDomain;
import io.minio.messages.ListMultipartUploadsResult;
import io.minio.messages.Upload;
import org.springframework.core.convert.converter.Converter;

import java.util.List;


public class ListMultipartUploadsResultToDomainConverter implements Converter<ListMultipartUploadsResult, ListMultipartUploadsDomain> {

    private final ListMultipartUploadsArguments listMultipartUploadsArguments;
    private final Converter<List<Upload>, List<UploadDomain>> toMultipartUpload = new UploadToDomainConverter();

    public ListMultipartUploadsResultToDomainConverter(ListMultipartUploadsArguments listMultipartUploadsArguments) {
        this.listMultipartUploadsArguments = listMultipartUploadsArguments;
    }

    @Override
    public ListMultipartUploadsDomain convert(ListMultipartUploadsResult source) {
        ListMultipartUploadsDomain domain = new ListMultipartUploadsDomain();
        domain.setTruncated(source.isTruncated());
        domain.setNextKeyMarker(source.nextKeyMarker());
        domain.setNextUploadIdMarker(source.nextUploadIdMarker());
        domain.setMultipartUploads(toMultipartUpload.convert(source.uploads()));
        domain.setDelimiter(listMultipartUploadsArguments.getDelimiter());
        domain.setPrefix(listMultipartUploadsArguments.getPrefix());
        domain.setMaxUploads(source.maxUploads());
        domain.setKeyMarker(source.keyMarker());
        domain.setUploadIdMarker(source.uploadIdMarker());
        domain.setEncodingType(source.encodingType());
        domain.setBucketName(source.bucketName());
        return domain;
    }
}
