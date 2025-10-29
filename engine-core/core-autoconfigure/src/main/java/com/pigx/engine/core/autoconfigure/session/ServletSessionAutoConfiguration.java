package com.pigx.engine.core.autoconfigure.session;

import com.pigx.engine.core.autoconfigure.jackson2.Jackson2AutoConfiguration;
import com.pigx.engine.core.foundation.condition.ConditionalOnServletApplication;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;


@AutoConfiguration(after = Jackson2AutoConfiguration.class)
@ConditionalOnServletApplication
@ConditionalOnClass({SessionRepository.class})
@EnableRedisIndexedHttpSession
public class ServletSessionAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ServletSessionAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [Servlet Spring Session] Configure.");
    }

    @Bean
    public SpringSessionRememberMeServices springSessionRememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        log.trace("[PIGXD] |- Bean [Spring Session Remember Me Services] Configure.");
        return rememberMeServices;
    }

    @Bean
    public SessionRepositoryCustomizer<RedisIndexedSessionRepository> sessionRepositoryCustomizer() {
        // 关闭 Spring Session 自身的清理任务，让 Redis 自行清理过期内容
        // 如果不关闭，经常会出现 java.lang.IllegalStateException: LettuceConnectionFactory has been STOPPED. Use start() to initialize it 问题
        // cleanup-cron 参数默认值通常是 "0 * * * * *"（每分钟跑一次）。通过将其设置为一个无效的 cron 表达式（如 "* * * 31 2 ?"），Spring Session 就不会创建那个后台调度任务，自然也就不会在关闭时发生冲突了
        // * * * 31 2 ? 此表达式是指2月31日执行，但是该日期不存在，所以永远不会执行
        return (sessionRepository) -> sessionRepository.setCleanupCron("* * * 31 2 ?");
    }

    @Configuration(proxyBeanMethods = false)
    @EnableSpringHttpSession
    static class SpringHttpSessionConfiguration {

    }
}
