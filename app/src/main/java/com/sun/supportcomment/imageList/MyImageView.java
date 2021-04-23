package com.sun.supportcomment.imageList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class MyImageView extends ImageView {
    public MyImageView(Context context) {
        this(context,null);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
}
