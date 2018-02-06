package me.goodmanson.runner;

import me.goodmanson.crawler.Crawler;
//import me.goodmanson.crawler.JavaDomCrawler;
import me.goodmanson.dto.TestResponseDTO;
import me.goodmanson.socket.SocketUtil;
import me.goodmanson.test.BaseTest;
import me.goodmanson.test.TestCallback;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestRunner {

    @Autowired
    private TestScanner testScanner;

    @Autowired
    private Crawler crawler;

    public static void main(String[] args) throws Exception {
        TestRunner runner = new TestRunner();
        runner.runTests("http://www.bainbridgefirm.com/");
//        timeJSoup();
    }

    public void runTests(String url) {
        final List<BaseTest> tests = this.testScanner.getTests();

        TestCallback callback = (contentType, page) -> {
            tests.parallelStream()
                    .filter(test -> test.filter(contentType))
                    .forEach(test -> test.onPageCrawl(page));
        };

        tests.forEach(BaseTest::run);
        Crawler crawler = new Crawler();
        crawler.crawl(url, callback);
    }

    public void runTests(final String url, final List<BaseTest> tests, final Session session) {
        Crawler crawler;

        TestCallback callback = ((contentType, page) -> {
            tests.parallelStream()
                    .filter(test -> test.filter(contentType))
                    .forEach(test -> {
                        TestResponseDTO response = test.onPageCrawl(page);
                        if (response != null) {
                            SocketUtil.sendMessage(session, response.toJSON());
                        }
                    });
        });
        tests.forEach(BaseTest::run);
        crawler = new Crawler();
        crawler.crawl(url, callback);
    }

    public static void timeJSoup() throws Exception {
        double start, end;
        List<Double> times = new ArrayList<>();
        Connection conn = Jsoup.connect("http://www.google.com");
        Connection.Response res = conn.execute();
        for (int i = 0; i < 10000; i++) {
            System.out.println("iteration: " + i);
            start = System.nanoTime();
            Jsoup.parse(res.body());
            end = System.nanoTime();
            times.add(end - start);
        }
        times = times.stream().sorted().collect(Collectors.toList());
        double avg = 0;
        for (Double time : times) {
            System.out.println("time: " + time / 1e6 + "ms");
            avg += time;
        }

        System.out.println("average time: " + (avg / times.size()) / 1e6 + "ms");
        System.out.println("median time: " + times.get(times.size() / 2) / 1e6 + "ms");
        System.out.println("min time: " + times.get(0) / 1e6 + "ms");
        System.out.println("mode time: " + times.get(times.size() - 1) / 1e6 + "ms");
    }
}
