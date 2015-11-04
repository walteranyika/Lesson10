package com.walter.lesson10;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void save(View v) {
		EditText edt1 = (EditText) findViewById(R.id.editText1);
		EditText edt2 = (EditText) findViewById(R.id.editText2);
		EditText edt3 = (EditText) findViewById(R.id.editText3);
		String majina = edt1.getText().toString();
		String address = edt2.getText().toString();
		String age = edt3.getText().toString();
		// save the data to our remote db
		// emobilis-server.com/libs
		// packaging data
		RequestParams params = new RequestParams();
		params.put("names", majina);
		params.put("email", address);
		params.put("age", age);
		// send data
		// http://emobilis-server.com/lesson10/save.php
		// http://10.0.2.2/lesson10/save.php
		String url = "http://emobilis-server.com/lesson10/save.php";
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] arg1, byte[] data) {
				String response = new String(data);
				Toast.makeText(getApplicationContext(), response,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				Toast.makeText(getApplicationContext(),
						"Something bad happened", Toast.LENGTH_SHORT).show();

			}
		});
		// client.p

	}

	public void show(View s) {
		String url = "http://emobilis-server.com/lesson10/fetch.php";
		// http://10.0.2.2/lesson10/fetch.php
		// http://192.168.4.11/lesson10/fetch.php
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] data) {
				String response = new String(data);
				Gson gson = new Gson();
				try {
					JSONArray array = new JSONArray(response);
					int size = array.length();
					for (int i = 0; i < size; i++) {
						String item = array.getString(i);
						Person person = gson.fromJson(item, Person.class);
						Log.d("ITEM", person.getAge());
					}

				} catch (JSONException e) {
					Log.d("ERROR", e.getMessage());
					e.printStackTrace();
				}

				Log.d("JSON", response);

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				Toast.makeText(getApplicationContext(),
						"Something bad happened", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void json_btn(View v) {
		Person p = new Person();
		p.setId("1");
		p.setNames("John Mark");
		p.setAge("34");
		p.setEmail("jm@gmail.com");
		Gson gson = new Gson();
		String pJson = gson.toJson(p);
		Log.d("JSON", pJson);
	}
}
