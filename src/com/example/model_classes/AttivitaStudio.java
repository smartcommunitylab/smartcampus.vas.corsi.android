package com.example.model_classes;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import eu.trentorise.smartcampus.studyMate.models.Corso;
import eu.trentorise.smartcampus.studyMate.models.Evento;

public class AttivitaStudio extends Evento {

	String oggetto;
	ArrayList<Allegato> allegati;
	ArrayList<Servizio> servizi_attivita;

	public AttivitaStudio(long id, Corso corso, String titolo,
			String event_location, String room, Date data, String descrizione,
			Time start, Time stop, boolean all_day, boolean availability,
			String oggetto, ArrayList<Allegato> allegati,
			ArrayList<Servizio> servizi_attivita) {
		super(id, corso, titolo, event_location, room, data, descrizione,
				start, stop, all_day, availability);
		this.oggetto = oggetto;
		this.allegati = allegati;
		this.servizi_attivita = servizi_attivita;
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

	public ArrayList<Servizio> getServizi_attivita() {
		return servizi_attivita;
	}

	public void setServizi_attivita(ArrayList<Servizio> servizi_attivita) {
		this.servizi_attivita = servizi_attivita;
	}

}
