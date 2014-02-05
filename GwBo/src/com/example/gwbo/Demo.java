package com.example.gwbo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Demo extends Activity implements OnClickListener {
	
	private Button add;
	private Button sub;
	private TextView tv;
	private int counter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo);
		
		initialize();
	}

	private void initialize() {
		counter = 0;
		
		add = (Button) findViewById(R.id.demo_b_add);
		sub = (Button) findViewById(R.id.demo_b_sub);
		tv = (TextView) findViewById(R.id.demo_tv);
		
		add.setOnClickListener(this);
		sub.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.demo_b_add:
			counter++;
			tv.setText(counter + "");
			break;
		case R.id.demo_b_sub:
			counter--;
			tv.setText(counter + "");
			break;
		}
	}
	
}
