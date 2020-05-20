package com.sun.bsdiffdemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.sun.bsdiffdemo.utils.SignCheck;
import com.sun.bsdiffdemo.utils.UriparseUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    List list = new ArrayList<String>();


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private SignCheck signCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                }
            }
        }

        startService()


    }

    //生成差分包
    @SuppressLint("StaticFieldLeak")
    public void diff(View view) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected Void doInBackground(Void... params) {

                //获取现有apk的路径
                String oldPath = getApplicationInfo().sourceDir;
                Log.e("TAG", "oldPath==" + oldPath);
                //获取差分包的路径
                String patch = new File(Environment.getExternalStorageDirectory(), "diff.apk").getAbsolutePath();
                Log.e("TAG", "patch===" + patch);
                //获取新apk的路径
                String newPath = createNewApk().getAbsolutePath();
                Log.e("TAG", "newPath===" + newPath);
                //生成差分包
                diff(oldPath, newPath, patch);
                Log.e("TAG", "生成差分包成功");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

            }
        }.execute();


    }


    @SuppressLint("StaticFieldLeak")
    public void update(final View view) {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                if (signCheck == null) {
                    signCheck = new SignCheck(view.getContext());
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                //获取现有apk的路径
                String oldPath = getApplicationInfo().sourceDir;

                Log.e("TAG", "oldPath==" + oldPath);
                //获取差分包的路径
                String patch = new File(Environment.getExternalStorageDirectory(), "diff.apk").getAbsolutePath();

                Log.e("TAG", "patch===" + patch);


                //获取合成apk的路径
                String output = createNewApk().getAbsolutePath();
                Log.e("TAG", "newPath===" + output);

                //合成新apk

                patch(oldPath, patch, output);
                Log.e("TAG", "合成成功");
                return output;
            }

            @Override
            protected void onPostExecute(String file) {
                Log.e("TAG", "安装");
                try {
                    //校验签名
//                if (signCheck.check()) {
                    UriparseUtils.installApkComp(MainActivity.this, file);
//                } else {
//                    Toast.makeText(MainActivity.this, "校验签名失败", Toast.LENGTH_SHORT).show();
//                }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }.execute();

    }

    private File createNewApk() {
        File newApk = new File(Environment.getExternalStorageDirectory(), "new-app.apk");
        if (!newApk.exists()) {
            try {
                newApk.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newApk;
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    /**
     * 生成差分包
     * @param oldApk
     * @param newApk
     * @param diff
     */
    public native void diff(String oldApk, String newApk, String diff);

    /**
     * 合并差分包
     * @param oldApk
     * @param diff
     * @param newPath
     */
    public native void patch(String oldApk, String diff, String newPath);

    public native void nativeMain(int argc, int[] argv);

    public void nativeMain(View view) {
        int[] argv={4,1,5,7};
        nativeMain(4,argv);
    }
}
