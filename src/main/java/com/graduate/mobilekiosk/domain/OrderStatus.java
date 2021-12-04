package com.graduate.mobilekiosk.domain;

public enum OrderStatus {
    ORDER("수락 대기중"), ACCEPT("수락"), REFUSE("거절"), SOLD("조리 완료");

    private final String description;

    OrderStatus(String description) { this.description = description;}

    public String getDescription() {
        return description;
    }
}
