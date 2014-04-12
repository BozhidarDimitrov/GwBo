package com.example.gwbo;

import android.os.Bundle;

import com.example.gwbo.system.AbstractFragmentAcitivty;
import com.example.gwbo.system.LoggingManager;

public class VolumeBooster extends AbstractFragmentAcitivty {

	
	@Override
	public boolean localDebug() {
		return LoggingManager.VOLUME_BOOSTER;
	}

	@Override
	protected String getClassTag() {
		return VolumeBooster.class.getSimpleName();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.volume_booster);
	}
}
