package com.sam_chordas.android.stockhawk.ui.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.api.MyAPI;
import com.sam_chordas.android.stockhawk.models.StockDetailModel;
import com.sam_chordas.android.stockhawk.ui.adapters.CurrentStockAdapter;
import com.squareup.picasso.Picasso;

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
public class CurrentStockFragment extends Fragment {
    LayoutInflater inflater;
    public static List<StockDetailModel> mCurrentStockDetails;

    public static CurrentStockFragment newInstance(String symbol) {
        //Log.v("NEWINSTANCECURRENT", symbol);
        Bundle args = new Bundle();
        args.putString("SYMBOL", symbol);
        CurrentStockFragment currentStockFragment = new CurrentStockFragment();
        currentStockFragment.setArguments(args);
        return currentStockFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_current_stock, container, false);
//        TextView textView = (TextView) rootView.findViewById(R.id.testview);
//        textView.setText("TESTING A CUSTOM FRAGMENT");
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        String symbol = getArguments().getString("SYMBOL");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://empyrean-aurora-455.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyAPI service = retrofit.create(MyAPI.class);
        Call<List<StockDetailModel>> call = service.getStockDetails(symbol);
        call.enqueue(new Callback<List<StockDetailModel>>() {
            @Override
            public void onResponse(Call<List<StockDetailModel>> call, Response<List<StockDetailModel>> response) {
                List<StockDetailModel> details = response.body();
//                for(StockDetailModel detail: details) {
//                    Log.v("RESPONSE", detail.getValue());
//                }
                StockDetailModel status = details.remove(0);
                if(status.getValue().equalsIgnoreCase("success")) {
                    mCurrentStockDetails = details;
                    CurrentStockAdapter adapter = new CurrentStockAdapter(view.getContext(), details);
                    ListView listView = ButterKnife.findById(view, R.id.stock_details_list);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<StockDetailModel>> call, Throwable t) {
                Log.v("Failed", "Failed...");
                Intent upIntent = NavUtils.getParentActivityIntent(getActivity());
                Bundle mBundle = new Bundle();
                mBundle.putString("ERROR", "TRUE");
                upIntent.putExtras(mBundle);
                NavUtils.navigateUpTo(getActivity(), upIntent);
            }
        });
        final String imageUri = "http://chart.finance.yahoo.com/t?s=" + symbol + "&lang=en-US&width=512&height=288";
        final ImageView mImageView = ButterKnife.findById(view, R.id.stock_image);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(view.getContext()).load(imageUri).into(mImageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog settingsDialog = new Dialog(view.getContext());
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                View dialogView = inflater.inflate(R.layout.my_dialog_layout, null);
                ImageView dImageView = ButterKnife.findById(dialogView, R.id.dialog_image);
                Picasso.with(dialogView.getContext()).load(imageUri).into(dImageView);
                dImageView.setMinimumHeight(500);
                dImageView.setMinimumWidth(1300);
                dImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                settingsDialog.setContentView(dialogView);
                settingsDialog.show();
            }
        });
    }

}