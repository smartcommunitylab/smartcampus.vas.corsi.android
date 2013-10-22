package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.Date;

public class AdptDetailedEvent {
	private Date date;
	private String name;
	private String description;
	private String ora;
	private String room; // prova

	public AdptDetailedEvent(Date date, String name, String description,
			String ora, String room) {
		super();
		this.date = date;
		this.name = name;
		this.description = description;
		this.ora = ora;
		this.room = room; // prova
	}

	public AdptDetailedEvent(Date date, String name, String description,
			String ora) {
		super();
		this.date = date;
		this.name = name;
		this.description = description;
		this.ora = ora;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOra() {
		return ora;
	}

	public void setOra(String ora) {
		this.ora = ora;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

}
