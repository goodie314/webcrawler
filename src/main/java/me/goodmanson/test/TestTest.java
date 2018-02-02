package me.goodmanson.test;

import org.jsoup.nodes.Document;

@Test
public class TestTest extends BaseTest {

    @Override
    public void run() {
        System.out.println("Child");
    }

    @Override
    public void onPageCrawl(Document page) {
//        System.out.println("Crawling " + page.location());
    }
}
