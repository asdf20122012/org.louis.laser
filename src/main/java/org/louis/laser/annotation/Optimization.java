package org.louis.laser.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 用于field为{@link Number}类型,值大于等于0的时候
 * 
 * @author louisjiang(493509534@qq.com)
 * @version 2015-1-29 下午11:29:04
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
public @interface Optimization {
}
