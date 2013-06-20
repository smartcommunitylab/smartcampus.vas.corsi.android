package eu.trentorise.smartcampus.smartuni.models;


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
	private Integer rating_contenuto;

	// valutazione carico studio
	private Integer rating_carico_studio;

	// valutazione lezioni
	private Integer rating_lezioni;

	// valutazione materiali
	private Integer rating_materiali;

	// valutazione esame
	private Integer rating_esame;

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

	public Integer getRating_contenuto() {
		return rating_contenuto;
	}

	public void setRating_contenuto(Integer rating_contenuto) {
		this.rating_contenuto = rating_contenuto;
	}

	public Integer getRating_carico_studio() {
		return rating_carico_studio;
	}

	public void setRating_carico_studio(int rating_carico_studio) {
		this.rating_carico_studio = rating_carico_studio;
	}

	public Integer getRating_lezioni() {
		return rating_lezioni;
	}

	public void setRating_lezioni(Integer rating_lezioni) {
		this.rating_lezioni = rating_lezioni;
	}

	public Integer getRating_materiali() {
		return rating_materiali;
	}

	public void setRating_materiali(int rating_materiali) {
		this.rating_materiali = rating_materiali;
	}

	public Integer getRating_esame() {
		return rating_esame;
	}

	public void setRating_esame(Integer rating_esame) {
		this.rating_esame = rating_esame;
	}

}
