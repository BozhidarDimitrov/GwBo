package com.example.gwbo;

import java.text.NumberFormat;
import java.util.Locale;

import android.os.Bundle;
import android.widget.TextView;

import com.example.gwbo.system.AbstractActivity;

public class Locales extends AbstractActivity {
	
	private TextView mText;

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
		setContentView(R.layout.locales);
		
		mText = (TextView) findViewById(R.id.locales_text);
		mText.setText("");
		
		Locale[] locales = NumberFormat.getAvailableLocales();
		
		for (Locale l: locales) {
			write("Language: " + l.getLanguage());
			write("DisplayLanguage: " + l.getDisplayLanguage());
			write("Country: " + l.getCountry());
			write("DisplayCountry: " + l.getDisplayCountry());
			write("DisplayName: "  + l.getDisplayName());
			write("Variant: " + l.getVariant());
			write("DisplayVariant: " + l.getDisplayVariant());
			write("toString: " + l.toString());
			write("");
			write("");
		}
	}
	
	private void write(String text) {
		mText.setText(mText.getText() + text + "\n");
	}

}
