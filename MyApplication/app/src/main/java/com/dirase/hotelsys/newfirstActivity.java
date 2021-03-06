package com.dirase.hotelsys;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.dirase.hotelsys.R.id.btn;
import static com.dirase.hotelsys.R.id.btn2;
import static com.dirase.hotelsys.first.firurl;

public class newfirstActivity extends AppCompatActivity {
    private Spinner newfirst_stars,newfirst_hotel_max,newfirst_hotel_min;
    private TextView xingji;
    private int stars,min,max,leixing;
    private RadioGroup radioGroup;
    private RadioButton BGroupID;
    private RadioButton RGroupID;
    private String url;
    private Button confirm,btn1,btn2;
    private EditText sheng,city,first_view;
    private int year, month,day;
    private String date = "";
    private String date1 = "";
    public static String hotelurl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfirst);
        newfirst_stars = (Spinner)findViewById(R.id.newfirst_stars);
        newfirst_hotel_min = (Spinner)findViewById(R.id.newfirst_hotel_min);
        newfirst_hotel_max = (Spinner)findViewById(R.id.newfirst_hotel_max);
        xingji = (TextView)findViewById(R.id.xingji);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroupID);
        BGroupID=(RadioButton)findViewById(R.id.BGroupID);
        RGroupID=(RadioButton)findViewById(R.id.RGroupID);
        confirm = (Button)findViewById(R.id.newfirst_confirm);
        sheng = (EditText)findViewById(R.id.newfirst_sheng);
        city = (EditText)findViewById(R.id.newfirst_city);
        first_view = (EditText)findViewById(R.id.newfirst_view);
        btn1 = (Button)findViewById(R.id.newfirst_hotel_btn1);
        btn2 = (Button)findViewById(R.id.newfirst_hotel_btn2);
        leixing = 3;
        min = 0;
        max = 999999;
        stars = 0;
        year = 2000;
        month = 1;
        day = 1;
        //设置监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i == R.id.BGroupID) {
                    leixing = 1;
                    Toast.makeText(getApplicationContext(), "选择: 商务"+ "类型："+leixing,
                            Toast.LENGTH_SHORT).show();
                }else if (i == R.id.RGroupID){
                    leixing =2;
                    Toast.makeText(getApplicationContext(), "选择: 休闲"+ "类型："+leixing,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        newfirst_stars.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 将所选mySpinner 的值带入myTextView 中
                stars = arg2+1;
            }



            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        newfirst_hotel_min.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 将所选mySpinner 的值带入myTextView 中
                if(arg2==0){
                    min = 0;
                }
                if(arg2==1){
                    min = 200;
                }
                if(arg2==2){
                    min = 300;
                }
                if(arg2==3){
                    min = 400;
                }
                if(arg2==4){
                    min = 500;
                }
                if(arg2==5){
                    min = 1000;
                }
            }



            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        newfirst_hotel_max.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 将所选mySpinner 的值带入myTextView 中
                if(arg2==0){
                    max = 200;
                }
                if(arg2==1){
                    max = 300;
                }
                if(arg2==2){
                    max = 400;
                }
                if(arg2==3){
                    max = 500;
                }
                if(arg2==4){
                    max = 1000;
                }
                if(arg2==5){
                    max = 999999;
                }
            }



            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stars==0){
                    url = firurl+"gethotel2/"+min+"-"+max+"-"+sheng.getText().toString()+"-"+city.getText().toString()+"-"+leixing+"-"+first_view.getText().toString();
                    Intent mintent=new Intent(newfirstActivity.this,first.class);
                    hotelurl =url;
                    startActivity(mintent);
                }
                else {
                    url = firurl+"gethotel/"+min+"-"+max+"-"+sheng.getText().toString()+"-"+city.getText().toString()+"-"+stars+"-"+leixing+"-"+first_view.getText().toString();
                    Intent mintent=new Intent(newfirstActivity.this,first.class);
                    hotelurl =url;
                    startActivity(mintent);
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date();
                datePickerDialog();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date();
                datePickerDialog1();
            }
        });
    }

    private void datePickerDialog() {
        new DatePickerDialog(newfirstActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//                date = String.format("%d年%d月%d日", year, monthOfYear, dayOfMonth);
//                btn1.setText(date);
                String y = String.format("%d",year);
                String  m = String.format("%d",monthOfYear);
                String d = String.format("%d",dayOfMonth);
                int f = Integer.parseInt(m)+1;
                date = y+f+d;
                btn1.setText(y+"年"+f+"月"+d+"日");
            }
        }, year, month, day).show();
    }

    private void datePickerDialog1() {
        new DatePickerDialog(newfirstActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//                date1 = String.format("%d年%d月%d日", year, monthOfYear, dayOfMonth);
//                btn2.setText(date1);
                String y = String.format("%d",year);
                String  m = String.format("%d",monthOfYear);
                String d = String.format("%d",dayOfMonth);
                int f = Integer.parseInt(m)+1;
                date1 = y+f+d;
                btn2.setText(y+"年"+f+"月"+d+"日");
            }
        }, year, month, day).show();
    }
    private void date() {
        Calendar c = Calendar.getInstance();
        //年
        year = c.get(Calendar.YEAR);
        //月
        month = c.get(Calendar.MONTH);
        //日
        day = c.get(Calendar.DAY_OF_MONTH);
    }
}
