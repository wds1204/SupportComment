package com.sun.bsdiffdemo.diff;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Objects;

/**
 * Copyright (C), 2016-2019, 未来酒店
 * File: RecyclerItemCallback.java
 * Author: wds_sun
 * Date: 2019-12-30 11:55
 * Description:
 */
public class RecyclerItemCallback extends DiffUtil.Callback {


    private List<Bean> mOldDataList;
    private List<Bean> mNewDataList;

    public RecyclerItemCallback(List<Bean> oldDataList, List<Bean> newDataList) {
        this.mOldDataList = oldDataList;
        this.mNewDataList = newDataList;
    }

    @Override
    public int getOldListSize() {
        return mOldDataList!=null?mOldDataList.size():0;
    }

    @Override
    public int getNewListSize() {
        return mNewDataList!=null?mNewDataList.size():0;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(mOldDataList.get(oldItemPosition).id,mNewDataList.get(newItemPosition).id);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(mOldDataList.get(oldItemPosition).content,mNewDataList.get(newItemPosition).content);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Bean oldBean = mOldDataList.get(oldItemPosition);
        Bean newBean = mNewDataList.get(newItemPosition);
        if (!Objects.equals(oldBean.content, newBean.content)) {
            return "content";
        }
        return null;
    }
}
