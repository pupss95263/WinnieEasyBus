package com.example.easybus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Page702Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page702);
        //隱藏title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //跳頁到回答錯誤的頁面
        ImageButton ib1 = (ImageButton)findViewById(R.id.ib1);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1 = new Intent(Page702Activity.this,Page7falseActivity.class);
                startActivity(it1);
            }
        });

        //跳頁到回答正確的頁面
        ImageButton ib2 = (ImageButton)findViewById(R.id.ib2);
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it2 = new Intent(Page702Activity.this,Page7trueActivity.class);
                startActivity(it2);
            }
        });

    }
}
