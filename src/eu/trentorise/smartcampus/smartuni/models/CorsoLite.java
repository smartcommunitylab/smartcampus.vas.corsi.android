package eu.trentorise.smartcampus.smartuni.models;

import java.io.Serializable;

public class CorsoLite implements Serializable
{

	// id del corso
	private Long	id;

	// nome del corso
	private String	nome;

	// nome del dipartimento
	private long	dipartimento;

	public CorsoLite()
	{
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public long getDipartimento()
	{
		return dipartimento;
	}

	public void setDipartimento(long dipartimento)
	{
		this.dipartimento = dipartimento;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

}
