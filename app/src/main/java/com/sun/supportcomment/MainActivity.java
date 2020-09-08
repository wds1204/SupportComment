package com.sun.supportcomment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("TAG", "MainActivity====>onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG", "MainActivity====>onStop");
    }

    public void memoryFileTemp(View view) {
    }
}
