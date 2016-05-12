package com.sam_chordas.android.stockhawk.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.models.StockDetailModel;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by rahulravindran on 12/05/16.
 */
public class CurrentStockAdapter extends ArrayAdapter<StockDetailModel> {

    private final Context context;
    private final List<StockDetailModel> data;

    public CurrentStockAdapter(Context context, List<StockDetailModel> data) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.stockdetail_listview_item, parent, false);
        }
        //Log.v("CurrentStockAdapter", "Entered GETVIEW");
        TextView label = ButterKnife.findById(view, R.id.details_label);
        label.setText(data.get(position).getLabel());
        TextView value = ButterKnife.findById(view, R.id.details_value);
        if (!(data.get(position).getLabel().equalsIgnoreCase("change") ||
                data.get(position).getLabel().equalsIgnoreCase("changeytd")))
            value.setText(data.get(position).getValue());
        else {
            String[] changeArray = data.get(position).getValue().split(" ");
            DecimalFormat df2 = new DecimalFormat("###.##");
            double change = Double.parseDouble(changeArray[0]);
            double changepercent = Double.parseDouble(changeArray[1]);
            if (changepercent > 0) {
                SpannableStringBuilder ssb = new SpannableStringBuilder(df2.format(change) + "(+" +
                        df2.format(changepercent) + "%) ");
                Bitmap arrow = BitmapFactory.decodeResource(view.getResources(), R.drawable.up);
                ssb.setSpan(new ImageSpan(view.getContext(), arrow), ssb.length() - 1, ssb.length(),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                value.setText(ssb, TextView.BufferType.SPANNABLE);
            } else {
                SpannableStringBuilder ssb = new SpannableStringBuilder(df2.format(change) + "(" +
                        df2.format(changepercent) + "%) ");
                Bitmap arrow = BitmapFactory.decodeResource(view.getResources(), R.drawable.down);
                ssb.setSpan(new ImageSpan(view.getContext(), arrow), ssb.length() - 1, ssb.length(),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                value.setText(ssb, TextView.BufferType.SPANNABLE);
            }
        }
        return view;
    }
}