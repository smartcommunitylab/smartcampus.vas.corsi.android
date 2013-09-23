package eu.trentorise.smartcampus.studyMate.models;

public class CourseLite {

	public String id;
	public String nome;
	private long dipartimento;

	public CourseLite() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return nome;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.nome = name;
	}

	public long getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(long dipartimento) {
		this.dipartimento = dipartimento;
	}

}
