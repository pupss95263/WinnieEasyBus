package com.example.easybus;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Page8Activity extends AppCompatActivity {
    TextView mEnteredName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page8);

        //隱藏title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mEnteredName=findViewById(R.id.EnteredName);

       /* FirebaseDatabase database=FirebaseDatabase.getInstance();//連結資料庫
        DatabaseReference databaseReference=database.getReference("Users").child("fullName");//連結資料(名稱:Users)

        //addListenerForSingleValueEvent => 從Database中讀值
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                mEnteredName.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }); */

    }
}