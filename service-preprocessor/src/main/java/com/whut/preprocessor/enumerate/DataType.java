package com.whut.preprocessor.enumerate;

public enum DataType {

    REAL("浮点型"),
    DINT("整型"),
    BOOL("布尔型");

    private String description;

    private DataType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
