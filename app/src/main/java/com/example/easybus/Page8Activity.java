package com.example.easybus;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Page8Activity extends AppCompatActivity {
    SelectPicPopupWindow menuWindow; //自訂義的彈出框類別(SelectPicPopupWindow)

    Button mLogoutBtn;
    ImageView backBtn;
    CircleImageView mPforfilepic;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    DatabaseReference databaseReference;
    TextView mEnteredName,mEmail,mEMname,mEMphone;

    public static final int SELECT_PHOTO=1;
    public static final int TAKE_PHOTO = 3;
    private Uri imageUri;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page8);

        //隱藏title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //FloatingActionButton.Floatingbtn();

        backBtn=findViewById(R.id.backicon);
        mLogoutBtn=findViewById(R.id.LogoutBtn);
        mEnteredName = findViewById(R.id.EnteredName);
        mPforfilepic = findViewById(R.id.profilepic);
        mEmail=findViewById(R.id.textView3);
        mEMname=findViewById(R.id.textView2);
        mEMphone=findViewById(R.id.textView5);
        mContext = Page8Activity.this;

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(fUser.getUid());

        //獲取username
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user =snapshot.getValue(User.class);
                assert user != null;
                mEnteredName.setText(user.getFullName());
                mEmail.setText(user.getEmail());
                mEMname.setText(user.getEmName());
                mEMphone.setText(user.getEmPhone());
                if(user.getImageURL().equals("default")){
                    mPforfilepic.setImageResource(R.drawable.profile);
                }else{
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(mPforfilepic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Page8Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //返回健(回需求者選單)
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Page3Activity.class));
            }
        });

        //登出
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                Intent intent=new Intent(Page8Activity.this,Page3Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

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
                    openAlbum();
                    break;
                case R.id.cancelBtn:
                    break;
                default:
                    break;
            }
        }
    };

    private void openAlbum() {
        Intent in=new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(in,SELECT_PHOTO);
    }

    public void takePhoto() {
        //時間命名圖片的名稱
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);

        //儲存至DCIM資料夾
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File outputImage = new File(path,filename+".jpg");

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
        //指定圖片輸出地址為imageUri
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE"); //照相
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri); //指定圖片地址
        startActivityForResult(intent,TAKE_PHOTO); //啟動相機
        //拍完照startActivityForResult() 结果返回onActivityResult()函数
    }

    // 使用startActivityForResult()方法開啟Intent的回調
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        switch(requestCode){
            case TAKE_PHOTO:
                try{
                    //將圖片解析成bitmap對象
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    //將圖片顯示出來
                    mPforfilepic.setImageBitmap(bitmap);
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }
                break;

            case SELECT_PHOTO:
                Uri uri=data.getData();
                ContentResolver cr = this.getContentResolver();
                try {
                    //獲取圖片
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    mPforfilepic.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                super.onActivityResult(requestCode, resultCode, data);
                break;

            default:
                break;
        }
    }
}
