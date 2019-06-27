package com.whut.dataQuery.enumerate;

public enum  Section {

    UNKNOWN("未知"),
    SPINNING("纺丝"),
    WASHING("水洗"),
    WATER_PULLING("水牵"),
    OILING("上油"),
    DRYING("烘干"),
    STREAM_PULLING("蒸牵"),
    WINDING("风干");

    private String description;

    private Section(String desc) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
