package eu.trentorise.smartcampus.android.studyMate.models;

import java.util.Date;
import java.util.List;

public class Course {

	public int id; // id
	public String nome; // name
	public Date data_inizio; // start date
	public Date data_fine; // end date
	public String descrizione; // description
	public List<Comment> commenti; // list of comments
	public int valutazione_media;

	public Course() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Comment> getCommenti() {
		return commenti;
	}

	public Date getData_fine() {
		return data_fine;
	}

	public Date getData_inizio() {
		return data_inizio;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getNome() {
		return nome;
	}

	public void setCommenti(List<Comment> commenti) {
		this.commenti = commenti;
	}

	public void setData_fine(Date data_fine) {
		this.data_fine = data_fine;
	}

	public void setData_inizio(Date data_inizio) {
		this.data_inizio = data_inizio;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setValutazione_media(int valutazione_media) {
		valutazione_media = (valutazione_media * 5) / 10;
		this.valutazione_media = valutazione_media;
	}

	public int getValutazione_media() {
		return valutazione_media;
	}

}