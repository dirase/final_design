package com.dirase.hotelsys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class tips_detailsActivity extends AppCompatActivity {
    private Button hotel,people,psd,rate,tipsdetails_tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_details);
        hotel = (Button)findViewById(R.id.tipsdetails_hotel);
        people = (Button)findViewById(R.id.tipsdetails_people);
        psd = (Button)findViewById(R.id.tipsdetails_password);
        rate = (Button)findViewById(R.id.tipsdetails_rate);
        tipsdetails_tips= (Button)findViewById(R.id.tipsdetails_tips);
        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tips_detailsActivity.this,admin_hotel_Activity.class));
            }
        });
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tips_detailsActivity.this,admin_people_Activity.class));
            }
        });
        psd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tips_detailsActivity.this,admin_password_Activity.class));
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tips_detailsActivity.this,admin_rateActivity.class));
            }
        });
        tipsdetails_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tips_detailsActivity.this,admin_tipsActivity.class));
            }
        });
    }
}
