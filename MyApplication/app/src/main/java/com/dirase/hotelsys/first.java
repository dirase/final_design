package com.dirase.hotelsys;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import static android.R.attr.bitmap;
import static android.R.attr.id;
import static java.net.Proxy.Type.HTTP;

public class first extends AppCompatActivity {

    private ListView listView;
    private Button first_button2,first_button3,first_search;
    public static String firurl = "http://192.168.0.110:8080/";
    public  String url = firurl+"findHotelInfo/";
    public  String url1 = firurl+"findhotelnum/1";
    private EditText first_edittext;
    private  List<String> mList1 = new ArrayList<>();
    private Context context = first.this;
    private  List<String> mList2 = new ArrayList<>();
    private  List<String> mList3 = new ArrayList<>();
    private  List<String> mList4 = new ArrayList<>();
    private  List<String> mList5 = new ArrayList<>();
    private  List<String> mList6 = new ArrayList<>();
    private  List<String> mList7 = new ArrayList<>();
    private String newurl ="";
    private int hotel_num,hotel_room_num;
    public String firtext;
    final MyAdapter adapter = new MyAdapter(first.this, mList1,mList2,mList3,mList4,mList5,mList6,mList7);
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        first_button2 = (Button)findViewById(R.id.first_button2);
        first_button3 = (Button)findViewById(R.id.first_button3);
        first_search = (Button)findViewById(R.id.first_search);
        first_edittext = (EditText)findViewById(R.id.first_edittext);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        newurl = bundle.getString("index");
        Log.e("newurl",newurl);
        //initList();
//        final Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                hotel_num = resultJson1(url1);
//                for(int i = 0;i<hotel_num;i++){
//                    StringBuilder stringBuilder = new StringBuilder(url);
//                    stringBuilder.append(i);
//                    hotel_room_num = resultJson(stringBuilder.toString());
//                }
//            }
//        });
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                resultJson4(newurl);
            }
        });
        thread.start();
        this.listView = (ListView) findViewById(R.id.first_listview);
        listView.setAdapter(adapter);
        //ListView item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(first.this,hotel_Activity.class));
                Toast.makeText(first.this, "Click item" + i, Toast.LENGTH_SHORT).show();
            }
        });
        //ListView item 中的删除按钮的点击事件
        adapter.setOnItemDeleteClickListener(new MyAdapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int i) {
                startActivity(new Intent(first.this,hotel_Activity.class));
                adapter.notifyDataSetChanged();
            }
        });
        first_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(first.this,historyActivity.class));
            }
        });
        first_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(first.this,myActivity.class));
            }
        });
        first_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(first.this,hotel_Activity.class);
                        int i = resultJson2(firurl+"findhotelbyname/"+first_edittext.getText().toString());
                        if(i<=hotel_num&&i>0){
                            intent.putExtra("index",""+i);
                            Looper.prepare();
                            startActivity(intent);
                            Looper.loop();
                        }
                        else {
                            Toast.makeText(first.this,"null",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                thread1.start();
            }
        });
    }


    private  ArrayList<HashMap<String, Object>> Analysis(String jsonStr)
            throws JSONException {
        /******************* 解析 ***********************/
        JSONArray jsonArray = null;
        // 初始化list数组对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
//        jsonArray = new JSONArray(jsonStr);
        JSONObject jsonObject = new JSONObject(jsonStr);
        //JSONArray jResult = jsonObject.getJSONArray("");
        //for (int i = 0; i < jResult.length(); i++) {
            //JSONObject jsonObject = new JSONObject(jsonStr);
            // 初始化map数组对象
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("hotel_adress", jsonObject.getString("hotel_adress"));
            map.put("name", jsonObject.getString("name"));
            map.put("hotel_phone", jsonObject.getString("hotel_phone"));
            map.put("hotel_room_num", jsonObject.getInt("hotel_room_num"));
            map.put("num", jsonObject.getString("num"));
        mList1.add(jsonObject.getString("hotel_adress"));
        mList2.add(jsonObject.getString("name"));
        mList3.add(jsonObject.getString("hotel_phone"));
        adapter.notifyDataSetChanged();
//            map.put("xianJia", jsonObject.getString("xianJia"));
//            map.put("id", jsonObject.getInt("id"));

            list.add(map);
        //}
        return list;
    }

    private  ArrayList<HashMap<String, Object>> Analysis2(String jsonStr)
            throws JSONException {
        /******************* 解析 ***********************/
        JSONArray jsonArray = null;
        // 初始化list数组对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
//        jsonArray = new JSONArray(jsonStr);
        JSONObject jsonObject = new JSONObject(jsonStr);
        //JSONArray jResult = jsonObject.getJSONArray("");
        //for (int i = 0; i < jResult.length(); i++) {
        //JSONObject jsonObject = new JSONObject(jsonStr);
        // 初始化map数组对象
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("hotel_adress", jsonObject.getString("hotel_adress"));
        map.put("name", jsonObject.getString("name"));
        map.put("hotel_phone", jsonObject.getString("hotel_phone"));
        map.put("hotel_room_num", jsonObject.getInt("hotel_room_num"));
        map.put("num", jsonObject.getInt("num"));
        list.add(map);
        //}
        return list;
    }

    private static ArrayList<HashMap<String, Object>> Analysis1(String jsonStr)
            throws JSONException {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONObject jsonObject = new JSONObject(jsonStr);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("hotel_num_num", jsonObject.getInt("hotel_num_num"));
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
        Log.e("json",new String(outStream.toByteArray()));
        return new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
    }

    private int  resultJson(String url) {
        int string = 0;
        try {
            Iterator<HashMap<String, Object>> it=Analysis(readParse(url)).iterator();
            while (it.hasNext()) {
                Map<String, Object> ma = it.next();
                string = (int) ma.get("hotel_room_num");
                Log.e("json","result:"+string);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("json","error1");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("json","error2");
            resultJson(url);
        }
        Log.e("json","hotel_room_num result:"+string);
        return string;
    }

    private int  resultJson2(String url) {
        int string = 0;
        try {
            Iterator<HashMap<String, Object>> it=Analysis2(readParse(url)).iterator();
            while (it.hasNext()) {
                Map<String, Object> ma = it.next();
                string = (int) ma.get("num");
                Log.e("json","result:"+string);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("json","error1");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("json","error2");
            resultJson2(url);
        }
        Log.e("json","num result:"+string);
        return string;
    }

    private int  resultJson1(String url) {
        int string = 0;
        try {
            Iterator<HashMap<String, Object>> it=Analysis1(readParse(url)).iterator();
            while (it.hasNext()) {
                Map<String, Object> ma = it.next();
                string = (int) ma.get("hotel_num_num");
                Log.e("json","result:"+string);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("json","error1");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("json","error2");
            resultJson1(url);
        }
        Log.e("json","result:"+string);
        return string;
    }

    private void  resultJson4(String url) {
        try {
            Iterator<HashMap<String, Object>> it=Analysis4(readParse(url)).iterator();
            while (it.hasNext()) {
                Map<String, Object> ma = it.next();
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

    private  ArrayList<HashMap<String, Object>> Analysis4(String jsonStr)
            throws JSONException {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("hotel_adress", jsonObject.getString("hotel_adress"));
            map.put("name", jsonObject.getString("name"));
            map.put("hotel_phone", jsonObject.getString("hotel_phone"));
            map.put("hotel_room_num", jsonObject.getInt("hotel_room_num"));
            map.put("num", jsonObject.getInt("num"));
            mList1.add(jsonObject.getString("hotel_adress"));
            mList2.add(jsonObject.getString("name"));
            mList3.add(jsonObject.getString("hotel_phone"));
            mList4.add(jsonObject.getString("hotel_stars"));
            mList5.add(jsonObject.getString("hotel_leixing"));
            mList6.add(jsonObject.getString("hotel_feature"));
            mList7.add(jsonObject.getString("num"));
            list.add(map);
        }
        adapter.notifyDataSetChanged();
        return list;
    }

}
