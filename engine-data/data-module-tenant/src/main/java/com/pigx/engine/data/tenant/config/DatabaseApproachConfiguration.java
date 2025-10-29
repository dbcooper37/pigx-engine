package com.pigx.engine.data.tenant.config;

import com.pigx.engine.data.tenant.condition.ConditionalOnMultiTenant;
import com.pigx.engine.data.tenant.enums.MultiTenant;
import com.pigx.engine.data.tenant.hibernate.DatabaseMultiTenantConnectionProvider;
import com.pigx.engine.data.tenant.hibernate.HerodotusHibernatePropertiesProvider;
import com.pigx.engine.data.tenant.properties.MultiTenantProperties;
import com.pigx.engine.data.tenant.service.MultiTenantDataSourceFactory;
import jakarta.annotation.PostConstruct;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.jdbc.SchemaManagementProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;


@Configuration(proxyBeanMethods = false)
@ConditionalOnMultiTenant(MultiTenant.DATABASE)
@EnableTransactionManagement
@EntityScan(basePackages = {
        "com.pigx.engine.data.tenant.entity",
})
@EnableJpaRepositories(basePackages = {
        "com.pigx.engine.data.tenant.repository",
})
class DatabaseApproachConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DatabaseApproachConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Database Approach] Configure.");
    }

    @Bean
    public HibernatePropertiesCustomizer databaseMultiTenantConnectionProvider(DataSource dataSource) {
        DatabaseMultiTenantConnectionProvider provider = new DatabaseMultiTenantConnectionProvider(dataSource);
        log.trace("[PIGXD] |- Bean [Multi Tenant Connection Provider] Configure.");
        return provider;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, HibernateProperties hibernateProperties,
                                                                       JpaProperties jpaProperties, JpaVendorAdapter jpaVendorAdapter,
                                                                       ConfigurableListableBeanFactory beanFactory,
                                                                       ObjectProvider<SchemaManagementProvider> providers,
                                                                       ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy,
                                                                       ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy,
                                                                       ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers,
                                                                       MultiTenantProperties multiTenantProperties
    ) {

        HerodotusHibernatePropertiesProvider provider = new HerodotusHibernatePropertiesProvider(dataSource, hibernateProperties, jpaProperties, beanFactory, providers, physicalNamingStrategy, implicitNamingStrategy, hibernatePropertiesCustomizers);
        Map<String, Object> properties = provider.getVendorProperties();

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        //此处不能省略，哪怕你使用了 @EntityScan，实际上 @EntityScan 会失效
        entityManagerFactory.setPackagesToScan(multiTenantProperties.getPackageToScan());
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setJpaPropertyMap(properties);
        return entityManagerFactory;
    }

    @Primary
    @Bean
    @ConditionalOnClass({LocalContainerEntityManagerFactoryBean.class})
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
    }

    @Bean
    @ConditionalOnClass({LocalContainerEntityManagerFactoryBean.class})
    public MultiTenantDataSourceFactory multiTenantDataSourceFactory() {
        MultiTenantDataSourceFactory multiTenantDataSourceFactory = new MultiTenantDataSourceFactory();
        log.trace("[PIGXD] |- Bean [Multi Tenant DataSource Factory] Configure.");
        return multiTenantDataSourceFactory;
    }
}
