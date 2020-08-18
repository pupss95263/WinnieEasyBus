package com.example.easybus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText mFullname, mPassword, mEmail;
    TextView mRegistertext;
    Button mReg;
    ProgressBar mProgressBar;
    FirebaseAuth fAuth;
    FirebaseDatabase myDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //隱藏title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mFullname=findViewById(R.id.fullname);
        mPassword=findViewById(R.id.password);
        mEmail=findViewById(R.id.Email);
        mRegistertext=findViewById(R.id.Registertext);
        mReg=findViewById(R.id.RegisterBtn);

        fAuth=FirebaseAuth.getInstance();
        myDataBase=FirebaseDatabase.getInstance();
        mProgressBar=findViewById(R.id.progressBar);

        mProgressBar.setVisibility(View.GONE);

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), Page8Activity.class));
            finish();
        }

        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String fullName=mFullname.getText().toString();

                final StoreFirebase storefirebase=new StoreFirebase(email,password,fullName);

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("請輸入電子信箱");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("請輸入密碼");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("密碼至少要6位數");
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);

                //使用者註冊
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"註冊成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            //存資料到FIREBASE
                            myDataBase.getReference().child(fAuth.getUid()).setValue(storefirebase);
                        }else{
                            Toast.makeText(Register.this,"註冊失敗"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mRegistertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}
