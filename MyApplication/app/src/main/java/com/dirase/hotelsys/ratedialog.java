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
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
    String id = "";
    String hotel = "";

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
                            readParse(firurl+"rate/"+id+"-1-"+hotel);
                            dismiss();

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
                            readParse(firurl+"rate/"+id+"-2-"+hotel);
                            dismiss();
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
                            readParse(firurl+"rate/"+id+"-3-"+hotel);
                            dismiss();
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
                            readParse(firurl+"rate/"+id+"-4-"+hotel);
                            dismiss();
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
                            readParse(firurl+"rate/"+id+"-5-"+hotel);
                            dismiss();
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
