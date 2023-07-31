package com.crud.project.annotation;

import com.crud.project.config.SessionPublisherConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SessionPublisherConfig.class)
public @interface EnableSessionPublisher {
}
