package eu.trentorise.smartcampus.android.studyMate.models;

import java.io.Serializable;

public class Evento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -132877965855146086L;
	private EventoId eventoId;
	private long cds;
	private int yearCds;
	private String title;
	private String room;
	private String teacher;
	private String type;
	private String personalDescription;
	private long adCod;
	private long gruppo;

	public EventoId getEventoId() {
		return eventoId;
	}

	public void setEventoId(EventoId eventoId) {
		this.eventoId = eventoId;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
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

	public String getPersonalDescription() {
		return personalDescription;
	}

	public void setPersonalDescription(String personalDescription) {
		this.personalDescription = personalDescription;
	}

	public long getAdCod() {
		return adCod;
	}

	public void setAdCod(long adCod) {
		this.adCod = adCod;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return super.equals(o);
	}

	public long getGruppo() {
		return gruppo;
	}

	public void setGruppo(long gruppo) {
		this.gruppo = gruppo;
	}

}