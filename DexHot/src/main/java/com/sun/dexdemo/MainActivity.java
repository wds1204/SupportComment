package com.sun.dexdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
