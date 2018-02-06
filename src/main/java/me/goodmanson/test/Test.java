package me.goodmanson.test;

import org.jsoup.nodes.Document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Test {
    String checkName() default "";
    String[] onlyOnContentTypes() default {};
}
