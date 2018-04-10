package com.dirase.hotelsys;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.dirase.hotelsys.first.firurl;

public class newfirstActivity extends AppCompatActivity {
    private Spinner newfirst_stars,newfirst_hotel_max,newfirst_hotel_min;
    private TextView xingji;
    private int stars,min,max,leixing;
    private RadioGroup radioGroup;
    private RadioButton BGroupID;
    private RadioButton RGroupID;
    private String url;
    private Button confirm;
    private EditText sheng,city;

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
        leixing = 3;
        min = 0;
        max = 999999;
        stars = 0;
        //设置监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i == R.id.BGroupID) {
                    leixing = 1;
                    Toast.makeText(getApplicationContext(), "choice: 商务"+ "类型："+leixing,
                            Toast.LENGTH_SHORT).show();
                }else if (i == R.id.RGroupID){
                    leixing =2;
                    Toast.makeText(getApplicationContext(), "choice: 休闲"+ "类型："+leixing,
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
                    url = firurl+"gethotel2/"+min+"-"+max+"-"+sheng.getText().toString()+"-"+city.getText().toString()+"-"+leixing;
                    Intent mintent=new Intent(newfirstActivity.this,first.class);
                    mintent.putExtra("index",url);
                    startActivity(mintent);
                }
                else {
                    url = firurl+"gethotel2"+min+"-"+max+"-"+sheng.getText().toString()+"-"+city.getText().toString()+"-"+stars+"-"+leixing;
                    Intent mintent=new Intent(newfirstActivity.this,first.class);
                    mintent.putExtra("index",url);
                    startActivity(mintent);
                }
            }
        });
    }
}
