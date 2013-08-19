package eu.trentorise.smartcampus.studyMate.models;

public class Commento {

	private int id;

	// corso di riferimento
	private Corso corso;

	// utente che ha scritto commento
	private Studente id_studente;

	// testo del commento
	private String testo;

	// data in cui e' stato scritto commento
	private String data_inserimento;

	// valutazione contenuto
	private float rating_contenuto;

	// valutazione carico studio
	private float rating_carico_studio;

	// valutazione lezioni
	private float rating_lezioni;

	// valutazione materiali
	private float rating_materiali;

	// valutazione esame
	private float rating_esame;

	public Commento() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Corso getCorso() {
		return corso;
	}

	public void setCorso(Corso corso) {
		this.corso = corso;
	}

	public Studente getId_studente() {
		return id_studente;
	}

	public void setId_studente(Studente id_studente) {
		this.id_studente = id_studente;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getData_inserimento() {
		return data_inserimento;
	}

	public void setData_inserimento(String data_inserimento) {
		this.data_inserimento = data_inserimento;
	}

	public float getRating_contenuto() {
		return rating_contenuto;
	}

	public void setRating_contenuto(float f) {
		this.rating_contenuto = f;
	}

	public float getRating_carico_studio() {
		return rating_carico_studio;
	}

	public void setRating_carico_studio(float rating_carico_studio) {
		this.rating_carico_studio = rating_carico_studio;
	}

	public float getRating_lezioni() {
		return rating_lezioni;
	}

	public void setRating_lezioni(float rating_lezioni) {
		this.rating_lezioni = rating_lezioni;
	}

	public float getRating_materiali() {
		return rating_materiali;
	}

	public void setRating_materiali(float rating_materiali) {
		this.rating_materiali = rating_materiali;
	}

	public float getRating_esame() {
		return rating_esame;
	}

	public void setRating_esame(float rating_esame) {
		this.rating_esame = rating_esame;
	}

}
