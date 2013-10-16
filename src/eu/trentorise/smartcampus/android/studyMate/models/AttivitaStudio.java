package eu.trentorise.smartcampus.android.studyMate.models;

import java.util.ArrayList;

public class AttivitaStudio {

	String oggetto;
	ArrayList<Allegato> allegati;
	// ArrayList<Servizio> servizi_attivita;
	long id;
	Corso corso;
	String titolo;
	String event_location;
	String room;
	String data;
	String descrizione;
	String start;

	boolean all_day;
	boolean availability;

	boolean prenotazione_aule;
	boolean mensa;
	boolean tutoring;
	boolean biblioteca;

	public AttivitaStudio() {

	}

	public AttivitaStudio(String oggetto, ArrayList<Allegato> allegati,
			long id, Corso corso, String titolo, String event_location,
			String room, String data, String descrizione, String start,
			boolean all_day, boolean availability, boolean prenotazione_aule,
			boolean mensa, boolean tutoring, boolean biblioteca) {
		super();
		this.oggetto = oggetto;
		this.allegati = allegati;
		this.id = id;
		this.corso = corso;
		this.titolo = titolo;
		this.event_location = event_location;
		this.room = room;
		this.data = data;
		this.descrizione = descrizione;
		this.start = start;
		this.all_day = all_day;
		this.availability = availability;
		this.prenotazione_aule = prenotazione_aule;
		this.mensa = mensa;
		this.tutoring = tutoring;
		this.biblioteca = biblioteca;
	}

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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
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

	public boolean isPrenotazione_aule() {
		return prenotazione_aule;
	}

	public void setPrenotazione_aule(boolean prenotazione_aule) {
		this.prenotazione_aule = prenotazione_aule;
	}

	public boolean isMensa() {
		return mensa;
	}

	public void setMensa(boolean mensa) {
		this.mensa = mensa;
	}

	public boolean isTutoring() {
		return tutoring;
	}

	public void setTutoring(boolean tutoring) {
		this.tutoring = tutoring;
	}

	public boolean isBiblioteca() {
		return biblioteca;
	}

	public void setBiblioteca(boolean biblioteca) {
		this.biblioteca = biblioteca;
	}
	
	

}
