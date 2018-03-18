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
 * Created by dirase on 2018/3/18.
 */

public class hotel_rate_adapter  extends BaseAdapter {
    private Context mContext;
    private List<String> mList1 = new ArrayList<>();
    private List<String> mList2 = new ArrayList<>();

    public hotel_rate_adapter(Context context, List<String> list1,List<String> list2) {
        mContext = context;
        mList1 = list1;
        mList2 = list2;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.exlistview_item, null);
            viewHolder.text1 = (TextView) view.findViewById(R.id.ex_text1);
            viewHolder.text2 = (TextView) view.findViewById(R.id.ex_text2);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if(!mList1.get(i).equals("0")){
        viewHolder.text1.setText(mList1.get(i));
        viewHolder.text2.setText(mList2.get(i));
        }
        return view;

    }




    class ViewHolder {
        TextView text1,text2;
    }
}
