package eu.trentorise.smartcampus.studyMate.models;

import java.io.Serializable;

public class CorsoLaurea implements Serializable {

	private static final long serialVersionUID = 8681730600984301605L;

	private long id;

	private String nome;

	private Dipartimento dipartimento;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Dipartimento getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(Dipartimento dipartimento) {
		this.dipartimento = dipartimento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
