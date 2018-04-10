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

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.dirase.hotelsys.first.firurl;
import static java.net.Proxy.Type.HTTP;

public class signupactivity extends AppCompatActivity {
    private EditText signup_username,signup_password1,signup_password2,signup_phone;
    private Button signup_back,signup_register;
    public String signupurl = firurl+"signup/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);
        signup_back = (Button)findViewById(R.id.sign_up_back);
        signup_register = (Button)findViewById(R.id.sign_up_confirm);
        signup_username = (EditText)findViewById(R.id.sign_up_name);
        signup_password1 = (EditText)findViewById(R.id.sign_up_password1);
        signup_password2 = (EditText)findViewById(R.id.sign_up_password2);
        signup_phone = (EditText)findViewById(R.id.sign_up_phonenum);
        signup_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(signup_username.length()==0){
                    Toast.makeText(signupactivity.this,"username is null",Toast.LENGTH_SHORT).show();
                }
                else if(signup_password1.length()==0){
                    Toast.makeText(signupactivity.this,"password is null",Toast.LENGTH_SHORT).show();
                }
                else if(signup_phone.length()==0){
                    Toast.makeText(signupactivity.this,"phone is null",Toast.LENGTH_SHORT).show();
                }
                else if(signup_password1.getText().toString().equals(signup_password2.getText().toString())){
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            StringBuilder stringBuilder = new StringBuilder(signupurl);
                            stringBuilder.append(signup_username.getText().toString());
                            Log.e("string",signup_username.getText().toString());
                            stringBuilder.append("-");
                            stringBuilder.append(signup_password1.getText().toString());
                            stringBuilder.append("-");
                            stringBuilder.append(signup_phone.getText().toString());
                            try {
                                if("OK".equals(readParse(stringBuilder.toString()))){
                                    startActivity(new Intent(signupactivity.this,first.class));
                                    finish();
                                }
                            } catch (Exception e) {
                                Looper.prepare();
                                Toast.makeText(signupactivity.this,"username is used",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }
                else {
                    Toast.makeText(signupactivity.this,"please confirm password",Toast.LENGTH_SHORT).show();
                }
            }
        });
        signup_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        Log.e("json",new String(outStream.toByteArray()));
        return new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
    }

}
