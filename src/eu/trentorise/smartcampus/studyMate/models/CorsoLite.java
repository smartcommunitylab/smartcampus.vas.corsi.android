package eu.trentorise.smartcampus.studyMate.models;

import java.io.Serializable;

public class CorsoLite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1619282265752237328L;

	// id del corso
	private Long id;

	// nome del corso
	private String nome;

	public CorsoLite() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
