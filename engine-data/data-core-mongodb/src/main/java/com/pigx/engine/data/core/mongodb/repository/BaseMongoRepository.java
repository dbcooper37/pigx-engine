package com.pigx.engine.data.core.mongodb.repository;

import com.pigx.engine.core.definition.domain.BaseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;


@NoRepositoryBean
public interface BaseMongoRepository<E extends BaseEntity, ID extends Serializable> extends MongoRepository<E, ID> {
}
