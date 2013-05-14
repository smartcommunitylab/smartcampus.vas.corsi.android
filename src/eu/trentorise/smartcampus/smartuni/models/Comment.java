package eu.trentorise.smartcampus.smartuni.models;

import java.util.Date;

public class Comment {
	public int id;
	public String text; // text of the comment
	public Date date; // creation date
	public UserCourse autore; // author

	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public UserCourse getAutore() {
		return autore;
	}

	public Date getDate() {
		return date;
	}

	public int getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public void setAutore(UserCourse autore) {
		this.autore = autore;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

}
