package com.itvedant.multifolderselector;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
public class MainActivity extends Activity {
    ExpandableListView expListView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		     
        expListView = (ExpandableListView) findViewById(R.id.expandableListView1);
        expListView.setAdapter(new TreeAdapter(this));
	}

}
