package com.pigx.engine.data.hibernate.dialect;

import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.query.sqm.FetchClauseType;

/* loaded from: data-module-hibernate-3.5.7.0.jar:cn/herodotus/engine/data/hibernate/dialect/OpenGaussDialect.class */
public class OpenGaussDialect extends PostgreSQLDialect {
    public boolean supportsFetchClause(FetchClauseType type) {
        return false;
    }
}
