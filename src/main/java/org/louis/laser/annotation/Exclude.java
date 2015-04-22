package org.louis.laser.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 排除这个属性
 * 
 * @author louisjiang(493509534@qq.com)
 * @version 2015-1-30 下午9:02:48
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
public @interface Exclude {

}
