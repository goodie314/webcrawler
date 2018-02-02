package me.goodmanson.crawler;

import me.goodmanson.test.BaseTest;
import me.goodmanson.test.Test;
import me.goodmanson.test.TestCallback;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Crawler {

    public static TestCallback callback;

    public static void main(String[] args) {
        long start, end, parallel;
        String url = "http://www.kelnerlaw.com/";

//        List<Test> tests = new ArrayList<>();
//        tests.add(new BaseTest());
//        tests.add(new BaseTest());
//        Crawler.callback = (page) -> tests.forEach(test -> test.onPageCrawl(page));
//        List<Document> pages;
//        Crawler crawler = new Crawler();
//
//        start = System.currentTimeMillis();
//        pages = crawler.crawl(url);
//        end = System.currentTimeMillis();
//        parallel = end - start;
//        System.out.println("Discovered " + pages.size() + " pages");
//
//        System.out.println("Parallel: " + parallel / 1000);

    }

    public Crawler() {
    }

    public List<Document> crawl(String url, TestCallback onPageCrawl) {
        Set<String> discoveredUrls = new HashSet<String>();
        discoveredUrls.add(url);
        return this.crawl(url, url, discoveredUrls, onPageCrawl);
    }

    private List<Document> crawl(String url, String baseUrl, final Set<String> discoveredUrls, TestCallback onPageCrawl) {
        Document page;
        List<String> links;
        List<Document> pages;

        if (!url.contains(baseUrl) || url.contains("#")) {
            return Collections.emptyList();
        }

        page = this.getPage(url, onPageCrawl);
        if (page == null) {
            return Collections.emptyList();
        }

        pages = new ArrayList<>();
        pages.add(page);

        pages.addAll(page.select("a[href]")
                .parallelStream()
                .map(link -> link.absUrl("href"))
                .filter(link -> {
                    if (!discoveredUrls.contains(link)) {
                        discoveredUrls.add(link);
                        return true;
                    }
                    else {
                        return false;
                    }
                })
                .map(link -> crawl(link, baseUrl, discoveredUrls, onPageCrawl))
                .flatMap(List::stream)
                .collect(Collectors.toList()));
        return pages;
    }

    private Document getPage(String url, TestCallback onPageCrawl) {
        Connection connection;
        Connection.Response response;
        Document page;

        connection = Jsoup.connect(url).userAgent("mozilla/5.0");

        try {
            response = connection.execute();
        } catch (IOException e) {
            System.out.println("Error retrieving page at " + url);
            return null;
        }

        page = Jsoup.parse(response.body(), response.url().toString());
        onPageCrawl.onPageCrawl(page);
        return page;
    }
}
