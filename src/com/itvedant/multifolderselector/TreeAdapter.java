package com.itvedant.multifolderselector;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TreeAdapter extends BaseExpandableListAdapter {

	private static Activity context;
    private static Folder root ;
    private static List<String> folders = null ;
    private Folder currentParent = null;
    final String[] okFileExtensions =  new String[] {"jpg", "png", "gif","jpeg"};
    FilenameFilter fl = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				for(String ext: okFileExtensions)
					if(filename.toLowerCase(Locale.ENGLISH).endsWith(ext))return true ;
				return false;
			}
	};
	public TreeAdapter(Activity context){
		this(context,null);
	}

    public TreeAdapter(Activity context,Folder currentParent) {
    	TreeAdapter.context = context ;
    	this.currentParent = currentParent ;
		if(folders == null){
			folders = new ArrayList<String>();
			root = new Folder();
			root.setName("root");
			root.setPath(Environment.getExternalStorageDirectory().getAbsolutePath());
			createList(new File(root.getPath()),0);
			this.currentParent = root ;
			Log.d("TAG",String.valueOf(folders.size()));
			for(String folder:folders){
				processFolder(folder);
			}
		}

	}

	private void createList(File root,int level) 
	{
	   level++;
	   File[] files = root.listFiles();
	   for(File f: files){
		   if(f.isHidden())continue ;
		   if(f.isDirectory()){
				   createList(f,level);
		   }
		   String[] fileNames =  f.list(fl) ;
		   if(fileNames != null && fileNames.length > 1){
			   if(folders != null){
				   folders.add(f.getAbsolutePath().replaceAll(Environment.getExternalStorageDirectory().getAbsolutePath(), ""));
			   }
		   }
	   }
	}
    
    
    private void processFolder(String folder) {
    	Folder parent = root ;
    	String[] folders = folder.substring(1).split("/");
    	for(int i=0;i<folders.length;i++)
    	{	
    		Folder child = parent.getFolderByName(folders[i]);
			if(child == null)
			{
				child = new Folder();
				child.setName(folders[i]); 
				parent.addFolder(child);
			}
			parent = child ;
    	}
	}
    
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return currentParent.folders.get(childPosition) ;
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.child_item, null);
//        }
        Folder childInQuestion = currentParent.folders.get(childPosition) ; // (Folder) getChild(groupPosition, childPosition);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
                R.layout.child_item,null);
        if(childInQuestion.folders.size() > 0){
	        ExpandableListView elv = new ExpandableListView(context);
	        elv.setAdapter(new TreeAdapter(context,childInQuestion)); 
	        layout.addView(elv);
        }else{
        	TextView tv = new TextView(context);
        	tv.setText(childInQuestion.getName());
        	layout.addView(tv);
        }
        return layout;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return currentParent.folders.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
//		String s = root.folders.get(groupPosition).getName();
		return currentParent.getName();
	}

	@Override
	public int getGroupCount() {
		return 1;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String folderName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.laptop);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(folderName);
        return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
