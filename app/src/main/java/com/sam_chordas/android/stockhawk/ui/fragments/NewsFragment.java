package com.sam_chordas.android.stockhawk.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.api.MyAPI;
import com.sam_chordas.android.stockhawk.models.NewsModel;
import com.sam_chordas.android.stockhawk.ui.adapters.NewsAdapter;

import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rahulravindran on 12/05/16.
 */
public class NewsFragment extends Fragment {


    public static NewsFragment newInstance(String symbol) {
        Bundle args = new Bundle();
        args.putString("SYMBOL", symbol);
        NewsFragment newsFragment = new NewsFragment();
        newsFragment.setArguments(args);
        return newsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news, container, false);

        String symbol = getArguments().getString("SYMBOL");

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://empyrean-aurora-455.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        MyAPI service = retrofit.create(MyAPI.class);
        Call<List<NewsModel>> call = service.getNews(symbol);
        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                List<NewsModel> newslist = response.body();
                for(NewsModel news: newslist) {
                    Log.v("RESPONSE", news.getTitle());
                }
                NewsAdapter newsAdapter = new NewsAdapter(view.getContext(), newslist);
                ListView listView = ButterKnife.findById(view, R.id.newslist);
                listView.setAdapter(newsAdapter);
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {

            }
        });

        return view;
    }
}