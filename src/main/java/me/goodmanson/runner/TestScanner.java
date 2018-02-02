package me.goodmanson.runner;

import me.goodmanson.test.BaseTest;
import me.goodmanson.test.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestScanner {

    private List<BaseTest> tests = new ArrayList<>();

    public List<BaseTest> getTests() {
        if (this.tests.isEmpty()) {
            findAnnotatedClasses("me.goodmanson");
        }

        return this.tests;
    }

    public void findAnnotatedClasses(String scanPackage) {
        BaseTest test;
        ClassPathScanningCandidateComponentProvider provider = createComponentScanner();
        for (BeanDefinition beanDef : provider.findCandidateComponents(scanPackage)) {
            test = getTest(beanDef);
            this.tests.add(test);
        }
    }

    private ClassPathScanningCandidateComponentProvider createComponentScanner() {
        // Don't pull default filters (@Component, etc.):
        ClassPathScanningCandidateComponentProvider provider
                = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Test.class));
        return provider;
    }

    private BaseTest getTest(BeanDefinition beanDef) {
        BaseTest baseTest;
        try {
            Class<?> cl = Class.forName(beanDef.getBeanClassName());
            Test test = cl.getAnnotation(Test.class);
            baseTest = (BaseTest) cl.newInstance();
        } catch (Exception e) {
            System.err.println("Got exception: " + e.getMessage());
            return null;
        }

        return baseTest;
    }
}
