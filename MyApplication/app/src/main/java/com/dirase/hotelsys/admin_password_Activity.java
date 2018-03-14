package com.dirase.hotelsys;

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

import static com.dirase.hotelsys.R.id.first_password_edittext;
import static com.dirase.hotelsys.R.id.first_username_edittext;
import static com.dirase.hotelsys.first.firurl;

public class admin_password_Activity extends AppCompatActivity {
    private EditText psd_name,psd_old_password,psd_new_password1,psd_new_password2;
    private Button psd_conmfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_password_);
        psd_name = (EditText)findViewById(R.id.adminpassword_name);
        psd_old_password = (EditText)findViewById(R.id.adminpassword_old_password);
        psd_new_password1 = (EditText)findViewById(R.id.adminpassword_new_password1);
        psd_new_password2 = (EditText)findViewById(R.id.adminpassword_new_password2);
        psd_conmfirm = (Button)findViewById(R.id.adminpassword_confirm);
        psd_conmfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder stringBuilder = new StringBuilder(firurl);
                        stringBuilder.append("finduserinfo/");
                        stringBuilder.append(psd_name.getText().toString());
                        Log.e("string",stringBuilder.toString());
                        if(psd_old_password.getText().toString().equals(resultJson1(stringBuilder.toString()))){
                          if (psd_new_password1.getText().toString().equals(psd_new_password2.getText().toString())){
                              try {
                                  if("OK".equals(readParse(firurl+"changepsd/"+psd_name.getText().toString()+"-"+psd_new_password1.getText().toString()))){
                                      finish();
                                  }
                              } catch (Exception e) {
                                  e.printStackTrace();
                              }
                          }
                        }
                        else {
                            Looper.prepare();
                            Toast.makeText(admin_password_Activity.this,"please confirm password",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                });
                thread.start();
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
                Log.e("json","result:"+string);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("json","error1");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("json","error2");
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
