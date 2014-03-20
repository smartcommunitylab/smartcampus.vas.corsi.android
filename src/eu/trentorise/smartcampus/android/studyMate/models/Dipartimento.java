package eu.trentorise.smartcampus.android.studyMate.models;

import java.io.Serializable;

public class Dipartimento implements Serializable {

	private static final long serialVersionUID = 8681710690984301605L;

	private long facId;

	private String description;

	public long getId() {
		return facId;
	}

	public void setId(long id) {
		this.facId = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
