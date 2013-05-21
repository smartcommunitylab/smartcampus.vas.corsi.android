package eu.trentorise.smartcampus.smartuni.models;

import java.util.ArrayList;

public class Notifications {

	private ArrayList<Notice> listNotifications;
	
	public Notifications() {
		// TODO Auto-generated constructor stub
	}
	
	public void setListNotifications(ArrayList<Notice> listNotifications) {
		this.listNotifications = listNotifications;
	}
	
	
	public ArrayList<Notice> getListNotifications(){
		if(listNotifications.size()!=0)
			return listNotifications;
		else
			return null;
	}
}
