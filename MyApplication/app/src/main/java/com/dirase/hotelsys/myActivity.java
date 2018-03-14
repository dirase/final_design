package com.dirase.hotelsys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class myActivity extends AppCompatActivity {
    Button button1,button2,changepsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        button1 = (Button)findViewById(R.id.my_button1);
        button2 = (Button)findViewById(R.id.my_button2);
        changepsd = (Button)findViewById(R.id.my_change_password);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(myActivity.this,first.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(myActivity.this,historyActivity.class));
            }
        });
        changepsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(myActivity.this,admin_password_Activity.class));
            }
        });
    }
}
