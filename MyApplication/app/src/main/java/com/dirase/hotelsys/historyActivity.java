package com.dirase.hotelsys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class historyActivity extends AppCompatActivity {
    private Button button1,button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        button1 = (Button)findViewById(R.id.history_button1);
        button3 = (Button)findViewById(R.id.history_button3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(historyActivity.this,first.class));
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(historyActivity.this,myActivity.class));
            }
        });
    }
}
