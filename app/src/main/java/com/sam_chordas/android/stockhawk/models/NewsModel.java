package com.sam_chordas.android.stockhawk.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rahulravindran on 12/05/16.
 */
public class NewsModel {
    @SerializedName("Title")
    private String title;
    @SerializedName("Description")
    private String content;
    @SerializedName("Source")
    private String publisher;
    @SerializedName("Date")
    private String date;
    @SerializedName("URL")
    private String url;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
