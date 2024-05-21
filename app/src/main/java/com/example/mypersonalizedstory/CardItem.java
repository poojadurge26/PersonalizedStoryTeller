package com.example.mypersonalizedstory;

public class CardItem {
    private String title;
    private String subtext;

    public CardItem(String title, String subtext) {
        this.title = title;
        this.subtext = subtext;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtext() {
        return subtext;
    }
}
