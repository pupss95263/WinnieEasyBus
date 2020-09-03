package com.example.easybus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Page8Activity extends AppCompatActivity {
    SelectPicPopupWindow menuWindow; //自訂義的彈出框類別(SelectPicPopupWindow)

    TextView mEnteredName;

    public static final int TAKE_PHOTO = 1;
    private Uri imageUri;
    private Context mContext;
    ImageView mPforfilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page8);

        //隱藏title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mEnteredName = findViewById(R.id.EnteredName);
        mPforfilepic = findViewById(R.id.profilepic);
        mContext = Page8Activity.this;

        mPforfilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //實例化SelectPicPopupWindow
                menuWindow = new SelectPicPopupWindow(Page8Activity.this, itemsOnClick);
                //設計彈出框
                menuWindow.showAtLocation(Page8Activity.this.findViewById(R.id.profilepic), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                //拍照
                case R.id.takePhotoBtn:
                    //takePhoto();
                    break;
                //相簿選擇相片
                case R.id.SelectPhotoBtn:
                    //selectPhoto();
                    break;
                case R.id.cancelBtn:
                    break;
                default:
                    break;
            }
        }
    };
}
