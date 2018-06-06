package com.dirase.hotelsys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.dirase.hotelsys.MainActivity.people_num;
import static com.dirase.hotelsys.first.firurl;

public class changepersonActivity extends AppCompatActivity {
	private EditText name,phone;
	private Button confirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changeperson);
		name = (EditText) findViewById(R.id.person_name);
		phone = (EditText)findViewById(R.id.person_phone);
		confirm = (Button)findViewById(R.id.person_confirm);
		confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							String o = readParse(firurl+"updateperson/"+name.getText().toString()+"-"+phone.getText().toString()+"-"+people_num);
							if (o.equals("OK")){
								finish();
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
