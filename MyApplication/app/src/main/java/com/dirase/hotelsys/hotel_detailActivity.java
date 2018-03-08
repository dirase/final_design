package com.dirase.hotelsys;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

import static com.dirase.hotelsys.R.id.hotel_hotel_name;
import static com.dirase.hotelsys.first.firurl;

public class hotel_detailActivity extends AppCompatActivity {
    private List<String> mList1 = new ArrayList<>();
    private Context context = hotel_detailActivity.this;
    private  List<String> mList2 = new ArrayList<>();
    private  List<String> mList3 = new ArrayList<>();
    private  List<String> mList4 = new ArrayList<>();
    private ListView listView;
    final room_adapter adapter = new room_adapter(hotel_detailActivity.this, mList1,mList2,mList3,mList4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        this.listView = (ListView) findViewById(R.id.hotel_detail_exlistview);
        listView.setAdapter(adapter);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String i = bundle.getString("index");
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(hotel_detailActivity.this,room_detail_Activity.class));
            }
        });*/
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                resultJson1(firurl+"findroombyhotel/"+i);
            }
        });
        thread.start();
    }

    private void  resultJson1(String url) {
        try {
            Iterator<HashMap<String, Object>> it=Analysis1(readParse(url)).iterator();
            while (it.hasNext()) {
                Map<String, Object> ma = it.next();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("json","error1");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("json","error2");
        }
    }

    private  ArrayList<HashMap<String, Object>> Analysis1(String jsonStr)
            throws JSONException {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("room_name", jsonObject.getString("room_name"));
            map.put("room_used", jsonObject.getString("room_used"));
            map.put("room_num", jsonObject.getString("room_num"));
            map.put("hotel_level", jsonObject.getString("hotel_level"));
            mList1.add(jsonObject.getString("room_name"));
            mList2.add(jsonObject.getString("hotel_level"));
            mList3.add(jsonObject.getString("room_used"));
            mList4.add(jsonObject.getString("room_num"));
            adapter.notifyDataSetChanged();
            list.add(map);
        }
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
