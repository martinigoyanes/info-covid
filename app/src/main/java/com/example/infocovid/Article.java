package com.example.infocovid;

public class Article {

    private String link = "";
    private String summary = "";
    private String title = "";

    public Article(String link, String summary, String title){
        this.link = link;
        this.summary = summary;
        this.title = title;
    }

    public String getLink() {
        return link;
    }
    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
