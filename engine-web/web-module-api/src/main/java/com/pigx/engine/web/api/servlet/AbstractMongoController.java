package com.pigx.engine.web.api.servlet;

import com.pigx.engine.data.core.mongodb.entity.AbstractAuditEntity;
import com.pigx.engine.data.core.mongodb.service.BaseMongoService;
import java.io.Serializable;

/* loaded from: web-module-api-3.5.7.0.jar:cn/herodotus/engine/web/api/servlet/AbstractMongoController.class */
public abstract class AbstractMongoController<E extends AbstractAuditEntity, ID extends Serializable> extends AbstractWriteableController<E, ID, BaseMongoService<E, ID>> {
}
