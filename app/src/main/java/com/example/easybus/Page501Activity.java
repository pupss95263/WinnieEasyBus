package com.example.easybus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Page501Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page501);
        //隱藏title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent it2 = getIntent();
        String d = it2.getStringExtra("destination");

        Intent it = new Intent();
        it.setAction(Intent.ACTION_VIEW);
        Uri u = Uri.parse("google.navigation:q="+d);
        it.setData(u);
        startActivity(it);
    }
}
