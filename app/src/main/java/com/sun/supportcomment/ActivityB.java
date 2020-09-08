package com.sun.supportcomment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG", "ActivityB====>onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "ActivityB=====>onResume");
    }
}
