package me.goodmanson.dto;

import java.util.List;

/**
 * Created by u6062536 on 2/6/2018.
 */
public class StartCrawlerDTO {

    private String url;
    private List<String> tests;

    public StartCrawlerDTO() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getTests() {
        return tests;
    }

    public void setTests(List<String> tests) {
        this.tests = tests;
    }
}
