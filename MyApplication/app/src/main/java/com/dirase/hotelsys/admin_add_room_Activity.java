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

import static com.dirase.hotelsys.first.firurl;

public class admin_add_room_Activity extends AppCompatActivity {
    private Button back,add;
    private EditText room_name,room_level,room_info,things;
    private String i ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_room_);
        back = (Button)findViewById(R.id.admin_add_room_back);
        add = (Button)findViewById(R.id.admin_room_add);
        room_name = (EditText)findViewById(R.id.admin_room_name);
        room_level = (EditText)findViewById(R.id.admin_room_level);
        room_info = (EditText)findViewById(R.id.admin_add_room_info);
        things = (EditText)findViewById(R.id.admin_add_room_things);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        i = bundle.getString("index");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if("OK".equals(readParse(firurl+"addroom/"+room_name.getText().toString()+"-"+room_level.getText().toString()+"-"+room_info.getText().toString()+"-"+i+"-"+things.getText().toString()))){
                                Looper.prepare();
                                Toast.makeText(admin_add_room_Activity.this,"success",Toast.LENGTH_SHORT).show();
                                room_name.setText("");
                                room_level.setText("");
                                room_info.setText("");
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
