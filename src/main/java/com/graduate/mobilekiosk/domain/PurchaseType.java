package com.graduate.mobilekiosk.domain;

public enum PurchaseType {
    EAT("매장에서 식사"), WRAP("포장");

    private final String description;

    PurchaseType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
