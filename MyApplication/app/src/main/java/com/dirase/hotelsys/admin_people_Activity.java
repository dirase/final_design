package com.dirase.hotelsys;

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

public class admin_people_Activity extends AppCompatActivity {
    private EditText user_name;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_people_);
        user_name = (EditText)findViewById(R.id.admin_people_name);
        delete = (Button)findViewById(R.id.admin_people_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if("OK".equals(readParse(firurl+"deletepeople/"+user_name.getText().toString()))){
                                finish();
                            }
                            else {
                                Looper.prepare();
                                Toast.makeText(admin_people_Activity.this,"error",Toast.LENGTH_SHORT).show();
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
