package com.example.easybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText mPassword, mEmail;
    TextView mLogintext,mForgetext;
    ImageButton backBtn;
    Button mBtn;
    ProgressBar mProgressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //隱藏title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mPassword=findViewById(R.id.password);
        mEmail=findViewById(R.id.Email);
        mLogintext=findViewById(R.id.logintext);
        mBtn=findViewById(R.id.RegisterBtn);
        mForgetext=findViewById(R.id.Forgetext);
        backBtn=findViewById(R.id.back);

        fAuth=FirebaseAuth.getInstance();
        mProgressBar=findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String _email=mEmail.getText().toString().trim();
                final String _password=mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(_email)){
                    mEmail.setError("請輸入電子信箱");
                    return;
                }
                if(TextUtils.isEmpty(_password)) {
                    mPassword.setError("請輸入密碼");
                    return;
                }
                if(_password.length()<6){
                    mPassword.setError("密碼至少要6位數");
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);

                //authentication 帳密驗證
                fAuth.signInWithEmailAndPassword(_email,_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"登入成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Page8Activity.class));
                        }else{
                            Toast.makeText(Login.this,"登入失敗"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        //跳頁到"註冊"
        mLogintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        //跳頁到"忘記密碼"
        mForgetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgetPassword.class));
            }
        });

        //返回健(回需求者選單)
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Page3Activity.class));
            }
        });
    }
}