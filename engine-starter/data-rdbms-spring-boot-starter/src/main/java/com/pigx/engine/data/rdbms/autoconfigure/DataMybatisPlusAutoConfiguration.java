package com.pigx.engine.data.rdbms.autoconfigure;

import cn.herodotus.engine.core.definition.constant.BaseConstants;
import cn.herodotus.engine.data.rdbms.autoconfigure.enhance.HerodotusIdentifierGenerator;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
/* loaded from: data-rdbms-spring-boot-starter-3.5.7.0.jar:cn/herodotus/engine/data/rdbms/autoconfigure/DataMybatisPlusAutoConfiguration.class */
public class DataMybatisPlusAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DataMybatisPlusAutoConfiguration.class);

    @Value(BaseConstants.ANNOTATION_SQL_INIT_PLATFORM)
    private String platform;

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [Data Mybatis Plus] Configure.");
    }

    private DbType parseDbType() {
        if (StringUtils.isNotBlank(this.platform)) {
            DbType type = DbType.getDbType(this.platform);
            if (ObjectUtils.isNotEmpty(type)) {
                return type;
            }
        }
        return DbType.POSTGRE_SQL;
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(parseDbType()));
        log.trace("[Herodotus] |- Bean [Mybatis Plus Interceptor] Configure.");
        return mybatisPlusInterceptor;
    }

    @Bean
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
        BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
        log.trace("[Herodotus] |- Bean [Block Attack Inner Interceptor] Configure.");
        return blockAttackInnerInterceptor;
    }

    @Bean
    public IdentifierGenerator identifierGenerator() {
        HerodotusIdentifierGenerator herodotusIdentifierGenerator = new HerodotusIdentifierGenerator();
        log.trace("[Herodotus] |- Bean [Herodotus Identifier Generator] Configure.");
        return herodotusIdentifierGenerator;
    }
}
