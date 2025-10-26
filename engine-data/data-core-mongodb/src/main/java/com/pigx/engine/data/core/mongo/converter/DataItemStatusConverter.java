package com.pigx.engine.data.mongo.converter;

import com.pigx.engine.data.core.enums.DataItemStatus;
import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.mongodb.core.convert.MongoConversionContext;

/* loaded from: data-core-mongodb-3.5.7.0.jar:cn/herodotus/engine/data/core/mongodb/converter/DataItemStatusConverter.class */
public class DataItemStatusConverter implements PropertyValueConverter<DataItemStatus, Integer, MongoConversionContext> {
    public DataItemStatus read(Integer value, MongoConversionContext context) {
        return DataItemStatus.get(value);
    }

    public Integer write(DataItemStatus value, MongoConversionContext context) {
        return Integer.valueOf(value.ordinal());
    }
}
