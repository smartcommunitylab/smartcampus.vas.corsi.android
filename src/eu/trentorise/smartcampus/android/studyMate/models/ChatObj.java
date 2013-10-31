package eu.trentorise.smartcampus.android.studyMate.models;

import java.sql.Date;

public class ChatObj {
	// id del gruppo

	private long id;

	// Nome del gruppo

	private String nome;

	// Nome del gruppo

	private Date data;

	public ChatObj() {
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
