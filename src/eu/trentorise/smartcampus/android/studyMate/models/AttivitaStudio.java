package eu.trentorise.smartcampus.android.studyMate.models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class AttivitaStudio {

	String oggetto;
	ArrayList<Allegato> allegati;
	ArrayList<Servizio> servizi_attivita;
	long id;
	Corso corso;
	String titolo;
	String event_location;
	String room;
	Date data;
	String descrizione;
	Time start;
	Time stop;
	boolean all_day;
	boolean availability;

	public AttivitaStudio(String oggetto, ArrayList<Allegato> allegati,
			ArrayList<Servizio> servizi_attivita, long id, Corso corso,
			String titolo, String event_location, String room, Date data,
			String descrizione, Time start, Time stop, boolean all_day,
			boolean availability) {
		super();
		this.oggetto = oggetto;
		this.allegati = allegati;
		this.servizi_attivita = servizi_attivita;
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

	// public AttivitaStudio(long id, Corso corso, String titolo,
	// String event_location, String room, Date data, String descrizione,
	// Time start, Time stop, boolean all_day, boolean availability,
	// String oggetto, ArrayList<Allegato> allegati,
	// ArrayList<Servizio> servizi_attivita) {
	// // super(id, corso, titolo, event_location, room, data, descrizione,
	// // start, stop, all_day, availability);
	//
	// this.oggetto = oggetto;
	// this.allegati = allegati;
	// this.servizi_attivita = servizi_attivita;
	// }

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public ArrayList<Allegato> getAllegati() {
		return allegati;
	}

	public void setAllegati(ArrayList<Allegato> allegati) {
		this.allegati = allegati;
	}

	public ArrayList<Servizio> getServizi_attivita() {
		return servizi_attivita;
	}

	public void setServizi_attivita(ArrayList<Servizio> servizi_attivita) {
		this.servizi_attivita = servizi_attivita;
	}

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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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

}
