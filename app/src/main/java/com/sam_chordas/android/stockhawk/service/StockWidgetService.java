package com.sam_chordas.android.stockhawk.service;

/**
 * Created by rahulravindran on 11/05/16.
 */

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.rest.Utils;

import java.util.ArrayList;
import java.util.List;

public class StockWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StockViewsFactory(this.getApplicationContext(),
                intent);
    }

    class StockViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context ctxt;
        private int appWidgetId;
        private List<Stock> stocks;

        public StockViewsFactory(Context ctxt, Intent intent) {
            this.ctxt = ctxt;
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

        }

        @Override
        public void onCreate() {
            // no-op
        }

        @Override
        public void onDestroy() {
            // no-op
        }

        @Override
        public int getCount() {
            return stocks.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews row = new RemoteViews(ctxt.getPackageName(),
                    R.layout.list_item_quote);

            String symbol = stocks.get(position).symbol;
            String bidPrice = stocks.get(position).bidPrice;

            String change = stocks.get(position).change;
            row.setTextViewText(R.id.stock_symbol, symbol);
            row.setTextViewText(R.id.bid_price, bidPrice);
            row.setTextViewText(R.id.change, change);
            if (stocks.get(position).isUp) {
                row.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_green);
            } else {
                row.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_red);
            }
            return row;
        }

        @Override
        public RemoteViews getLoadingView() {
            return (null);
        }

        @Override
        public int getViewTypeCount() {
            return (1);
        }

        @Override
        public long getItemId(int position) {
            return (position);
        }

        @Override
        public boolean hasStableIds() {
            return (true);
        }

        @Override
        public void onDataSetChanged() {
            Cursor cursor = ctxt.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                    new String[]{QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
                            QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
                    QuoteColumns.ISCURRENT + " = ?",
                    new String[]{"1"},
                    null);
            stocks = new ArrayList<>(cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    stocks.add(new Stock(
                            cursor.getString(cursor.getColumnIndex("symbol")),
                            cursor.getString(cursor.getColumnIndex("bid_price")),
                            Utils.showPercent ? cursor.getString(cursor.getColumnIndex("percent_change")) : cursor.getString(cursor.getColumnIndex("change")),
                            cursor.getInt(cursor.getColumnIndex("is_up")) == 1
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

    static class Stock {
        public final String symbol;
        public final String bidPrice;
        public final String change;
        public final boolean isUp;

        public Stock(String symbol, String bidPrice, String change, boolean isUp) {
            this.symbol = symbol;
            this.bidPrice = bidPrice;
            this.change = change;
            this.isUp = isUp;
        }
    }
}