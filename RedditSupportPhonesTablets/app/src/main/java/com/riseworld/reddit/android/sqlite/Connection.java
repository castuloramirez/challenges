package com.riseworld.reddit.android.sqlite;

/**
 * POJO that represents a row in the table "programming"
 **/
public class Connection {
    private int id;
    private String title;
    private String autor;
    private String url;
    private String permalink;

    public Connection() {}

    public String getPermalink() { return permalink; }

    public void setPermalink(String permalink) { this.permalink = permalink; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}