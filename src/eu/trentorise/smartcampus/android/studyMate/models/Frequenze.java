package eu.trentorise.smartcampus.android.studyMate.models;

public class Frequenze {

	private long id;

	private Studente studente;

	private Corso corso;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Studente getStudente() {
		return studente;
	}

	public void setStudente(Studente studente) {
		this.studente = studente;
	}

	public Corso getCorso() {
		return corso;
	}

	public void setCorso(Corso corso) {
		this.corso = corso;
	}

}
