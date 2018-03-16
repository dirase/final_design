package com.dirase.hotelsys;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.dirase.hotelsys.R.id.textView;
import static com.dirase.hotelsys.R.id.textView3;
import static com.dirase.hotelsys.first.firurl;

/**
 * Created by dirase on 2018/3/16.
 */

public class rate_adapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList1 = new ArrayList<>();
    private List<String> mList2 = new ArrayList<>();
    private List<String> mList3 = new ArrayList<>();
    private List<String> mList4 = new ArrayList<>();
    private List<String> mList5 = new ArrayList<>();

    public rate_adapter(Context context, List<String> list1,List<String> list2,List<String> list3,List<String> list4,List<String> list5) {
        mContext = context;
        mList1 = list1;
        mList2 = list2;
        mList3 = list3;
        mList4 = list4;
        mList5 = list5;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.admin_rate_list, null);
            viewHolder.delete = (Button) view.findViewById(R.id.rate_delete);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.rate_text1);
            viewHolder.textView2 = (TextView) view.findViewById(R.id.rate_text2);
            viewHolder.textView3 = (TextView) view.findViewById(R.id.rate_text3);
            viewHolder.textView4 = (TextView) view.findViewById(R.id.rate_text4);
            viewHolder.textView4.setMovementMethod(new ScrollingMovementMethod(){});
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView1.setText(mList1.get(i));
        viewHolder.textView2.setText(mList2.get(i));
        viewHolder.textView3.setText(mList3.get(i));
        viewHolder.textView4.setText(mList4.get(i));
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("delete",firurl+"deleterate/"+mList5.get(i)+"-"+mList1.get(i)+"-"+mList3.get(i));
                            readParse(firurl+"deleterate/"+mList5.get(i)+"-"+mList1.get(i)+"-"+mList3.get(i));
                            mList3.set(i,"0");
                            mList4.set(i,"null");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                mOnItemDeleteListener.onDeleteClick(i);
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
        Button delete;
        TextView textView1,textView2,textView3,textView4;
    }

    public static String readParse(String urlPath) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        Log.e("json",new String(outStream.toByteArray()));
        return new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
    }

}

