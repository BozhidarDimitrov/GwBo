package com.example.gwbo;

import com.example.gwbo.system.AbstractFragmentAcitivty;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditTextTexts extends AbstractFragmentAcitivty implements 
		OnClickListener{
	
	private EditText et;
	private TextView tv;
	private Button b;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM, 
		//		WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		
		setContentView(R.layout.edit_text_tests);
		
		et = (EditText) findViewById(R.id.edit_text_tests_et);
		tv = (TextView) findViewById(R.id.edit_text_tests_tv);
		b = (Button) findViewById(R.id.edit_text_test_button);
		
		et.setRawInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		b.setOnClickListener(this);
		
	}

	@Override
	public boolean localDebug() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getClassTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onClick(View v) {
		//et.setText(strip(et.getText().toString()));
		et.getText().replace(0, et.getText().length(), strip(et.getText().toString()));
	}
	
	private String strip(String text) {
		text = text.replace(" ", "");
		return text;
	}
}
