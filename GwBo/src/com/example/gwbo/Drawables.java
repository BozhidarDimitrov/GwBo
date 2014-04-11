package com.example.gwbo;

import android.os.Bundle;

import com.example.gwbo.system.AbstractFragmentAcitivty;
import com.example.gwbo.system.LoggingManager;

public class Drawables extends AbstractFragmentAcitivty{

	/* DEBUGGING */
	@Override
	public boolean localDebug() {
		return LoggingManager.DRAWABLES;
	}

	@Override
	protected String getClassTag() {
		return Drawables.class.getSimpleName();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawables);
	}

	
}
