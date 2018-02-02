package me.goodmanson.test;

import org.jsoup.nodes.Document;

public interface TestCallback {

    void onPageCrawl(Document page);
}
