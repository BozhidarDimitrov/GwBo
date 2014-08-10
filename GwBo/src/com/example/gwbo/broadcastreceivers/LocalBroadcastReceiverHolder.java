package com.example.gwbo.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gwbo.R;
import com.example.gwbo.system.AbstractActivity;
import com.example.gwbo.system.LoggingManager;

public class LocalBroadcastReceiverHolder extends AbstractActivity implements 
		OnClickListener {
	
	private static final String MY_ACTION = "my_action";
	private static final String MESSAGE_TAG = "message_tag";
	
	private EditText mEt;
	private Button mButton;
	private TextView mTextView;
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			mTextView.setText(intent.getStringExtra(MESSAGE_TAG));
		}
	};
	
	/* DEBUGGING */
	@Override
	public boolean localDebug() {
		return LoggingManager.LOCAL_BROADCAST_RECEIVER_HOLDER;
	}

	@Override
	protected String getClassTag() {
		return LocalBroadcastReceiverHolder.class.getSimpleName();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.local_broadcast_receiver_holder);
		
		mEt = (EditText) findViewById(R.id.local_broadcast_receiver_holder_et);
		mButton = (Button) findViewById(R.id.local_broadcast_receiver_holder_button);
		mTextView = (TextView) findViewById(R.id.local_broadcast_receiver_holder_tv);
		
		mButton.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();	
		LocalBroadcastManager.getInstance(this).registerReceiver(
				mReceiver, 
				new IntentFilter(MY_ACTION));
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.local_broadcast_receiver_holder_button:
			
			//prepare intent
			Intent intent = new Intent(MY_ACTION);
			intent.putExtra(MESSAGE_TAG, mEt.getText().toString());
			
			LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
			break;
		}
	}
	
	
}
