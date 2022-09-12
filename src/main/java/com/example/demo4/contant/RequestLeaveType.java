package com.example.demo4.contant;

public enum RequestLeaveType {
    NO_SALARY("Không lương"),
    BY_YEAR("Phép năm");
    private final String title;

    RequestLeaveType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
