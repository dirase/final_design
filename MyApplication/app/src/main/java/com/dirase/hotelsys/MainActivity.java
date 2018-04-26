package com.dirase.hotelsys;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Map;

import static com.dirase.hotelsys.first.firurl;
import static com.dirase.hotelsys.first.readParse;

public class MainActivity extends AppCompatActivity {
    private Button login,sign_up;
    public static String people_num;
    public static int ad_hul_num;
    private EditText first_password_edittext,first_username_edittext;
    private EditText user_name,user_password;
    public  String urlfirst = firurl+"finduserinfo/";
    private int level =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         login = (Button)findViewById(R.id.first_login);
        sign_up = (Button)findViewById(R.id.first_signup);
        first_password_edittext = (EditText)findViewById(R.id.first_password_edittext);
        first_username_edittext = (EditText)findViewById(R.id.first_username_edittext);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder stringBuilder = new StringBuilder(urlfirst);
                        stringBuilder.append(first_username_edittext.getText().toString());
                        Log.e("string",stringBuilder.toString());
                        if(first_password_edittext.getText().toString().equals(resultJson1(stringBuilder.toString()))){
                            if(level==0){
                                Intent intent = new Intent(MainActivity.this,newfirstActivity.class);
                                intent.putExtra("people_num",""+people_num);
                                startActivity(intent);
                                finish();
                            }
                            else if(level==1){
                                startActivity(new Intent(MainActivity.this,tips_detailsActivity.class));
                                finish();
                            }
                            else if(level==2){
                                Intent intent = new Intent(MainActivity.this,hoteladminActivity.class);
                                intent.putExtra("people_num",""+people_num);
                                intent.putExtra("num",""+ad_hul_num);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else {
                            Looper.prepare();
                            Toast.makeText(MainActivity.this,"Wrong password or username",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                });
                thread.start();

            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this,signupactivity.class));
                startActivity(new Intent(MainActivity.this,mapActivity.class));
               // finish();
            }
        });
    }

    private String  resultJson1(String url) {
        String string = "0";
        try {
            Iterator<HashMap<String, Object>> it=Analysis1(readParse(url)).iterator();
            while (it.hasNext()) {
                Map<String, Object> ma = it.next();
                string = (String) ma.get("people_password");
                people_num = (String) ma.get("people_num");
                level = (int)ma.get("people_level");
                if(level==2){
                    ad_hul_num = (int)ma.get("people_hotel");
                }
                Log.e("json","result:"+string);
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
        map.put("people_password", jsonObject.getString("people_password"));
        map.put("people_num", jsonObject.getString("people_num"));
        map.put("people_level", jsonObject.getInt("people_level"));
        if((int)map.get("people_level")==2){
            ad_hul_num = (int)map.get("people_hotel");
            map.put("people_hotel", jsonObject.getInt("people_hotel"));
        }
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
