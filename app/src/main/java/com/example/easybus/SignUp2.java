package com.example.easybus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Pattern;



public class SignUp2 extends AppCompatActivity {

    //InetAddress ip;
    EditText mFullname, mPassword, mEmail;
    TextView mRegistertext;
    Button mReg;
    ProgressBar mProgressBar;
    RadioButton mRad1,mRad2;
    RadioGroup mRaG;
    String midentity="";
    String url1,url2,url3,ip;

    //取得IP
    private String getMyIp(){
        //新增一個WifiManager物件並取得WIFI_SERVICE
        WifiManager wifi_service = (WifiManager)getSystemService(WIFI_SERVICE);
        //取得wifi資訊
        WifiInfo wifiInfo = wifi_service.getConnectionInfo();
        //取得IP，但這會是一個詭異的數字，還要再自己換算才行
        int ipAddress = wifiInfo.getIpAddress();
        //利用位移運算和AND運算計算IP
        String ip = String.format("%d.%d.%d.%d",(ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));
        String binStr=Integer.toBinaryString(ipAddress);
        //String ip = Integer.valueOf(binStr,2).toString();
        System.out.println("ip    :"+ip);
        System.out.println("二進位:"+binStr);
        return ip;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mFullname=findViewById(R.id.fullname);
        mPassword=findViewById(R.id.password);
        mEmail=findViewById(R.id.Email);
        mRegistertext=findViewById(R.id.Registertext);
        mReg=findViewById(R.id.register);

        mRaG=findViewById(R.id.RadioGroup);
        mRad1=findViewById(R.id.radioButton1);
        mRad2=findViewById(R.id.radioButton2);

        mProgressBar=findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);



        mRegistertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login2.class);
                startActivity(intent);
                finish();
            }
        });
        //ip = getMyIp();
        GetIP getip = new GetIP();
        //ip = getip(name);


        url2 = "http://10.0.32.218/LoginRegister/signup.php";
        //Toast.makeText(this,url1,Toast.LENGTH_SHORT).show();
        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fullname,email,password,identity;
                fullname = String.valueOf(mFullname.getText());
                email = String.valueOf(mEmail.getText());
                password = String.valueOf(mPassword.getText());

                switch(mRaG.getCheckedRadioButtonId()){
                    case R.id.radioButton1:
                        midentity = "需求者";
                        break;
                    case R.id.radioButton2:
                        midentity = "照顧者";
                        break;
                }
                identity = midentity;
                Boolean match = validateEmailAddress(email);
                if (match==true) {
                    if (!fullname.equals("") && !email.equals("") && !password.equals("") && !identity.isEmpty()) {
                        mProgressBar.setVisibility(View.VISIBLE);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String[] field = new String[4];
                                field[0] = "fullname";
                                field[1] = "email";
                                field[2] = "password";
                                field[3] = "identity";

                                String[] data = new String[4];
                                data[0] = fullname;
                                data[1] = email;
                                data[2] = password;
                                data[3] = identity;


                                PutData putData = new PutData(url2, "POST", field, data);//小高電腦的IP
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        mProgressBar.setVisibility(View.GONE);
                                        String result = putData.getResult();
                                        if (result.equals("Sign Up Success")) {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), Login2.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"All filed required",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean validateEmailAddress(String email){ //加空值判斷
                String emailInput = email;

                if(!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
                    //Toast.makeText(this,"Email Validated Successfully!",Toast.LENGTH_SHORT).show();
                    return true;
                }else{
                    Toast.makeText(this,"InValid Email Address!",Toast.LENGTH_SHORT).show();
                    return false;
                }
    }
}
