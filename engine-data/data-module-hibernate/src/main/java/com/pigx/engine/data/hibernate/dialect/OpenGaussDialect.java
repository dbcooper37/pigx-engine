package com.pigx.engine.data.hibernate.dialect;

import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.query.sqm.FetchClauseType;


public class OpenGaussDialect extends PostgreSQLDialect {

    @Override
    public boolean supportsFetchClause(FetchClauseType type) {
        return false;
    }
}
