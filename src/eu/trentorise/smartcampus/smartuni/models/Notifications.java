package eu.trentorise.smartcampus.smartuni.models;

import java.util.List;

public class Notifications {

	private List<String> listNotifications;
	
	public Notifications() {
		// TODO Auto-generated constructor stub
	}
	
	public void setListNotifications(List<String> listNotifications) {
		this.listNotifications = listNotifications;
	}
	
	
	public List<String> getListNotifications(){
		return listNotifications;
	}
}
