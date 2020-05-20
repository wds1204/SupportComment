package com.sun.pluginsupport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NewActivity没有在AndroidManifest中注册
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                startActivity(intent);

            }
        });
        startService(new Intent(this,MyService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
