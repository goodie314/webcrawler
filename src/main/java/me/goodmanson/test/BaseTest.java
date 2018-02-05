package me.goodmanson.test;


//import javadom.page.Document;

import org.jsoup.nodes.Document;

public class BaseTest {

    private String[] onlyOnContentType;
    private Test metaInfo;

    public void run() {
    }

    public void onPageCrawl(Document page) {
    }

    public void onCrawlFinish() {
    }

    public String[] getOnlyOnContentType() {
        return this.onlyOnContentType;
    }

    public void setOnlyOnContentType(String[] onlyOnContentType) {
        this.onlyOnContentType = onlyOnContentType;
    }

    public Test getMetaInfo() {
        return this.metaInfo;
    }

    public void setMetaInfo(Test metaInfo) {
        this.metaInfo = metaInfo;
    }

    public boolean filter(String contentType) {
        if (this.metaInfo.onlyOnContentTypes().length == 0) {
            return true;
        }
        for (String type : this.metaInfo.onlyOnContentTypes()) {
            if (contentType.equals(type)) {
                return true;
            }
        }
        return false;
    }
}
