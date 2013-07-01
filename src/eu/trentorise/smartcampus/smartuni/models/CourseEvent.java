package eu.trentorise.smartcampus.smartuni.models;

import java.util.Date;


public class CourseEvent {
	private Date date;
	private String name;
	
	
	public CourseEvent(Date date, String name) {
		super();
		this.date = date;
		this.name = name;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
