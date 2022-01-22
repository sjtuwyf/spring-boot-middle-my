package cn.bugstack.middleware.methodext.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ssqswyf
 * @date 2022/1/22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoMethodExt {

    String method() default "";
    String returnJson() default "";
}
