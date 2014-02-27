package com.example.gwbo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Template for a new Activity
 * 
 * @author Bozhidar
 *
 */
public class TemplateForActivity extends Activity{
	
	/* DEBUGGING */
	public static final boolean GLOBAL_DEBUG = true;
	private static final boolean LOCAL_DEBUG = true;
	private static final String TAG = TemplateForActivity.class.getSimpleName();
	private final String debug_intro = 
			"..." + this.toString().substring(this.toString().length() - 5) + " ";
	
	private void debug(String text){
		if (GLOBAL_DEBUG && LOCAL_DEBUG) Log.d(TAG, debug_intro + text);
	}
	
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
