package com.example.easybus;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Page8Activity extends AppCompatActivity {
    SelectPicPopupWindow menuWindow; //自訂義的彈出框類別(SelectPicPopupWindow)

    TextView mEnteredName;

    public static final int TAKE_PHOTO = 1;
    //public static final int SELECT_PHOTO=2;
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

    private View.OnClickListener itemsOnClick=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()){
                //拍照
                case R.id.takePhotoBtn:
                    takePhoto();
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

    public void takePhoto() {
        //創新File,保存鏡頭拍的照片,並將它存在SD卡
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");

        //照片更換
        try {
            //如果上次的照片存在,就刪除
            if (outputImage.exists()) {
                outputImage.delete();
            }
            //創一個新文件
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //如果Android版本大於7.0
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(Page8Activity.this, "com.example.EasyBus.fileprovider",outputImage);
        }else{
            imageUri = Uri.fromFile(outputImage);
        }

        //申請動態權限
        if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity)mContext,new String[]{Manifest.permission.CAMERA},100);
        }else{
            //啟動相機程序
            startCamera();
        }
    }

    private void startCamera(){
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        //指定圖片輸出地址為imageUri
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    // 使用startActivityForResult()方法開啟Intent的回調
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        switch(requestCode){
            case TAKE_PHOTO:
                if (requestCode==RESULT_OK){
                    try{
                        //將圖片解析成bitmap對象
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        //將圖片顯示出來
                        mPforfilepic.setImageBitmap(bitmap);
                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void onRequestPermissionResult(int requestCode,@Nullable String[] permissions, @Nullable int[] grantResults){
        switch(requestCode){
            case 100:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //啟用相機程序
                    startCamera();
                }else{
                    Toast.makeText(mContext,"沒有權限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

}
