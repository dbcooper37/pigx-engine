package com.pigx.engine.data.core.mongodb.converter;

import com.pigx.engine.data.core.enums.DataItemStatus;
import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.mongodb.core.convert.MongoConversionContext;


public class DataItemStatusConverter implements PropertyValueConverter<DataItemStatus, Integer, MongoConversionContext> {

    @Override
    public DataItemStatus read(Integer value, MongoConversionContext context) {
        return DataItemStatus.get(value);
    }

    @Override
    public Integer write(DataItemStatus value, MongoConversionContext context) {
        return value.ordinal();
    }
}
