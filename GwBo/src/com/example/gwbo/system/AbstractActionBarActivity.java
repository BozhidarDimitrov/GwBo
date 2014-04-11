package com.example.gwbo.system;

import com.example.gwbo.system.LoggingManager.Debuggable;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public abstract class AbstractActionBarActivity extends ActionBarActivity implements Debuggable {
	
	/* DEBUGGING */
	private final String log_intro = 
			"..." + this.toString().substring(this.toString().length() - 5) + " ";
	
	@Override
	public void debug(String message) {
		if (LoggingManager.GLOBAL_DEBUG && localDebug()) {
			Log.d(getClassTag(), log_intro + message);
		}
	}
	
	protected abstract String getClassTag();
	
	/* LIFECYCLE */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		debug("onCreate()");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		debug("onRestart()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		debug("onStart()");
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		debug("onRestoreInstanceState()");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		debug("onResume()");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		debug("onPause()");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		debug("onSaveInstanceState()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		debug("onStop()");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		debug("onDestroy()");
	}
}
