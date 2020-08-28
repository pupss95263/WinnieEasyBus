package com.example.easybus;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Page8Activity extends AppCompatActivity {
    //SelectPicPopupWindow menuWindow; //自訂義的彈出框類別(SelectPicPopupWindow)
    TextView mEnteredName;
    ImageView mPforfilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page8);

        //隱藏title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mEnteredName=findViewById(R.id.EnteredName);
        mPforfilepic=findViewById(R.id.profilepic);

        /*
        mPforfilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //實例化SelectPicPopupWindow
                menuWindow=new SelectPicPopupWindow(Page8Activity.this,itemsOnClick);
                //設計彈出框
                menuWindow.showAtLocation(Page8Activity.this.findViewById(R.id.profilepic), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        private OnClickListener itemsOnClick=new OnClickListener(){

        };
        */

    }
}

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
