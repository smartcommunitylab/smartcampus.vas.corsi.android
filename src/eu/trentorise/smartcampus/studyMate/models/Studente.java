package eu.trentorise.smartcampus.studyMate.models;

import java.util.Collection;

public class Studente {

	// private static final long serialVersionUID = 8681710690984309605L;

	private long id;

	private String nome;

	private String cognome;

	private String corso_laurea;

	private String dipartimento;

	private String anno_corso;

	private String email;

	private long userSCId;

	private Collection<Corso> corsi;

	public Collection<Corso> getCorsi() {
		return corsi;
	}

	public void setCorsi(Collection<Corso> corsi) {
		this.corsi = corsi;
	}

	public long getUserSCId() {
		return userSCId;
	}

	public void setUserSCId(long userSCId) {
		this.userSCId = userSCId;
	}

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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCorso_laurea() {
		return corso_laurea;
	}

	public void setCorso_laurea(String corso_laurea) {
		this.corso_laurea = corso_laurea;
	}

	public String getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(String dipartimento) {
		this.dipartimento = dipartimento;
	}

	public String getAnno_corso() {
		return anno_corso;
	}

	public void setAnno_corso(String anno_corso) {
		this.anno_corso = anno_corso;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
>>>>>>> refs/remotes/origin/gruppi_studio
