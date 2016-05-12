package com.sam_chordas.android.stockhawk.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rahulravindran on 12/05/16.
 */
public class StockModel {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLastprice() {
        return lastprice;
    }

    public void setLastprice(String lastprice) {
        this.lastprice = lastprice;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    @SerializedName("Name")
    String name;
    @SerializedName("Symbol")
    String symbol;
    @SerializedName("LastPrice")
    String lastprice;
    @SerializedName("ChangePercent")
    String change;
    @SerializedName("MarketCap")
    String market;
}