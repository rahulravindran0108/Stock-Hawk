package com.sam_chordas.android.stockhawk.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.api.MyAPI;
import com.sam_chordas.android.stockhawk.helpers.CustomViewPager;
import com.sam_chordas.android.stockhawk.models.StockModel;
import com.sam_chordas.android.stockhawk.ui.adapters.SectionsPagerAdapter;
import com.sam_chordas.android.stockhawk.ui.fragments.CurrentStockFragment;

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
public class DetailActivity extends AppCompatActivity {

    private static String stockSymbol = "";
    private Menu mOptionsMenu;
    private ShareDialog shareDialog;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String symbol = getIntent().getExtras().getString("SYMBOL");
        this.stockSymbol = symbol;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://empyrean-aurora-455.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyAPI service = retrofit.create(MyAPI.class);
        Call<List<StockModel>> call = service.getStocks(symbol);
        call.enqueue(new Callback<List<StockModel>>() {
            @Override
            public void onResponse(Call<List<StockModel>> call, Response<List<StockModel>> response) {
                for(StockModel stock: response.body()) {
                    getSupportActionBar().setTitle(stock.getName());
                }
            }

            @Override
            public void onFailure(Call<List<StockModel>> call, Throwable t) {

            }
        });

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), symbol);

        // Set up the ViewPager with the sections adapter.
        CustomViewPager mViewPager = ButterKnife.findById(this, R.id.container);
        mViewPager.setPagingEnabled(false);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = ButterKnife.findById(this, R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_facebook) {
            String title = "Current Stock Price of "+
                    CurrentStockFragment.mCurrentStockDetails.get(0).getValue()+","+
                    CurrentStockFragment.mCurrentStockDetails.get(2).getValue();

            shareDialog = new ShareDialog(this);
            callbackManager = CallbackManager.Factory.create();



            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    Log.v("INSIDESUCCESS", "I CAME HERE SUCCESS");
                    Toast.makeText(getApplicationContext(), "You shared the post", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                    Log.v("INSIDECANCEL", "I CAME HERE CANCEL");

                    Toast.makeText(getApplicationContext(), "You cancelled the post", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(getApplicationContext(), "Error while posting", Toast.LENGTH_SHORT).show();
                }
            });

            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentTitle(title)
                        .setContentDescription(
                                "Stock Information of " +
                                        CurrentStockFragment.mCurrentStockDetails.get(0).getValue())
                        .setImageUrl(Uri.parse("http://chart.finance.yahoo.com/t?s=" + stockSymbol +
                                "&lang=en-US&width=512&height=288"))
                        .setContentUrl(Uri.parse("http://finance.yahoo.com/q?s="+stockSymbol))
                        .build();
                shareDialog.show(content, ShareDialog.Mode.FEED);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

