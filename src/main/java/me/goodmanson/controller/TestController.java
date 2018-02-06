package me.goodmanson.controller;

import me.goodmanson.runner.TestScanner;
import me.goodmanson.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private TestScanner scanner;

    @RequestMapping(value = "tests")
    public List<BaseTest> getTests() {
        return this.scanner.getTests();
    }
}
