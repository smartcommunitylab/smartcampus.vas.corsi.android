package eu.trentorise.smartcampus.smartuni.models;

import java.util.Date;

public class Comment {
	public int id;
	public String testo; // text of the comment
	public Date data; // creation date
	public UserCourse autore; // author
	public int valutazione;

	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public UserCourse getAutore() {
		return autore;
	}

	public int getId() {
		return id;
	}

	public void setAutore(UserCourse autore) {
		this.autore = autore;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public String getTesto() {
		return testo;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public int getValutazione() {
		return valutazione;
	}

	public void setValutazione(int valutazione) {
		valutazione = (valutazione * 5) / 10;
		this.valutazione = valutazione;
	}
}