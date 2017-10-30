package com.kyaracter.foursquare.okhttp.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kyaracter.foursquare.okhttp.library.OkHttpIOHandler;

import fi.foyt.foursquare.api.FoursquareApi;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FoursquareApi foursquareApi = new FoursquareApi("", "", "", new OkHttpIOHandler(new OkHttpClient()));
    }
}
