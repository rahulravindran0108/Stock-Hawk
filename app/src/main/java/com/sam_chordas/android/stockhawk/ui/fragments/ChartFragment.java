package com.sam_chordas.android.stockhawk.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.sam_chordas.android.stockhawk.R;

import butterknife.ButterKnife;

/**
 * Created by rahulravindran on 12/05/16.
 */
public class ChartFragment extends Fragment {

    public static ChartFragment newInstance(String symbol) {
        Bundle args = new Bundle();
        args.putString("SYMBOL", symbol);
        ChartFragment chartFragment = new ChartFragment();
        chartFragment.setArguments(args);
        return chartFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        String symbol = getArguments().getString("SYMBOL");
        WebView webview = ButterKnife.findById(view, R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://empyrean-aurora-455.appspot.com/charts.php?symbol=" + symbol);
        return view;
    }
}