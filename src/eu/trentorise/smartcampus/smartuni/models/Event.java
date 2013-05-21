package eu.trentorise.smartcampus.smartuni.models;

import java.util.Date;
import java.util.List;

public class Event {

	public int id; // id
	public String name; // name
	public String where; // where, room, etc
	public Date date_start; // start date
	public Date date_end; // end date
	public boolean repeat; // repeat
	public CourseLite course; // course related
	public List<String> note; // list of notes

	public Event() {
		// TODO Auto-generated constructor stub
	}

	public CourseLite getCourse() {
		return course;
	}

	public Date getDate_end() {
		return date_end;
	}

	public Date getDate_start() {
		return date_start;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<String> getNote() {
		return note;
	}

	public String getWhere() {
		return where;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setCourse(CourseLite course) {
		this.course = course;
	}

	public void setDate_end(Date date_end) {
		this.date_end = date_end;
	}

	public void setDate_start(Date date_start) {
		this.date_start = date_start;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNote(List<String> note) {
		this.note = note;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public void setWhere(String where) {
		this.where = where;
	}

}
