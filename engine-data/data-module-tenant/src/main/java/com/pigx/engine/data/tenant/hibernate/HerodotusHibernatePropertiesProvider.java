package com.pigx.engine.data.tenant.hibernate;

import com.pigx.engine.core.definition.constant.SystemConstants;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.sql.DataSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.SchemaManagement;
import org.springframework.boot.jdbc.SchemaManagementProvider;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.util.ClassUtils;

/* loaded from: data-module-tenant-3.5.7.0.jar:cn/herodotus/engine/data/tenant/hibernate/HerodotusHibernatePropertiesProvider.class */
public class HerodotusHibernatePropertiesProvider {
    private final DataSource dataSource;
    private final HibernateProperties hibernateProperties;
    private final JpaProperties jpaProperties;
    private final HibernateDefaultDdlAutoProvider defaultDdlAutoProvider;
    private final List<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers;

    public HerodotusHibernatePropertiesProvider(DataSource dataSource, HibernateProperties hibernateProperties, JpaProperties jpaProperties, ConfigurableListableBeanFactory beanFactory, ObjectProvider<SchemaManagementProvider> providers, ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy, ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy, ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        this.dataSource = dataSource;
        this.hibernateProperties = hibernateProperties;
        this.jpaProperties = jpaProperties;
        this.defaultDdlAutoProvider = new HibernateDefaultDdlAutoProvider(providers);
        this.hibernatePropertiesCustomizers = determineHibernatePropertiesCustomizers((PhysicalNamingStrategy) physicalNamingStrategy.getIfAvailable(), (ImplicitNamingStrategy) implicitNamingStrategy.getIfAvailable(), beanFactory, hibernatePropertiesCustomizers.orderedStream().toList());
    }

    public Map<String, Object> getVendorProperties() {
        Supplier<String> defaultDdlMode = () -> {
            return this.defaultDdlAutoProvider.getDefaultDdlAuto(this.dataSource);
        };
        return new LinkedHashMap(this.hibernateProperties.determineHibernateProperties(this.jpaProperties.getProperties(), new HibernateSettings().ddlAuto(defaultDdlMode).hibernatePropertiesCustomizers(this.hibernatePropertiesCustomizers)));
    }

    private List<HibernatePropertiesCustomizer> determineHibernatePropertiesCustomizers(PhysicalNamingStrategy physicalNamingStrategy, ImplicitNamingStrategy implicitNamingStrategy, ConfigurableListableBeanFactory beanFactory, List<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        List<HibernatePropertiesCustomizer> customizers = new ArrayList<>();
        if (ClassUtils.isPresent("org.hibernate.resource.beans.container.spi.BeanContainer", getClass().getClassLoader())) {
            customizers.add(properties -> {
                properties.put("hibernate.resource.beans.container", new SpringBeanContainer(beanFactory));
            });
        }
        if (physicalNamingStrategy != null || implicitNamingStrategy != null) {
            customizers.add(new NamingStrategiesHibernatePropertiesCustomizer(physicalNamingStrategy, implicitNamingStrategy));
        }
        customizers.addAll(hibernatePropertiesCustomizers);
        return customizers;
    }

    /* loaded from: data-module-tenant-3.5.7.0.jar:cn/herodotus/engine/data/tenant/hibernate/HerodotusHibernatePropertiesProvider$HibernateDefaultDdlAutoProvider.class */
    private static class HibernateDefaultDdlAutoProvider implements SchemaManagementProvider {
        private final Iterable<SchemaManagementProvider> providers;

        HibernateDefaultDdlAutoProvider(Iterable<SchemaManagementProvider> providers) {
            this.providers = providers;
        }

        String getDefaultDdlAuto(DataSource dataSource) {
            if (!EmbeddedDatabaseConnection.isEmbedded(dataSource)) {
                return SystemConstants.NONE;
            }
            SchemaManagement schemaManagement = getSchemaManagement(dataSource);
            if (SchemaManagement.MANAGED.equals(schemaManagement)) {
                return SystemConstants.NONE;
            }
            return "create-drop";
        }

        public SchemaManagement getSchemaManagement(DataSource dataSource) {
            Stream map = StreamSupport.stream(this.providers.spliterator(), false).map(provider -> {
                return provider.getSchemaManagement(dataSource);
            });
            SchemaManagement schemaManagement = SchemaManagement.MANAGED;
            Objects.requireNonNull(schemaManagement);
            return (SchemaManagement) map.filter((v1) -> {
                return r1.equals(v1);
            }).findFirst().orElse(SchemaManagement.UNMANAGED);
        }
    }

    /* loaded from: data-module-tenant-3.5.7.0.jar:cn/herodotus/engine/data/tenant/hibernate/HerodotusHibernatePropertiesProvider$NamingStrategiesHibernatePropertiesCustomizer.class */
    private static class NamingStrategiesHibernatePropertiesCustomizer implements HibernatePropertiesCustomizer {
        private final PhysicalNamingStrategy physicalNamingStrategy;
        private final ImplicitNamingStrategy implicitNamingStrategy;

        NamingStrategiesHibernatePropertiesCustomizer(PhysicalNamingStrategy physicalNamingStrategy, ImplicitNamingStrategy implicitNamingStrategy) {
            this.physicalNamingStrategy = physicalNamingStrategy;
            this.implicitNamingStrategy = implicitNamingStrategy;
        }

        public void customize(Map<String, Object> hibernateProperties) {
            if (this.physicalNamingStrategy != null) {
                hibernateProperties.put("hibernate.physical_naming_strategy", this.physicalNamingStrategy);
            }
            if (this.implicitNamingStrategy != null) {
                hibernateProperties.put("hibernate.implicit_naming_strategy", this.implicitNamingStrategy);
            }
        }
    }
}
