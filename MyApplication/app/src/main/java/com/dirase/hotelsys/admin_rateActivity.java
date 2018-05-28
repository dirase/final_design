package com.dirase.hotelsys;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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
import static com.dirase.hotelsys.first.firurl;

public class admin_rateActivity extends AppCompatActivity {
    private List<String> mList1 = new ArrayList<>();
    private Context context = admin_rateActivity.this;
    private  List<String> mList2 = new ArrayList<>();
    private  List<String> mList3 = new ArrayList<>();
    private  List<String> mList4 = new ArrayList<>();
    private  List<String> mList5 = new ArrayList<>();
    private ListView listView;
    private Button back;
    final rate_adapter adapter = new rate_adapter(admin_rateActivity.this, mList1,mList2,mList3,mList4,mList5);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rate);
        this.listView = (ListView) findViewById(R.id.admin_rate_listview);
        listView.setAdapter(adapter);
        back = (Button)findViewById(R.id.admin_rate_back);
        back.setText("返回");
        adapter.setOnItemDeleteClickListener(new rate_adapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int i) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        });
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                    resultJson1(firurl + "findtips");
            }
        });
        thread.start();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    private ArrayList<HashMap<String, Object>> Analysis1(String jsonStr)
            throws JSONException {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("tips_text", jsonObject.getString("tips_text"));
            map.put("tips_hotel", jsonObject.getString("tips_hotel"));
            map.put("tips_num", jsonObject.getString("tips_num"));
            map.put("tips_room", jsonObject.getString("tips_room"));
            map.put("tips_star", jsonObject.getString("tips_star"));
            mList4.add(jsonObject.getString("tips_text"));
            mList1.add(jsonObject.getString("tips_hotel"));
            mList2.add(jsonObject.getString("tips_room"));
            mList5.add(jsonObject.getString("tips_num"));
            mList3.add(jsonObject.getString("tips_star"));
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
