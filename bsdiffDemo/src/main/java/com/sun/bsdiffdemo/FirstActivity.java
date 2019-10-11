package com.sun.bsdiffdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Log.e("TAG", "Build.VERSION.SDK_INT"+ Build.VERSION.SDK_INT);

    }

    public void intent(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }

    public void newIntent(View view) {

        startActivity(new Intent(this,SecActivity.class));
    }
}
