package me.goodmanson.test;

import me.goodmanson.dto.TestResponseDTO;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by u6062536 on 2/6/2018.
 */

@Test(checkName = "Broken External Links Test",
        onlyOnContentTypes = {"text/html"})
public class BrokenExternalLinksTest extends BaseTest {

    @Override
    public TestResponseDTO onPageCrawl(Document page) {
        TestResponseDTO response;
        Pattern pattern;
        Matcher matcher;
        String domain;
        Elements elements;

        response = new TestResponseDTO(this.getCheckName(), page.baseUri());
        pattern = Pattern.compile("(?:https?:\\/\\/)?(?:www\\.)?([^\\/?#]+)");
        matcher = pattern.matcher(page.baseUri());
        if (!matcher.find()) {
            return null;
        }
        domain = matcher.group(1) != null ? matcher.group(1) : page.baseUri();

        elements = page.select("a[href]");
        for (Element e : elements) {
            String eDomain = e.absUrl("href");
            matcher = pattern.matcher(eDomain);
            if (matcher.find()) {
                eDomain = matcher.group(1) != null ? matcher.group(1) : eDomain;
                if (!eDomain.equalsIgnoreCase(domain)) {
                    String attr = e.attr("target");
                    if (attr != null && attr.equalsIgnoreCase("_blank")) {
                        response.addSuccess("Good link: " + e.absUrl("href"));
                    }
                    else {
                        response.addError("Bad link: " + e.absUrl("href"));
                    }
                }
            }
        }
        return response;
    }
}
