package com.example.gwbo;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InitialMenu extends ListActivity {
	
	String[] classes = {"Demo"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(InitialMenu.this, android.R.layout.simple_list_item_1, classes));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		String strClass = classes[position];

		try {
			Class clickedClass = Class.forName("com.example.gwbo." + strClass);

			Intent intent = new Intent(InitialMenu.this, clickedClass);
			startActivity(intent);
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}

}
