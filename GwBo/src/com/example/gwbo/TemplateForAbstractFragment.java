package com.example.gwbo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class TemplateForAbstractFragment extends Fragment {
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (localDebug()) debug("onAttach()");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (localDebug()) debug("onCreate()");
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (localDebug()) debug("onCreateView()");
		return inflater.inflate(getLayoutId(), container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (localDebug()) debug("onActivityCreated()");
	}

	@Override
	public void onStart() {
		super.onStart();
		if (localDebug()) debug("onStart()");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (localDebug()) debug("onResume()");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (localDebug()) debug("onPause()");
	}

	@Override
	public void onStop() {
		super.onStop();
		if (localDebug()) debug("onStop()");
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (localDebug()) debug("onDestroyView()");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (localDebug()) debug("onDestroy()");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		if (localDebug()) debug("onDetach()");
	}
	
	/**
	 * TODO fill
	 * @param text
	 */
	protected abstract void debug(String text);
	
	/**
	 * TODO fill
	 * @return
	 */
	protected abstract boolean localDebug();
	
	/**
	 * TODO fill
	 * @return
	 */
	protected abstract int getLayoutId();
}
