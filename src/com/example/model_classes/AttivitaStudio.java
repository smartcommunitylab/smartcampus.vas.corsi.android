package com.example.model_classes;

import java.util.ArrayList;

public class AttivitaStudio {

	String oggetto;
	String data;
	String descrizione;
	ArrayList<Allegato> allegati;
	ArrayList<Servizio> servizi_attivita;
	
	public AttivitaStudio(String oggetto, String data, String descrizione,
			ArrayList<Allegato> allegati, ArrayList<Servizio> servizi_attivita) {
		super();
		this.oggetto = oggetto;
		this.data = data;
		this.descrizione = descrizione;
		this.allegati = allegati;
		this.servizi_attivita = servizi_attivita;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
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
