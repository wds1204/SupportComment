package com.sun.assetmananger;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sun.assetmananger.skin.SkinManager;
import com.sun.assetmananger.skin.SkinResource;

import java.io.File;

public class MainActivity extends BaseSkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                            当某条权限之前已经请求过，并且用户已经拒绝了该权限时，shouldShowRequestPermissionRationale ()方法返回的是true
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                }
            }
        } else {
//            createOutPublicDir();
        }


    }

    public void change(View view) {
        String skinPath = Environment.getExternalStorageDirectory() + File.separator + "plugin.skin";

        //手机内部sdcard文件夹下,不要放在扩展的sdcard中.
        SkinManager.getInstance().loadSkin(skinPath);


    }

    public void jump(View view) {

        startActivity(new Intent(this,MainActivity.class));
    }

    public void reset(View view) {

        SkinManager.getInstance().restoreDefault();
    }

    @Override
    public void changeSkin(SkinResource resource) {
        super.changeSkin(resource);
        Toast.makeText(this, "换肤", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
