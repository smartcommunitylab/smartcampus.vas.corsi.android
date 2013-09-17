package eu.trentorise.smartcampus.studyMate.models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Evento implements Serializable {

	public Evento(long id, Corso corso, String titolo, String event_location,
			String room, Date data, String descrizione, Time start, Time stop,
			boolean all_day, boolean availability) {
		super();
		this.id = id;
		this.corso = corso;
		this.titolo = titolo;
		this.event_location = event_location;
		this.room = room;
		this.data = data;
		this.descrizione = descrizione;
		this.start = start;
		this.stop = stop;
		this.all_day = all_day;
		this.availability = availability;
	}

	// id dell'evento
	private long id;

	// corso di riferimento
	private Corso corso;

	// // mail of the owner
	// @Column(name = "ORGANIZER")
	// private String organizer;

	// title of the event
	private String titolo;

	// where the place takes places
	private String event_location;

	// the room where the place takes places
	private String room;

	private Date data;

	// the description of the event
	private String descrizione;

	// the description of the event
	/*
	 * @Column(name = "NOTE") private ArrayList<Nota> note;
	 */
	// ora inizio
	private Time start;

	// ora fine
	private Time stop;

	// true if occupies the entire day
	private boolean all_day;

	// If this event counts as busy time or is free time that can be scheduled
	// over.
	private boolean availability;

	// // Whether guests can modify the event.
	// @Column(name = "CANMODIFY")
	// private boolean guests_can_modify;

	// // Whether guests can invite other guests
	// @Column(name = "CANINVITE")
	// private boolean guests_can_invite;

	// // Whether guests can see the list of attendees
	// @Column(name = "CANSEE")
	// private boolean guests_can_see;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Corso getCorso() {
		return corso;
	}

	public void setCorso(Corso corso) {
		this.corso = corso;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getEvent_location() {
		return event_location;
	}

	public void setEvent_location(String event_location) {
		this.event_location = event_location;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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

	public boolean isAll_day() {
		return all_day;
	}

	public void setAll_day(boolean all_day) {
		this.all_day = all_day;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}