package com.pigx.engine.data.rdbms.autoconfigure;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.data.rdbms.autoconfigure.enhance.HerodotusIdentifierGenerator;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
public class DataMybatisPlusAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DataMybatisPlusAutoConfiguration.class);

    @Value(BaseConstants.ANNOTATION_SQL_INIT_PLATFORM)
    private String platform;

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [Data Mybatis Plus] Configure.");
    }

    private DbType parseDbType() {
        if (StringUtils.isNotBlank(platform)) {
            DbType type = DbType.getDbType(platform);
            if (ObjectUtils.isNotEmpty(type)) {
                return type;
            }
        }

        return DbType.POSTGRE_SQL;
    }

    /**
     * 防止 修改与删除时对全表进行操作
     *
     * @return {@link MybatisPlusInterceptor}
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(parseDbType()));
        log.trace("[PIGXD] |- Bean [Mybatis Plus Interceptor] Configure.");
        return mybatisPlusInterceptor;
    }

    @Bean
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
        BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
        log.trace("[PIGXD] |- Bean [Block Attack Inner Interceptor] Configure.");
        return blockAttackInnerInterceptor;
    }

    @Bean
    public IdentifierGenerator identifierGenerator() {
        HerodotusIdentifierGenerator herodotusIdentifierGenerator = new HerodotusIdentifierGenerator();
        log.trace("[PIGXD] |- Bean [Herodotus Identifier Generator] Configure.");
        return herodotusIdentifierGenerator;
    }
}
