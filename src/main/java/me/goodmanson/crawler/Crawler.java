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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Crawler {

    private static final Pattern domainPattern = Pattern.compile("https?://www.([^/?#]+)");

    public Crawler() {
    }

    public List<Document> crawl(String url, TestCallback onPageCrawl) {
        Matcher matcher = domainPattern.matcher(url);
        String baseURL;
        if (matcher.find()) {
            baseURL = matcher.group(1) != null ? matcher.group(1) : url;
        }
        else {
            baseURL = url;
        }
        Set<String> discoveredUrls = new HashSet<String>();
        discoveredUrls.add(url);

        return this.crawl(url, baseURL, discoveredUrls, onPageCrawl);
    }

    private List<Document> crawl(String url, String baseUrl, final Set<String> discoveredUrls, TestCallback onPageCrawl) {
        Document page;
        List<Document> pages;
        Matcher domainMatcher;
        String domain;

        domainMatcher = domainPattern.matcher(url);
        if (domainMatcher.find()) {
            domain = domainMatcher.group(1);
            if (domain == null) {
                return Collections.emptyList();
            }
        }
        else {
            return Collections.emptyList();
        }

        if (!domain.contains(baseUrl) || url.contains("#")) {
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
                    int index = link.indexOf("?");
                    if (index > -1) {
                        link = link.substring(0, index);
                    }
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
        } catch (Exception e) {
            System.out.println("Error retrieving page at " + url);
            return null;
        }
        page = Jsoup.parse(response.body(), response.url().toString());
        onPageCrawl.onPageCrawl(response.contentType(), page);
        return page;
    }
}
