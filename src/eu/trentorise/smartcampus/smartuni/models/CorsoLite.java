package eu.trentorise.smartcampus.smartuni.models;


public class CorsoLite {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1619282265752237328L;

	// id del corso
	private long id;

	// nome del corso
	private String nome;

	// nome del dipartimento
	private long dipartimento;

	public CorsoLite() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(long dipartimento) {
		this.dipartimento = dipartimento;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
