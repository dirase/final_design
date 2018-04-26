package com.dirase.hotelsys;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import static android.R.string.no;
import static android.R.string.yes;
import static com.dirase.hotelsys.first.firurl;

/**
 * Created by dirase on 2018/3/8.
 */

public class ratedialog extends Dialog {

    private Context context;      // 上下文
    private int layoutResID;      // 布局文件id
    private ImageButton rate1,rate2,rate3,rate4,rate5;
    private EditText infotext;
    String id = "";
    String hotel = "";
    String info = "";
    int year, month, day;

    public ratedialog(Context context,String i,String h) {
        super(context, R.style.dialog_custom); //dialog的样式
        this.context = context;
        this.id = i;
        this.hotel = h;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_dialog);
        rate1 = (ImageButton)findViewById(R.id.rate1);
        rate2 = (ImageButton)findViewById(R.id.rate2);
        rate3 = (ImageButton)findViewById(R.id.rate3);
        rate4 = (ImageButton)findViewById(R.id.rate4);
        rate5 = (ImageButton)findViewById(R.id.rate5);
        infotext = (EditText)findViewById(R.id.rate_edit);
        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失
        rate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            date();
                            Log.e("shijian",firurl+"rate/"+id+"-1-"+hotel+"-"+infotext.getText().toString()+"-"+year+"-"+month+"-"+day);
                            if ("OK".equals(readParse(firurl+"rate/"+id+"-1-"+hotel+"-"+infotext.getText().toString()+"-"+year+"-"+month+"-"+day))){
                                dismiss();
                            }
                        } catch (Exception e) {
                            dismiss();
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();

            }
        });

        rate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            date();
                            Log.e("shijian",firurl+"rate/"+id+"-1-"+hotel+"-"+infotext.getText().toString()+"-"+year+"-"+month+"-"+day);
                            if ("OK".equals(readParse(firurl+"rate/"+id+"-1-"+hotel+"-"+infotext.getText().toString()+"-"+year+"-"+month+"-"+day))){
                                dismiss();
                            }
                        } catch (Exception e) {
                            dismiss();
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

        rate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            date();
                            Log.e("shijian",firurl+"rate/"+id+"-1-"+hotel+"-"+infotext.getText().toString()+"-"+year+"-"+month+"-"+day);
                            if ("OK".equals(readParse(firurl+"rate/"+id+"-1-"+hotel+"-"+infotext.getText().toString()+"-"+year+"-"+month+"-"+day))){
                                dismiss();
                            }
                        } catch (Exception e) {
                            dismiss();
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

        rate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            date();
                            Log.e("shijian",firurl+"rate/"+id+"-1-"+hotel+"-"+infotext.getText().toString()+"-"+year+"-"+month+"-"+day);
                            if ("OK".equals(readParse(firurl+"rate/"+id+"-1-"+hotel+"-"+infotext.getText().toString()+"-"+year+"-"+month+"-"+day))){
                                dismiss();
                            }
                        } catch (Exception e) {
                            dismiss();
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

        rate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            date();
                            Log.e("shijian",firurl+"rate/"+id+"-1-"+hotel+"-"+infotext.getText().toString()+"-"+year+"-"+month+"-"+day);
                            if ("OK".equals(readParse(firurl+"rate/"+id+"-1-"+hotel+"-"+infotext.getText().toString()+"-"+year+"-"+month+"-"+day))){
                                dismiss();
                            }
                        } catch (Exception e) {
                            dismiss();
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
    }

    private void date() {
        Calendar c = Calendar.getInstance();
        //年
        year = c.get(Calendar.YEAR);
        //月
        month = c.get(Calendar.MONTH)+1;
        //日
        day = c.get(Calendar.DAY_OF_MONTH);
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
