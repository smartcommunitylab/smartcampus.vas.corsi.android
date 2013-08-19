package eu.trentorise.smartcampus.studyMate.models;

import java.io.Serializable;
import java.util.Date;

public class Corso extends CorsoLite implements Serializable {

	// data di inizio del corso
	private Date data_inizio;

	// data di inizio del corso
	private long id_dipartimento;

	// data di fine del corso
	private Date data_fine;

	// descrizione del corso
	private String descrizione;

	// valutazione media di tutti gli UtenteCorsi
	private float valutazione_media;

	// seguo o non seguo il corso
	private boolean seguito;
	
	private boolean is_libretto;

	public Corso() {

	}

	public Date getData_inizio() {
		return data_inizio;
	}

	public void setData_inizio(Date data_inizio) {
		this.data_inizio = data_inizio;
	}

	public Date getData_fine() {
		return data_fine;
	}

	public void setData_fine(Date data_fine) {
		this.data_fine = data_fine;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public float getValutazione_media() {
		return valutazione_media;
	}

	public void setValutazione_media(float valutazione_media) {
		this.valutazione_media = valutazione_media;
	}

	public boolean isSeguito() {
		return seguito;
	}

	public void setSeguito(boolean seguito) {
		this.seguito = seguito;
	}

	public long getId_dipartimento() {
		return id_dipartimento;
	}

	public void setId_dipartimento(long id_dipartimento) {
		this.id_dipartimento = id_dipartimento;
	}

	public boolean isIs_libretto() {
		return is_libretto;
	}

	public void setIs_libretto(boolean is_libretto) {
		this.is_libretto = is_libretto;
	}

}
