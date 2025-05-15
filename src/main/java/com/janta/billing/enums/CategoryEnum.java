package com.janta.billing.enums;

public enum CategoryEnum {
    Electronics(1),
    Grocery(2),
    Clothing(3),Others(4);

    private final int code;

    CategoryEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
