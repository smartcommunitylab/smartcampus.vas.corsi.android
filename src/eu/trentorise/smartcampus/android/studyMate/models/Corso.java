package eu.trentorise.smartcampus.android.studyMate.models;

import java.io.Serializable;
import java.util.Date;

public class Corso extends CorsoLite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public Corso(){
		super();
	}

	// data di inizio del corso
	private Date data_inizio;

	// data di inizio del corso
	private long id_dipartimento;

	private long id_corsoLaurea;

	// data di fine del corso
	private Date data_fine;

	// descrizione del corso
	private String descrizione;

	// valutazione media di tutti gli UtenteCorsi
	private Float valutazione_media;

	private Float rating_contenuto;

	// valutazione carico studio

	private Float rating_carico_studio;

	// valutazione lezioni

	private Float rating_lezioni;

	// valutazione materiali

	private Float rating_materiali;

	// valutazione esame

	private Float rating_esame;


	public Float getRating_contenuto() {
		return rating_contenuto;
	}

	public void setRating_contenuto(Float rating_contenuto) {
		this.rating_contenuto = rating_contenuto;
	}

	public Float getRating_carico_studio() {
		return rating_carico_studio;
	}

	public void setRating_carico_studio(Float rating_carico_studio) {
		this.rating_carico_studio = rating_carico_studio;
	}

	public Float getRating_lezioni() {
		return rating_lezioni;
	}

	public void setRating_lezioni(Float rating_lezioni) {
		this.rating_lezioni = rating_lezioni;
	}

	public Float getRating_materiali() {
		return rating_materiali;
	}

	public void setRating_materiali(Float rating_materiali) {
		this.rating_materiali = rating_materiali;
	}

	public Float getRating_esame() {
		return rating_esame;
	}

	public void setRating_esame(Float rating_esame) {
		this.rating_esame = rating_esame;
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

	public long getId_dipartimento() {
		return id_dipartimento;
	}

	public void setId_dipartimento(long id_dipartimento) {
		this.id_dipartimento = id_dipartimento;
	}

	public long getId_corsoLaurea() {
		return id_corsoLaurea;
	}

	public void setId_corsoLaurea(long id_corsoLaurea) {
		this.id_corsoLaurea = id_corsoLaurea;
	}

	// public boolean isIs_libretto() {
	// return is_libretto;
	// }
	//
	// public void setIs_libretto(boolean is_libretto) {
	// this.is_libretto = is_libretto;
	// }

}
