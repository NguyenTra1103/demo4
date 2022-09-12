package com.example.demo4.contant;

public enum Shift {
    MORNING("Buổi sáng"),
    AFTERNOON("Buổi chiều");

    private final String title;

    Shift(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
