package com.maywide.core.entity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.maywide.core.web.interceptor.ExtParametersInterceptor;

/**
 * @see ExtParametersInterceptor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface SkipParamBind {

}
