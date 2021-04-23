package com.sun.supportcomment.imageList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sun.supportcomment.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class StaggeredGridAdapter extends RecyclerView.Adapter<StaggeredGridAdapter.LinearViewHolder> {


    private final Context mContext;
    private MyItemClickListener mItemClickListener;
    private List<String> list = new ArrayList<>();
    String []imsg={"https://img.cdn.com/a/a_1000x500.png","https://img.cdn.com/a/b_800x800.png",
            "https://img.cdn.com/a/c_500x1000.png", "https://img.cdn.com/a/d_700x2000.png",
            "https://img.cdn.com/a/e_400x2000.png","https://img.cdn.com/a/f_2000x200.png",
    };

    public StaggeredGridAdapter(Context mContext) {
        this.mContext = mContext;

        for (int i = 0; i < imsg.length; i++) {
            list.add(imsg[i]);
        }
    }

    @Override
    public StaggeredGridAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_staggere_grid_item, parent, false),mItemClickListener);
    }

    @Override
    public void onBindViewHolder(StaggeredGridAdapter.LinearViewHolder holder, int position) {
        if (position % 2 == 0) {
            holder.mImageView.setImageResource(R.drawable.img3);
        } else {
            holder.mImageView.setImageResource(R.drawable.img2);
        }
//        list.get(position).

    }

    @Override
    public int getItemCount() {
        return 6;

    }

    class LinearViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;

        private  MyItemClickListener mListener;

        public LinearViewHolder(View itemView,MyItemClickListener myItemClickListener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv);
            itemView.setOnClickListener(this);
            this.mListener=myItemClickListener;

        }

        @Override public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getPosition());
            }
        }
    }


    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }


    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }

}
