package com.graduate.mobilekiosk.domain;

public enum WhichPayment {
    CARD("카드결제"), CASH("현금결제"), KAKAOPAY("카카오페이"), NAVERPAY("네이버페이");

    private final String description;

    WhichPayment(String description) { this.description = description;}

    public String getDescription() {
        return description;
    }
}
