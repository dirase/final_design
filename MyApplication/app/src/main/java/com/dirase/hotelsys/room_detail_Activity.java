package com.dirase.hotelsys;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.dirase.hotelsys.R.id.hotel_hotel_name;
import static com.dirase.hotelsys.first.firurl;

public class room_detail_Activity extends AppCompatActivity {
    private TextView room_name,room_level,room_used;
    private Button purchase;
    private String hotel="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail_);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String i = bundle.getString("index");
        room_name = (TextView)findViewById(R.id.room_details_name);
        room_level = (TextView)findViewById(R.id.room_details_level);
        room_used = (TextView)findViewById(R.id.room_details_used);
        purchase = (Button)findViewById(R.id.room_details_purchase);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                resultJson1(firurl+"findroombynum/"+i);
            }
        });
        thread.start();

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(room_detail_Activity.this,testActivity.class);
                intent1.putExtra("index",i);
                intent1.putExtra("hotel",hotel);
                startActivity(intent1);
            }
        });

    }
    private void  resultJson1(String url) {
        try {
            Iterator<HashMap<String, Object>> it=Analysis1(readParse(url)).iterator();
            while (it.hasNext()) {
                Map<String, Object> ma = it.next();
                hotel = (String)ma.get("from_hotel");
                Looper.prepare();
                room_name.setText((String)ma.get("room_name"));
                room_level.setText((String)ma.get("room_used"));
                room_used.setText((String)ma.get("hotel_level"));
                Looper.loop();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("json","error1");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("json","error2");
            resultJson1(url);
        }
    }

    private static ArrayList<HashMap<String, Object>> Analysis1(String jsonStr)
            throws JSONException {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject jsonObject = new JSONObject(jsonStr);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("room_name", jsonObject.getString("room_name"));
        map.put("room_used", jsonObject.getString("room_used"));
        map.put("hotel_level", jsonObject.getString("hotel_level"));
        map.put("from_hotel", jsonObject.getString("from_hotel"));
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
