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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText mPassword, mEmail;
    TextView mLogintext,mForgetext;
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

        fAuth=FirebaseAuth.getInstance();
        mProgressBar=findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();

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

                //authenticate the user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"登入成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Page3Activity.class));
                        }else{
                            Toast.makeText(Login.this,"登入失敗"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                });

                mLogintext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),Register.class));
                    }
                });

                mForgetext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText resetMail=new EditText(v.getContext());
                        AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(v.getContext());
                        passwordResetDialog.setTitle("重設密碼？");
                        passwordResetDialog.setMessage("輸入電子信箱");
                        passwordResetDialog.setView(resetMail);

                        passwordResetDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String mail=resetMail.getText().toString();
                                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Login.this, "是", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Login.this, "否"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                        passwordResetDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        passwordResetDialog.create().show();
                    }
                });

            }
        });

    }
}