package com.sam_chordas.android.stockhawk.api;

import com.sam_chordas.android.stockhawk.models.NewsModel;
import com.sam_chordas.android.stockhawk.models.StockDetailModel;
import com.sam_chordas.android.stockhawk.models.StockModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rahulravindran on 12/05/16.
 */
public interface MyAPI {

    @GET("mservice.php")
    Call<List<StockDetailModel>> getStockDetails(@Query("symbol") String symbol);

    @GET("mservice.php")
    Call<List<NewsModel>> getNews(@Query("news") String symbol);

    @GET("mservice.php")
    Call<List<StockModel>> getStocks(@Query("batch") String symbols);
}
