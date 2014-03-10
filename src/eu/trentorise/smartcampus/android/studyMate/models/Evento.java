package eu.trentorise.smartcampus.android.studyMate.models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Evento extends BasicEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4216708970101566299L;


	private long id;
	private long cds;
	private int yearCds;
	private String title;
	private String room;
	private String teacher;
	private Time start;
	private Time stop;
	private String type;
	private Date date;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Time getStart() {
		return start;
	}

	public void setStart(Time start) {
		this.start = start;
	}

	public Time getStop() {
		return stop;
	}

	public void setStop(Time stop) {
		this.stop = stop;
	}

	public long getCds() {
		return cds;
	}

	public void setCds(long cds) {
		this.cds = cds;
	}

	public int getYearCds() {
		return yearCds;
	}

	public void setYearCds(int yearCds) {
		this.yearCds = yearCds;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	
}