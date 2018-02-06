package me.goodmanson.socket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.goodmanson.dto.StartCrawlerDTO;
import me.goodmanson.runner.TestRunner;
import me.goodmanson.runner.TestScanner;
import me.goodmanson.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by u6062536 on 2/6/2018.
 */

@ServerEndpoint(value = "/crawler")
public class CrawlerSocket {

    @Autowired
    private TestScanner scanner;

    @Autowired
    private TestRunner runner;

    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        ObjectMapper mapper;
        StartCrawlerDTO crawlerDTO;
        List<String> testsToRun;
        List<BaseTest> tests;
        TestScanner scanner;
        TestRunner runner;

        mapper = new ObjectMapper();
        crawlerDTO = mapper.readValue(message, StartCrawlerDTO.class);
        testsToRun = crawlerDTO.getTests();

        scanner = new TestScanner();
        tests = scanner.getTests()
                .stream()
                .filter(test -> testsToRun.contains(test.getCheckName()))
                .collect(Collectors.toList());

        runner = new TestRunner();
        runner.runTests(crawlerDTO.getUrl(), tests, session);
        session.close();
    }
}
