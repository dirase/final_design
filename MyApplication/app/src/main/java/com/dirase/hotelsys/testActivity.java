package com.dirase.hotelsys;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.dirase.hotelsys.MainActivity.people_num;
import static com.dirase.hotelsys.first.firurl;

public class testActivity extends AppCompatActivity {
    private Button btn,btn2,confirm;
    private int year, month, day,hour,minute;
    private String i = "";
    private String date2,date1,hotel;
    private EditText phone,personname,info;
    private String ratetime = "";
    private String hotel_num ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        btn = (Button) findViewById(R.id.btn);
        btn2=(Button)findViewById(R.id.btn2);
        confirm = (Button)findViewById(R.id.btn_confirm);
        phone = (EditText)findViewById(R.id.test_phone);
        personname = (EditText)findViewById(R.id.test_person);
        info = (EditText)findViewById(R.id.test_info);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String i = bundle.getString("index");
        hotel = bundle.getString("hotel");
        hotel_num = bundle.getString("num");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date();
                datePickerDialog1();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date();
                datePickerDialog();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if("OK".equals(readParse(firurl+"addtips/"+date1+"-"+date2+"-"+hotel_num+"-"+people_num+"-"+phone.getText().toString()+"-"+year+"-"+month+"-"+day+"-"+hour+"-"+minute+"-"+personname.getText().toString()+"-"+info.getText().toString()+"-"+i))){
                                finish();
                            }
                            else {
                                Looper.prepare();
                                Toast.makeText(testActivity.this,"error",Toast.LENGTH_SHORT).show();
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

    private void datePickerDialog() {
        new DatePickerDialog(testActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//                 date2 = String.format("%d%d%d", year, monthOfYear, dayOfMonth);
//                btn.setText(date2);
                String y = String.format("%d",year);
                String  m = String.format("%d",monthOfYear);
                String d = String.format("%d",dayOfMonth);
                int f = Integer.parseInt(m)+1;
                date2 = y+f+d;
                btn.setText(y+"年"+f+"月"+d+"日");
            }
        }, year, month, day).show();
    }

    private void datePickerDialog1() {
        new DatePickerDialog(testActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String y = String.format("%d",year);
                String  m = String.format("%d",monthOfYear);
                String d = String.format("%d",dayOfMonth);
                int f = Integer.parseInt(m)+1;
                //date1 = String.format("%d%d%d", year, monthOfYear, dayOfMonth);
                date1 = y+f+d;
                btn2.setText(y+"年"+f+"月"+d+"日");
            }
        }, year, month, day).show();
    }

    //获取当前系统时间
    private void date() {
        Calendar c = Calendar.getInstance();
        //年
        year = c.get(Calendar.YEAR);
        //月
        month = c.get(Calendar.MONTH);
        //日
        day = c.get(Calendar.DAY_OF_MONTH);
        //小时
        hour = c.get(Calendar.HOUR_OF_DAY);
//分钟
        minute = c.get(Calendar.MINUTE);

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
