package com.me.books.entities;

public class Chapter {
    private String title;
    private String content;
    private int index;

    public Chapter(String title, String content, int index) {
        this.title = title;
        this.content = content;
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
