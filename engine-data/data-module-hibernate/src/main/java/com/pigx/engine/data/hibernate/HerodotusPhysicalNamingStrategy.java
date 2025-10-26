package com.pigx.engine.data.hibernate;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/* loaded from: data-module-hibernate-3.5.7.0.jar:cn/herodotus/engine/data/hibernate/HerodotusPhysicalNamingStrategy.class */
public class HerodotusPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return new Identifier(name.getText(), true);
    }
}
