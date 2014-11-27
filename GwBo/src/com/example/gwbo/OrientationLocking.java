package com.example.gwbo;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.gwbo.system.AbstractActivity;

public class OrientationLocking extends AbstractActivity implements OnClickListener {
	
	private Button mPort;
	private Button mLand;
	private Button mUnlock;

	@Override
	public boolean localDebug() {
		return false;
	}

	@Override
	protected String getClassTag() {
		return null;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orientation_locking);
		
		mPort = (Button) findViewById(R.id.orientation_locking_button_port);
		mLand = (Button) findViewById(R.id.orientation_locking_button_land);
		mUnlock = (Button) findViewById(R.id.orientation_locking_button_unlock);
		
		mPort.setOnClickListener(this);
		mLand.setOnClickListener(this);
		mUnlock.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.orientation_locking_button_port:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			break;
		case R.id.orientation_locking_button_land:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			break;
		case R.id.orientation_locking_button_unlock:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
			break;
		}
	}
	
}
