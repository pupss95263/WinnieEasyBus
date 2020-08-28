package com.example.easybus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Page801Activity extends AppCompatActivity {
    ImageView mAdd,mBackicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page801);

        //隱藏title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mAdd=findViewById(R.id.add);
        mBackicon=findViewById(R.id.backicon);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddFamMember.class));
            }
        });

        mBackicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),.class));
            }
        });

    }
}
