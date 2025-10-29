package com.pigx.engine.data.hibernate;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;


public class HerodotusPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {

        // Hibernate 默认使用 Identifier.getCanonicalName()的值最为最终的值，text是原始值。如果quoted为true则使用text，否则就进行小写转换。所以此处quoted设置为true。参见具体方法。
        return new Identifier(name.getText(), true);
    }
}
