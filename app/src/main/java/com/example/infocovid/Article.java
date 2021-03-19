package com.example.infocovid;

public class Article {

    private String link = "";
    private String summary = "";
    private String title = "";
    private String date = "";
    private String source = "";



    public Article(String link, String summary, String title, String date, String source){
        this.link = link;
        this.summary = summary;
        this.title = title;
        this.date = date;
        this.source = source;
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

    public String getDate() {
        return date;
    }

    public String getSource() {
        return source;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
