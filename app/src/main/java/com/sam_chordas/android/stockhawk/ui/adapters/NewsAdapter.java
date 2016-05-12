package com.sam_chordas.android.stockhawk.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.models.NewsModel;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by rahulravindran on 12/05/16.
 */
public class NewsAdapter extends ArrayAdapter<NewsModel> {
    private final Context context;
    private final List<NewsModel> data;

    public NewsAdapter(Context context, List<NewsModel> data) {
        super(context, 0, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.news_listview_item, parent, false);
        }
        TextView newsTitle = ButterKnife.findById(view, R.id.newstitle);
        TextView newsContent = ButterKnife.findById(view, R.id.content);
        TextView newsPublisher = ButterKnife.findById(view, R.id.publisher);
        TextView newsDate = ButterKnife.findById(view, R.id.date);
        newsTitle.setText(data.get(position).getTitle());
        newsTitle.setPaintFlags(newsTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        newsContent.setText(data.get(position).getContent());
        newsPublisher.setText(String.format("Publisher: %s", data.get(position).getPublisher()));
        newsDate.setText(String.format("Date: %s", data.get(position).getDate()));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(position).getUrl()));
                context.startActivity(browserIntent);
            }
        });
        return view;
    }
}