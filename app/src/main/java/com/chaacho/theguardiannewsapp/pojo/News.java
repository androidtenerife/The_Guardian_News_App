package com.chaacho.theguardiannewsapp.pojo;

public class News {

    private String newsTitle;
    private String newsSection;
    private String newsAuthor;
    private String newsDate;
    private String newsUrlBrouwser;
    private String newsThumbnail;
    //TODO CAMBIAR ESTO POR OTRO NOMBRE
    private String newsTrailTextHtml;

    public News(String newsTitle, String newsSection, String newsAuthor, String newsDate, String newsUrlBrouwser, String newsThumbnail, String newsTrailTextHtml) {
        this.newsTitle = newsTitle;
        this.newsSection = newsSection;
        this.newsAuthor = newsAuthor;
        this.newsDate = newsDate;
        this.newsUrlBrouwser = newsUrlBrouwser;
        this.newsThumbnail = newsThumbnail;
        this.newsTrailTextHtml = newsTrailTextHtml;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsSection() {
        return newsSection;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public String getNewsUrlBrouwser() {
        return newsUrlBrouwser;
    }

    public String getNewsThumbnail() {
        return newsThumbnail;
    }

    public String getNewsTrailTextHtml() {
        return newsTrailTextHtml;
    }



}
