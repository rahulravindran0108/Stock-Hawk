package com.sam_chordas.android.stockhawk.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sam_chordas.android.stockhawk.ui.fragments.ChartFragment;
import com.sam_chordas.android.stockhawk.ui.fragments.CurrentStockFragment;
import com.sam_chordas.android.stockhawk.ui.fragments.NewsFragment;

/**
 * Created by rahulravindran on 12/05/16.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    String symbol;
    CurrentStockFragment currentStockFragment;
    ChartFragment chartFragment;
    NewsFragment newsFragment;
    public SectionsPagerAdapter(FragmentManager fm, String symbol) {
        super(fm);
        this.symbol = symbol;
        currentStockFragment = CurrentStockFragment.newInstance(symbol);
        chartFragment = ChartFragment.newInstance(symbol);
        newsFragment = NewsFragment.newInstance(symbol);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return currentStockFragment;
            case 1:
                return chartFragment;
            case 2:
                return newsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "CURRENT";
            case 1:
                return "HISTORICAL";
            case 2:
                return "NEWS";
        }
        return null;
    }
}