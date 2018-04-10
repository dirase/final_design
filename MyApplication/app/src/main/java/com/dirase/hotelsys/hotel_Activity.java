package com.dirase.hotelsys;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static com.dirase.hotelsys.first.firurl;

public class hotel_Activity extends AppCompatActivity {
    private TextView hotel_hotel_name,hotel_address,hotel_info;
    private Button hotel_details;
    private ListView hotel_rate_list;
    private List<String> mList1 = new ArrayList<>();
    private List<String> mList2 = new ArrayList<>();
    private ImageView star;
    String name ="";
    final hotel_rate_adapter adapter = new hotel_rate_adapter(hotel_Activity.this, mList1,mList2);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String i = bundle.getString("index");
        hotel_hotel_name = (TextView)findViewById(R.id.hotel_hotel_name);
        hotel_address = (TextView)findViewById(R.id.hotel_hotel_address);
        hotel_info = (TextView)findViewById(R.id.hotel_hotel_info);
        hotel_details = (Button)findViewById(R.id.hotel_hotel_detail);
        hotel_rate_list = (ListView)findViewById(R.id.hotel_rate_list);
        star = (ImageView)findViewById(R.id.hotel_hotel_star);
        hotel_rate_list.setAdapter(adapter);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                resultJson1(firurl+"findHotelInfo/"+i);
            }
        });
        thread.start();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Analysis(readParse(firurl+"findtipsbyhotel/"+i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        hotel_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(hotel_Activity.this,hotel_detailActivity.class);
                intent1.putExtra("index",""+name);
                startActivity(intent1);
            }
        });
    }

    private void  resultJson1(String url) {
        try {
            Iterator<HashMap<String, Object>> it=Analysis1(readParse(url)).iterator();
            while (it.hasNext()) {
                Map<String, Object> ma = it.next();
                Looper.prepare();
                hotel_hotel_name.setText("hotel_name "+(String)ma.get("name"));
                hotel_address.setText("hotel_adress "+(String)ma.get("hotel_adress"));
                hotel_info.setText("hotel_information "+(String)ma.get("hotel_information"));
                name = (String)ma.get("name");
                float star_num = ((int)ma.get("hotel_star_1")+2*(int)ma.get("hotel_star_2")+3*(int)ma.get("hotel_star_3")+4*(int)ma.get("hotel_star_4")+5*(int)ma.get("hotel_star_5"))/((int)ma.get("hotel_star_1")+(int)ma.get("hotel_star_2")+(int)ma.get("hotel_star_3")+(int)ma.get("hotel_star_4")+(int)ma.get("hotel_star_5"));
                if(star_num<2){
                    star.setBackgroundResource(R.drawable.one);
                }
                else if(star_num<3)star.setBackgroundResource(R.drawable.two);
                else if(star_num<4)star.setBackgroundResource(R.drawable.three);
                else if(star_num<5)star.setBackgroundResource(R.drawable.four);
                else star.setBackgroundResource(R.drawable.five);
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
        map.put("name", jsonObject.getString("name"));
        map.put("hotel_information", jsonObject.getString("hotel_information"));
        map.put("hotel_adress", jsonObject.getString("hotel_adress"));
        map.put("hotel_star_1", jsonObject.getInt("hotel_star_1"));
        map.put("hotel_star_2", jsonObject.getInt("hotel_star_2"));
        map.put("hotel_star_3", jsonObject.getInt("hotel_star_3"));
        map.put("hotel_star_4", jsonObject.getInt("hotel_star_4"));
        map.put("hotel_star_5", jsonObject.getInt("hotel_star_5"));
        map.put("hotel_phone", jsonObject.getString("hotel_phone"));
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

    private ArrayList<HashMap<String, Object>> Analysis(String jsonStr)
            throws JSONException {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("tips_star", jsonObject.getString("tips_star"));
            map.put("tips_text", jsonObject.getString("tips_text"));
            mList1.add(jsonObject.getString("tips_star"));
            mList2.add(jsonObject.getString("tips_text"));
                adapter.notifyDataSetChanged();
            list.add(map);
        }
        JSONObject jsonObject1 = new JSONObject(jsonStr);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("tips_star", jsonObject1.getString("tips_star"));
        map.put("tips_text", jsonObject1.getString("tips_text"));
        mList1.add(jsonObject1.getString("tips_star"));
        mList2.add(jsonObject1.getString("tips_text"));
            adapter.notifyDataSetChanged();
        list.add(map);
        return list;
    }
}
