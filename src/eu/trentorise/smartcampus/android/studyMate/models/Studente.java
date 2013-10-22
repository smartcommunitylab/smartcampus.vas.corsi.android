package eu.trentorise.smartcampus.android.studyMate.models;

import java.util.Collection;
import java.util.List;

public class Studente {

	// private static final long serialVersionUID = 8681710690984309605L;

	private long id;

	private String nome;

	private String cognome;

	private String corso_laurea;

	private Dipartimento dipartimento;

	private String anno_corso;

	private String email;

	private long userSCId;

	private Collection<Corso> corsi;

	private String idsCorsiSuperati;

	private List<CorsoLite> corsiSuperati;

	private String idsGruppiDiStudio;

	private List<GruppoDiStudio> gruppiDiStudio;

	private String idsCorsiInteresse;

	private List<CorsoLite> corsiInteresse;

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

	public Dipartimento getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(Dipartimento dipartimento) {
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

	public String getIdsCorsiSuperati() {
		return idsCorsiSuperati;
	}

	public void setIdsCorsiSuperati(String idsCorsiSuperati) {
		this.idsCorsiSuperati = idsCorsiSuperati;
	}

	public List<CorsoLite> getCorsiSuperati() {
		return corsiSuperati;
	}

	public void setCorsiSuperati(List<CorsoLite> corsiSuperati) {
		this.corsiSuperati = corsiSuperati;
	}

	public String getIdsGruppiDiStudio() {
		return idsGruppiDiStudio;
	}

	public void setIdsGruppiDiStudio(String idsGruppiDiStudio) {
		this.idsGruppiDiStudio = idsGruppiDiStudio;
	}

	public List<GruppoDiStudio> getGruppiDiStudio() {
		return gruppiDiStudio;
	}

	public void setGruppiDiStudio(List<GruppoDiStudio> gruppiDiStudio) {
		this.gruppiDiStudio = gruppiDiStudio;
	}

	public String getIdsCorsiInteresse() {
		return idsCorsiInteresse;
	}

	public void setIdsCorsiInteresse(String idsCorsiInteresse) {
		this.idsCorsiInteresse = idsCorsiInteresse;
	}

	public List<CorsoLite> getCorsiInteresse() {
		return corsiInteresse;
	}

	public void setCorsiInteresse(List<CorsoLite> corsiInteresse) {
		this.corsiInteresse = corsiInteresse;
	}

}
