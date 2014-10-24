package com.itvedant.multifolderselector;

import java.util.ArrayList;
import java.util.List;

public class Folder {
	private String name ;
	private String path ;
	List<Folder> folders = null ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public boolean folderExist(String newFolder) {
		if(this.folders != null && this.folders.size() > 0){
			for(Folder folder: this.folders){
				if(folder.getName() == newFolder)
					return true ;
			}
		}
		return false;
	}
	
	public void addFolder(Folder childDir) {
		folders.add(childDir);
	}
	
	public Folder getFolderByName(String dirName) {
		for(Folder folder:folders){
			if(folder.getName().equalsIgnoreCase(dirName))
				return folder ;
		}
		return null;
	}
	
	public Folder() {
		folders = new ArrayList<Folder>();
	}
}
