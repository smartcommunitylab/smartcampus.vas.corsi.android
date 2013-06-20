package eu.trentorise.smartcampus.smartuni.models;

import java.util.Collection;



public class Dipartimento {

	private static final long serialVersionUID = 8681710690984301605L;

	private long id;

	private String nome;
	

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
