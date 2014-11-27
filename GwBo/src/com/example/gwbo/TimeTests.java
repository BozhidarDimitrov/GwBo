package com.example.gwbo;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gwbo.system.AbstractActivity;

public class TimeTests extends AbstractActivity implements OnClickListener{
	
	private TextView mTextView;
	private Button mButton;
	private Calendar mCal;

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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_tests);
		
		mTextView = (TextView) findViewById(R.id.time_tests_tv);
		mButton = (Button) findViewById(R.id.time_tests_button);
		mButton.setOnClickListener(this);
		
		mCal = Calendar.getInstance();
	}

	@Override
	public void onClick(View arg0) {
		mCal.add(Calendar.MILLISECOND, 2000);
		mTextView.setText(mCal.get(Calendar.DATE) + " " + mCal.get(Calendar.HOUR) + " " + mCal.get(Calendar.MINUTE) + " " + mCal.get(Calendar.SECOND));
	}
}
