package com.jkrm.education.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hzw.baselib.widgets.PhotoFragmentDialog;
import com.jkrm.education.R;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/4/29 17:18
 */

public class CustomGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;
    private OnDelelteClickListener mOnDelelteClickListener;

    public void setOnDelelteClickListener(OnDelelteClickListener onDelelteClickListener) {
        mOnDelelteClickListener = onDelelteClickListener;
    }

    public CustomGridAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = View.inflate(mContext, R.layout.pro_pic_item_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        ImageView imageCenter = view.findViewById(R.id.iv_img_center);
        final ImageView imgClose = (ImageView) view.findViewById(R.id.img_close);
        if (i + 1 == mList.size()) {
            imageCenter.setImageBitmap(BitmapFactory.decodeResource(mContext.
                    getResources(), R.mipmap.icon_addpic_unfocused));
            imgClose.setVisibility(View.GONE);
           /* if (i == img_max_length) {
                imageView.setVisibility(View.GONE);
            }*/
        } else {
            Glide.with(mContext).load(mList.get(i+1)).into(imageView);
            //ImageLoader.getInstance().displayImage("file://" + imgList.get(position + 1), imageView);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size() > 1) {
                    mOnDelelteClickListener.deleteButtonClick(i+1);
                    mList.remove(i + 1);
                    notifyDataSetChanged();
                }
            }
        });
        return view;
    }
    //实现回调功能
    public interface OnDelelteClickListener {
         void deleteButtonClick(int postion);


    }
}
