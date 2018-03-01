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

public class hotel_detailActivity extends AppCompatActivity {
    private List<String> mList1 = new ArrayList<>();
    private Context context = hotel_detailActivity.this;
    private  List<String> mList2 = new ArrayList<>();
    private  List<String> mList3 = new ArrayList<>();
    private ListView listView;
    final MyAdapter adapter = new MyAdapter(hotel_detailActivity.this, mList1,mList2,mList3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        this.listView = (ListView) findViewById(R.id.first_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(hotel_detailActivity.this,room_detail_Activity.class));
            }
        });
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

    private static ArrayList<HashMap<String, Object>> Analysis1(String jsonStr)
            throws JSONException {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject jsonObject = new JSONObject(jsonStr);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", jsonObject.getString("name"));
        map.put("hotel_information", jsonObject.getString("hotel_information"));
        map.put("hotel_adress", jsonObject.getString("hotel_adress"));
        map.put("hotel_star_1", jsonObject.getString("hotel_star_1"));
        map.put("hotel_star_2", jsonObject.getString("hotel_star_2"));
        map.put("hotel_star_3", jsonObject.getString("hotel_star_3"));
        map.put("hotel_star_4", jsonObject.getString("hotel_star_4"));
        map.put("hotel_star_5", jsonObject.getString("hotel_star_5"));
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
}
