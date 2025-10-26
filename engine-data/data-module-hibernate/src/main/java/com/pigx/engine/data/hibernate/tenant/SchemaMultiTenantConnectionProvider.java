package com.pigx.engine.data.hibernate.tenant;

import com.pigx.engine.core.definition.constant.SystemConstants;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

@Component
/* loaded from: data-module-hibernate-3.5.7.0.jar:cn/herodotus/engine/data/hibernate/tenant/SchemaMultiTenantConnectionProvider.class */
public class SchemaMultiTenantConnectionProvider implements MultiTenantConnectionProvider<String>, HibernatePropertiesCustomizer {
    private static final Logger log = LoggerFactory.getLogger(SchemaMultiTenantConnectionProvider.class);
    private final DataSource dataSource;

    public SchemaMultiTenantConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getAnyConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    public Connection getConnection(String schema) throws SQLException {
        Connection connection = getAnyConnection();
        connection.setSchema(schema);
        log.debug("[Herodotus] |- Get connection for schema tenant [{}]", schema);
        return connection;
    }

    public void releaseConnection(String schema, Connection connection) throws SQLException {
        connection.setSchema(SystemConstants.TENANT_ID);
        releaseAnyConnection(connection);
    }

    public boolean supportsAggressiveRelease() {
        return false;
    }

    public boolean isUnwrappableAs(Class<?> aClass) {
        return false;
    }

    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.multi_tenant_connection_provider", this);
    }
}
