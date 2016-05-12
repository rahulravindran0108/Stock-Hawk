package com.sam_chordas.android.stockhawk.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rahulravindran on 12/05/16.
 */
public class StockDetailModel {
    @SerializedName("label")
    private String label;
    @SerializedName("value")
    private String value;

    public StockDetailModel() {

    }

    public StockDetailModel(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
