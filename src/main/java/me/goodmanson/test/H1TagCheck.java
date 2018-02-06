package me.goodmanson.test;


//import javadom.page.Document;
//import javadom.page.Node;

import me.goodmanson.dto.TestResponseDTO;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by u6062536 on 2/5/2018.
 */

@Test(checkName = "H1 tag check", onlyOnContentTypes = {"text/html"})
public class H1TagCheck extends BaseTest {
    int i = 0;

    @Override
    public TestResponseDTO onPageCrawl(Document page) {
        TestResponseDTO response;

        response = new TestResponseDTO(this.getCheckName(), page.baseUri());
        i++;
        System.out.println("Iter: " + i);
        System.out.println("Page: " + page.baseUri());
        Elements elements = page.select("h1");
        if (elements.size() != 1) {
            System.out.println("More than 1 h1 on the page");
            response.addError("More than 1 h1 on the page");
        }
        else {
            System.out.println("Only 1 h1 on the page");
            response.addSuccess("Only 1 h1 on the page");
        }

        return response;
    }
}
