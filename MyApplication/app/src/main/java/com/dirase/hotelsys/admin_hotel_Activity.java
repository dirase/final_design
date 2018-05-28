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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.baidu.location.g.j.S;
import static com.baidu.location.g.j.f;
import static com.dirase.hotelsys.first.firurl;

public class admin_hotel_Activity extends AppCompatActivity {
    private EditText name,info,address,phone,sheng,city,views,star,jing,wei;
    private Button add,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_hotel_);
        name=(EditText)findViewById(R.id.admin_hotel_name);
        info=(EditText)findViewById(R.id.admin_hotel_info);
        address=(EditText)findViewById(R.id.admin_hotel_address);
        delete=(Button)findViewById(R.id.admin_hotel_delete);
        sheng = (EditText)findViewById(R.id.admin_hotel_sheng);
        city = (EditText)findViewById(R.id.admin_hotel_city);
        phone = (EditText)findViewById(R.id.admin_hotel_phone);
        views = (EditText)findViewById(R.id.admin_hotel_view);
        star = (EditText) findViewById(R.id.admin_hotel_star);
        add = (Button)findViewById(R.id.admin_hotel_add);
        jing = (EditText)findViewById(R.id.admin_hotel_jing);
        wei = (EditText)findViewById(R.id.admin_hotel_wei);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String name1 = name.getText().toString();
                            String info1 = info.getText().toString();
                            String address1= address.getText().toString();
                            String sheng1 = sheng.getText().toString();
                            String city1 = city.getText().toString();
                            String views1 = views.getText().toString();
                            String star1 = star.getText().toString();
                            String phone1 = phone.getText().toString();
                            if (name1==null){
                                name1 = "null";
                            }
                            if (info1==null){
                                name1 = "null";
                            }
                            if (address1==null){
                                name1 = "null";
                            }
                            if (sheng1==null){
                                name1 = "null";
                            }
                            if (city1==null){
                                name1 = "null";
                            }
                            if (views1==null){
                                name1 = "null";
                            }
                            if (star1==null){
                                name1 = "null";
                            }
                            if (phone1==null){
                                name1 = "null";
                            }
                            if("OK".equals(readParse(firurl+"addhotel/"+name1+"-"+info1+"-"+address1+"-"+phone1+"-"+sheng1+"-"+city1+"-"+views1+"-"+star1+"-"+jing.getText().toString()+"-"+wei.getText().toString()))){
                                Intent intent1 = new Intent(admin_hotel_Activity.this,admin_add_room_Activity.class);
                                intent1.putExtra("index",name.getText().toString());
                                startActivity(intent1);
                            }
                            else {
                                Looper.prepare();
                                Toast.makeText(admin_hotel_Activity.this,"error",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if("OK".equals(readParse(firurl+"deletehotel/"+name.getText().toString()))){
                                finish();
                            }
                            else {
                                Looper.prepare();
                                Toast.makeText(admin_hotel_Activity.this,"error",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        } catch (Exception e) {
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
        Log.e("json","readParse"+new String(outStream.toByteArray()));
        return new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
    }
}
