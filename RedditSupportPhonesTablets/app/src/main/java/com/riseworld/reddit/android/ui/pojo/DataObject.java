package com.riseworld.reddit.android.ui.pojo;

/**
 * POJO for mapping the data
 **/
public class DataObject {
    private String title;

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    private String autor;
    private String url;
    private String permalink;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public DataObject(String title, String autor) {
        this.title = title;
        this.autor = autor;
    }

    public DataObject(){}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }
    public String getAutor() {
        return autor;
    }
}