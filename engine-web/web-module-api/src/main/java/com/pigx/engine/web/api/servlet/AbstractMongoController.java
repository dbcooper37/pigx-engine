package com.pigx.engine.web.api.servlet;

import com.pigx.engine.data.core.mongodb.entity.AbstractAuditEntity;
import com.pigx.engine.data.core.mongodb.service.BaseMongoService;

import java.io.Serializable;


public abstract class AbstractMongoController<E extends AbstractAuditEntity, ID extends Serializable> extends AbstractWriteableController<E, ID, BaseMongoService<E, ID>> {

}
