package me.goodmanson.runner;

import me.goodmanson.crawler.Crawler;
//import me.goodmanson.crawler.JavaDomCrawler;
import me.goodmanson.test.BaseTest;
import me.goodmanson.test.TestCallback;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestRunner {

    @Autowired
    private TestScanner testScanner;

    public static void main(String[] args) throws Exception {
        TestRunner runner = new TestRunner();
        runner.runTests("http://www.bainbridgefirm.com/");
//        timeJSoup();
    }

    public void runTests(String url) {
        this.testScanner = new TestScanner();
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

//    public void runJavaDomTests(String url) {
//        this.testScanner = new TestScanner();
//        final List<BaseTest> tests = this.testScanner.getTests();
//
//        TestCallback callback = (page) -> tests.parallelStream().forEach(test -> test.onPageCrawl(page));
//
//        tests.forEach(BaseTest::run);
//        JavaDomCrawler crawler = new JavaDomCrawler();
//        crawler.crawl(url, callback);
//    }

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
