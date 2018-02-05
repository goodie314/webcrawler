package me.goodmanson.test;


//import javadom.page.Document;

import org.jsoup.nodes.Document;

public interface TestCallback {

    void onPageCrawl(String contentType, Document page);
}
