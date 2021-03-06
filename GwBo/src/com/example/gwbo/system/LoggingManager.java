package com.example.gwbo.system;

public class LoggingManager {
	
	public interface Debuggable {
		public void debug(String message);
		public boolean localDebug();
	}
	
	public static final boolean GLOBAL_DEBUG = true;
	
	public static final boolean DEMO = false;
	public static final boolean DRAWABLES = false;
	public static final boolean VOLUME_BOOSTER = false;
	public static final boolean NUMBER_FORMAT_TEST = false;
	public static final boolean LOCAL_BROADCAST_RECEIVER_HOLDER = true;
}