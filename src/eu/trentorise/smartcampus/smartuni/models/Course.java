package eu.trentorise.smartcampus.smartuni.models;

import java.util.Date;
import java.util.List;

public class Course {

	public int id; // id
	public String name; // name
	public Date date_start; // start date
	public Date date_end; // end date
	public String description; // description
	public List<Comment> comments; // list of comments

	public Course() {
		// TODO Auto-generated constructor stub
	}

	public List<Comment> getComments() {
		return comments;
	}

	public Date getDate_end() {
		return date_end;
	}

	public Date getDate_start() {
		return date_start;
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setDate_end(Date date_end) {
		this.date_end = date_end;
	}

	public void setDate_start(Date date_start) {
		this.date_start = date_start;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
