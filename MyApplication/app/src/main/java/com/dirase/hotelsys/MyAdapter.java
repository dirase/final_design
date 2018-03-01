package com.dirase.hotelsys;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dirase on 2018/2/10.
 */

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList1 = new ArrayList<>();
    private List<String> mList2 = new ArrayList<>();
    private List<String> mList3 = new ArrayList<>();

    public MyAdapter(Context context, List<String> list1,List<String> list2,List<String> list3) {
        mContext = context;
        mList1 = list1;
        mList2 = list2;
        mList3 = list3;
    }

    public int getCount() {
        return mList1.size();
    }

    public Object getItem(int i) {
        return mList1.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.first_listview_item, null);
            viewHolder.mButton1 = (Button) view.findViewById(R.id.item_btn1);
            viewHolder.mButton2 = (Button) view.findViewById(R.id.item_btn2);
            viewHolder.mButton3 = (Button) view.findViewById(R.id.item_btn3);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mButton1.setText(mList1.get(i));
        viewHolder.mButton2.setText(mList2.get(i));
        viewHolder.mButton3.setText(mList3.get(i));
        viewHolder.mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent=new Intent(mContext,hotel_Activity.class);
                mintent.putExtra("index",""+i);
                mContext.startActivity(mintent);
               // mOnItemDeleteListener.onDeleteClick(i);
            }
        });
        viewHolder.mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent=new Intent(mContext,hotel_Activity.class);
                mintent.putExtra("index",""+i);
                mContext.startActivity(mintent);
               // mOnItemDeleteListener.onDeleteClick(i);
            }
        });
        viewHolder.mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent=new Intent(mContext,hotel_Activity.class);
                mintent.putExtra("index", ""+i);
                mContext.startActivity(mintent);
               // mOnItemDeleteListener.onDeleteClick(i);
            }
        });
        return view;
    }

    /**
     * 删除按钮的监听接口
     */
    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }

    private onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }

    class ViewHolder {
        Button mButton1,mButton3,mButton2;
    }

}
