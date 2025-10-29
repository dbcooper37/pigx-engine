package com.pigx.engine.oss.dialect.minio.converter.domain;

import com.pigx.engine.oss.specification.arguments.multipart.ListPartsArguments;
import com.pigx.engine.oss.specification.domain.base.OwnerDomain;
import com.pigx.engine.oss.specification.domain.multipart.ListPartsDomain;
import com.pigx.engine.oss.specification.domain.multipart.PartSummaryDomain;
import io.minio.messages.Initiator;
import io.minio.messages.ListPartsResult;
import io.minio.messages.Owner;
import io.minio.messages.Part;
import org.springframework.core.convert.converter.Converter;

import java.util.List;


public class ListPartsResultToDomainConverter implements Converter<ListPartsResult, ListPartsDomain> {

    private final Converter<Owner, OwnerDomain> owner = new OwnerToDomainConverter();
    private final Converter<Initiator, OwnerDomain> initiator = new InitiatorToDomainConverter();
    private final Converter<List<Part>, List<PartSummaryDomain>> parts = new PartToDomainConverter();

    private final ListPartsArguments arguments;

    public ListPartsResultToDomainConverter(ListPartsArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public ListPartsDomain convert(ListPartsResult source) {

        ListPartsDomain domain = new ListPartsDomain();
        domain.setOwner(owner.convert(source.owner()));
        domain.setInitiator(initiator.convert(source.initiator()));
        domain.setStorageClass(source.storageClass());
        domain.setMaxParts(source.maxParts());
        domain.setPartNumberMarker(source.partNumberMarker());
        domain.setNextPartNumberMarker(source.nextPartNumberMarker());
        domain.setTruncated(source.isTruncated());
        domain.setParts(parts.convert(source.partList()));
        domain.setUploadId(arguments.getUploadId());
        domain.setBucketName(source.bucketName());
        domain.setObjectName(source.objectName());

        return domain;
    }
}
