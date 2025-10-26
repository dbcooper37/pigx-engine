package com.pigx.engine.data.mongo.repository;

import com.pigx.engine.core.definition.domain.BaseEntity;
import java.io.Serializable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
/* loaded from: data-core-mongodb-3.5.7.0.jar:cn/herodotus/engine/data/core/mongodb/repository/BaseMongoRepository.class */
public interface BaseMongoRepository<E extends BaseEntity, ID extends Serializable> extends MongoRepository<E, ID> {
}
