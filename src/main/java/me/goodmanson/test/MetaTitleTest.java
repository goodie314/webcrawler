package me.goodmanson.test;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Test
public class MetaTitleTest extends BaseTest {

    @Override
    public void onPageCrawl(Document page) {
//        System.out.println("Crawl2: " + page.location());
        Element element = page.selectFirst("meta[property='og:title']");

        if (element != null) {
            System.out.println("Title found at " + page.location());
        }
        else {
            System.out.println("No title found at " + page.location());
        }
    }
}
