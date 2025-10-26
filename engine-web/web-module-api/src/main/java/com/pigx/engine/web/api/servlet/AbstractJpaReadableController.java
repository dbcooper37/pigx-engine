package com.pigx.engine.web.api.servlet;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.data.core.jpa.service.BaseJpaReadableService;
import java.io.Serializable;

/* loaded from: web-module-api-3.5.7.0.jar:cn/herodotus/engine/web/api/servlet/AbstractJpaReadableController.class */
public abstract class AbstractJpaReadableController<E extends BaseEntity, ID extends Serializable> extends AbstractReadableController<E, ID, BaseJpaReadableService<E, ID>> {
}
