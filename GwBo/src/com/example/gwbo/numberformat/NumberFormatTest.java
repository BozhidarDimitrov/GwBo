package com.example.gwbo.numberformat;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.gwbo.R;
import com.example.gwbo.system.AbstractActivity;
import com.example.gwbo.system.LoggingManager;

public class NumberFormatTest extends AbstractActivity implements OnClickListener {

	private EditText mEt1;
	private Button mButton;
	private EditText mEt2;
	
	@Override
	public boolean localDebug() {
		return LoggingManager.NUMBER_FORMAT_TEST;
	}

	@Override
	protected String getClassTag() {
		return NumberFormatTest.class.getSimpleName();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.number_format_test);
		
		initialize();
		
	}

	private void initialize() {
		mEt1 = (EditText) findViewById(R.id.number_format_test_et1);
		mButton = (Button) findViewById(R.id.number_format_test_button);
		mEt2 = (EditText) findViewById(R.id.number_format_test_et2);
		
		mButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.number_format_test_button:
			
			/*
			String text = mEt1.getText().toString();
			
			mEt2.setText(calculate(text));
			*/
			
			double a = -1234.5;
			debug("a = " + a);
			String string = doubleToString(a);
			debug("string = " + string);
			
			int cut = 4;
			String leftTerm = string.substring(0, cut);
			debug("leftTerm = " + leftTerm);
			String rightTerm = string.substring(cut);
			debug("rightTerm = " + rightTerm);
			String insert = "9";
			
			debug(getKS(calculate(leftTerm + insert + rightTerm)));
			
			debug("fail try: ");
			debug(getKS("23m4"));
			
			break;
		}
	}

	private String calculate(String text) {
		return text.replace(new DecimalFormatSymbols().getGroupingSeparator() + "", "");
	}
	
	private final String getKS(String term) {
		debug(" --- getKS() ---");
		NumberFormat formatter = NumberFormat.getInstance();
		
		ParsePosition pos = new ParsePosition(0);
		
		//Object object = formatter.parse(term, pos);
		
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		/*
		debug("multiplier = " + df.getMultiplier());
		debug("grouping size = " + df.getGroupingSize());
		debug("negative prefix = " + df.getNegativePrefix());
		debug("negative suffix = " + df.getNegativeSuffix());
		debug("positive prefix = " + df.getPositivePrefix());
		debug("positive suffix = " + df.getPositiveSuffix());
		debug("grouping separator = " + df.getDecimalFormatSymbols().getGroupingSeparator());
		*/
		
		Parser parser = new Parser(df.getDecimalFormatSymbols());
		//Object object = parser.parse(term, pos);
		
		//df = new DecimalFormat();
		Object object = df.parse(term, pos);
		
		// if term length == 0 it parses it, so term.length() > 0
		if ((term.length() == pos.getIndex()) && (term.length() > 0)) {
			double value;
			if (object instanceof Long) {
				value = ((Long) object).doubleValue();
			} else {
				value = (Double) object;
			}
			//the number needs to be correctly formatted
			// 6,7 correct, 6.7 not
			debug("ok");
			debug("pos = " + pos.getIndex());
			debug(" --- end ---");
			return formatter.format(value);
		} else {
			debug("fail");
			debug("pos = " + pos.getIndex());
			debug("error pos = " + pos.getErrorIndex());
			debug(" --- end ---");
			return null;
		}
	}
	
	public static String doubleToString(double number) {
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMaximumFractionDigits(3);
		return formatter.format(number);
	}
}
