package com.dirase.hotelsys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class first extends AppCompatActivity {

    private ListView listView;
    private List<String> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        initList();

        this.listView = (ListView) findViewById(R.id.first_listview);

        final MyAdapter adapter = new MyAdapter(first.this, mList);
        listView.setAdapter(adapter);
        //ListView item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(first.this, "Click item" + i, Toast.LENGTH_SHORT).show();
            }
        });
        //ListView item 中的删除按钮的点击事件
        adapter.setOnItemDeleteClickListener(new MyAdapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int i) {
                mList.remove(i);
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void initList() {
        for (int i = 0; i < 20; i++) {
            mList.add(i + "");
        }
    }
}
