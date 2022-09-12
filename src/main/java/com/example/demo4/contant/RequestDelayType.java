package com.example.demo4.contant;

public enum RequestDelayType {
    LATE("đến muộn"),
    EARLY("về sớm");

    private final String title;

    public String getTitle() {
        return title;
    }

    RequestDelayType(String title) {
        this.title = title;
    }
}
