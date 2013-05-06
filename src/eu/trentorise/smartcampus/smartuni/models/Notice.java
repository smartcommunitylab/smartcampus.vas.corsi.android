package eu.trentorise.smartcampus.smartuni.models;

public class Notice {

	public String description;
	public String datetime;
	public String user;
	
	
	public Notice() {
		// TODO Auto-generated constructor stub
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
