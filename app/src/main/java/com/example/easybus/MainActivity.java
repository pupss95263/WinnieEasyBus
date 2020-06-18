package com.example.easybus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_FINE_LOCATION_PERMISSION = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this,Page2Activity.class);
                startActivity(it);
            }
        });
        //隱藏title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //定位
        CheckPermission();
    }

    private void CheckPermission() {
        //如果使用者尚未允許權限
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 如果裝置版本是6.0（包含）以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 請求授權：第一個參數是請求授權的名稱，第二個參數是請求代碼
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION_PERMISSION);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 用 requestCode 來判斷這是哪一個權限
        if (requestCode == REQUEST_FINE_LOCATION_PERMISSION) {
            //如果使用者拒絕權限
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, "請設定允許位置資訊授權，才能定位", Toast.LENGTH_LONG).show();
                    Intent it = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri u = Uri.fromParts("package", getPackageName(), null);
                    it.setData(u);
                    startActivity(it);
                }
                else{
                    Toast.makeText(this, "本App需允許位置授權，才能定位", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        }
    }

}
