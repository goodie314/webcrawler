package me.goodmanson.controller;

import me.goodmanson.runner.TestRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRunController {

    @Autowired
    private TestRunner runner;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void runTests() {
        runner.runTests("http://www.kelnerlaw.com");
    }
}
