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
    private TextView room_name,room_level,room_used,things;
    private Button purchase;
    private String hotel="";
    private String room_class = "";
    private String hotel_num = "";
    private String room_num = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail_);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String i = bundle.getString("index");
        room_num = i;
        hotel_num = bundle.getString("num");
        room_name = (TextView)findViewById(R.id.room_details_name);
        room_level = (TextView)findViewById(R.id.room_details_level);
        room_used = (TextView)findViewById(R.id.room_details_used);
        purchase = (Button)findViewById(R.id.room_details_purchase);
        things = (TextView)findViewById(R.id.room_details_things);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("findroom",firurl+"findroombynum/"+i);
                resultJson1(firurl+"findroombynum/"+i);
            }
        });
        thread.start();

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(room_detail_Activity.this,testActivity.class);
                intent1.putExtra("index",room_class);
                intent1.putExtra("hotel",hotel);
                intent1.putExtra("num",hotel_num);
                intent1.putExtra("room_num",room_num);
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
                //room_level.setText("价格："+(String)ma.get("room_used"));
                room_class = (String)ma.get("room_name");
                room_used.setText((String)ma.get("hotel_level"));
                things.setText((String)ma.get("things"));
                //settime.setText("订单创建时间："+(String)ma.get("tips_rateyear")+"年"+(String)ma.get("tips_ratemonth")+"月"+(String)ma.get("tips_rateday")+"日");
                //personname.setText("姓名："+(String)ma.get("tips_personname"));
                if((int)ma.get("room_used")>0){
                    room_level.setText("房间未满，可以使用");
                    purchase.setClickable(true);
                } else {
                    room_level.setText("房间已满，不可使用");
                    purchase.setVisibility(View.GONE);
                    purchase.setClickable(false);
                }
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
        map.put("room_used", jsonObject.getInt("room_used"));
        map.put("hotel_level", jsonObject.getString("hotel_level"));
        map.put("from_hotel", jsonObject.getString("from_hotel"));
        map.put("things", jsonObject.getString("room_things"));
 //       map.put("tips_rateday", jsonObject.getString("tips_rateday"));
//        map.put("tips_ratemonth", jsonObject.getString("tips_ratemonth"));
//        map.put("tips_rateyear", jsonObject.getString("tips_rateyear"));
//        map.put("tips_personname", jsonObject.getString("tips_personname"));
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
