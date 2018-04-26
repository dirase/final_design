package com.dirase.hotelsys;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.dirase.hotelsys.first.firurl;

public class history_details_Activity extends AppCompatActivity {
    private String i,room_num,hotel_num;
    private TextView start_time,end_time,tips_num,tips_room,jiage;
    private Button tips_hotel,tips_rate,tips_cancel;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private String d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details_);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        i = bundle.getString("index");
        d= bundle.getString("index");
        room_num = "";
        hotel_num = "";
        start_time=(TextView)findViewById(R.id.history_details_start_time);
        end_time = (TextView)findViewById(R.id.history_details_end_time);
        tips_num =(TextView)findViewById(R.id.history_details_tips_num);
        tips_room=(TextView)findViewById(R.id.history_details_room);//联系电话
        tips_hotel=(Button)findViewById(R.id.history_details_hotel);
        tips_rate = (Button)findViewById(R.id.history_details_rate);
        jiage = (TextView)findViewById(R.id.history_details_jiage);
        tips_cancel = (Button)findViewById(R.id.history_details_cancel);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("cancel",firurl+"findtipsbynum/"+i);
                resultJson1(firurl+"findtipsbynum/"+i);
            }
        });
        thread.start();
//        tips_room.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent1 = new Intent(history_details_Activity.this,room_detail_Activity.class);
//                intent1.putExtra("index",room_num);
//                startActivity(intent1);
//            }
//        });
        tips_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(history_details_Activity.this,hotel_Activity.class);
                intent1.putExtra("index",hotel_num);
                startActivity(intent1);
            }
        });
        tips_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratedialog dialog = new ratedialog(history_details_Activity.this,i,hotel_num);
                dialog.show();
            }
        });
        tips_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert = null;
                builder = new AlertDialog.Builder(history_details_Activity.this);
                alert = builder
                        .setTitle("警告：")
                        .setMessage("确认取消订单")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Thread thread1 = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            readParse(firurl+"cancel/"+d);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread1.start();

                            }
                        })
                        .create();
                alert.show();
            }
        });
    }
    private String  resultJson1(String url) {
        String string = "0";
        try {
            Iterator<HashMap<String, Object>> it=Analysis1(readParse(url)).iterator();
            while (it.hasNext()) {
                Map<String, Object> ma = it.next();
                Looper.prepare();
                start_time.setText( (String)ma.get("tips_time_start"));
                end_time.setText( (String)ma.get("tips_time_stop"));
                tips_num.setText( "tips_num "+(String)ma.get("tips_num"));
                tips_room.setText( "phone: "+(String)ma.get("tips_phone"));
                tips_hotel.setText( "tips_hotel "+(String)ma.get("tips_hotel"));
                //room_num = (String)ma.get("tips_room");
                hotel_num = (String)ma.get("tips_hotel");
                jiage.setText("金额："+(String)ma.get("tips_room"));
                Looper.loop();

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("json","error1");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("json","error2");
            resultJson1(url);
            return "0";
        }
        Log.e("json","result:"+string);
        return string;
    }

    private static ArrayList<HashMap<String, Object>> Analysis1(String jsonStr)
            throws JSONException {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject jsonObject = new JSONObject(jsonStr);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("tips_num", jsonObject.getString("tips_num"));
        map.put("tips_time_start", jsonObject.getString("tips_time_start"));
        map.put("tips_time_stop", jsonObject.getString("tips_time_stop"));
        map.put("tips_phone", jsonObject.getString("tips_phone"));
        map.put("tips_hotel", jsonObject.getString("tips_hotel"));
        map.put("tips_star", jsonObject.getString("tips_star"));
        map.put("tips_room", jsonObject.getString("tips_room"));
        list.add(map);
        return list;
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
        Log.e("json","readParse"+new String(outStream.toByteArray()));
        return new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
    }

}
