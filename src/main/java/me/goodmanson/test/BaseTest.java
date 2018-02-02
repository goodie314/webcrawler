package me.goodmanson.test;

import org.jsoup.nodes.Document;

public class BaseTest {

    public void run() {
        System.out.println("Parent");
    }

    public void onPageCrawl(Document page) {
    }

    public void onCrawlFinish() {
    }
}
