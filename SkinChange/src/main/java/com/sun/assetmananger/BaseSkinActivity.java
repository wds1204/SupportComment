package com.sun.assetmananger;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.ViewCompat;

import com.sun.assetmananger.skin.SkinManager;
import com.sun.assetmananger.skin.SkinResource;
import com.sun.assetmananger.skin.SkinSupport;
import com.sun.assetmananger.skin.attr.SkinAttr;
import com.sun.assetmananger.skin.attr.SkinView;
import com.sun.assetmananger.skin.callback.ISkinChangeListener;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2016-2019, 未来酒店
 * File: BaseSkinActivity.java
 * Author: wds_sun
 * Date: 2019-09-20 17:19
 * Description: 换肤BaseActivity
 */
public abstract class BaseSkinActivity extends AppCompatActivity implements ISkinChangeListener {
    private static final String TAG = BaseSkinActivity.class.getName();
    private SkinCompatViewInflater skinCompatViewInflater = null;
    Window mWindow;


    private static final boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, this);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if (skinCompatViewInflater == null) {
            skinCompatViewInflater = new SkinCompatViewInflater();
        }
        //拦截view的创建，获取view后自己解析
        boolean inheritContext = false;
        if (IS_PRE_LOLLIPOP) {
            inheritContext = (attrs instanceof XmlPullParser)
                    // If we have a XmlPullParser, we can detect where we are in the layout
                    ? ((XmlPullParser) attrs).getDepth() > 1
                    // Otherwise we have to use the old heuristic
                    : shouldInheritContext((ViewParent) parent);
        }

        //1：创建View;
        //2.解析属性;
        //3.统一交给SkinManager管理
        View view = skinCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                IS_PRE_LOLLIPOP, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                VectorEnabledTintResources.shouldBeUsed() /* Only tint wrap the context if enabled */
        );
        Log.e(TAG, "View====name>" + name);
        Log.e(TAG, "View====>" + view);

        if (view != null) {
            //解析
            List<SkinAttr> skinAttrs = SkinSupport.getSkinAttrs(context, attrs);
            if (skinAttrs!=null&&skinAttrs.size() != 0) {
                //SkinManager管理
                addSkinManager(view, skinAttrs);

            }

        }

        return view;
    }

    private void addSkinManager(View view, List<SkinAttr> skinAttrs) {

        SkinView skinView = new SkinView(view, skinAttrs);

        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);

        if (skinViews == null) {
            skinViews = new ArrayList<>();
            SkinManager.getInstance().registerSkinView(skinViews, this);
        }
        skinViews.add(skinView);

//        // 如果需要换肤
        if(SkinManager.getInstance().needChangeSkin()) {
            SkinManager.getInstance().changeSkin(skinView);
        }
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = mWindow.getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }

    @Override
    public void changeSkin(SkinResource resource) {

    }

    @Override
    protected void onDestroy() {
        SkinManager.getInstance().unregister(this);
        super.onDestroy();
    }
}
