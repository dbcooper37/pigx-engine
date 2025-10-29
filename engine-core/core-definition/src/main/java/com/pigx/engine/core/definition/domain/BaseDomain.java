package com.pigx.engine.core.definition.domain;

import java.io.Serializable;


public sealed interface BaseDomain extends Serializable permits BaseDto, BaseEntity, BaseModel, Feedback, Pagination, Response {
}
