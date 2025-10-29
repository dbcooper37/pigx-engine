package com.pigx.engine.oss.rest.minio.annotation;

import com.pigx.engine.oss.rest.minio.config.OssRestMinioConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(OssRestMinioConfiguration.class)
public @interface EnableHerodotusMinioRest {
}
