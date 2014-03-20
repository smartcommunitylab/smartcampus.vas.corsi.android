package eu.trentorise.smartcampus.android.studyMate.models;

import java.io.Serializable;
import java.util.List;

public class CorsoLaurea implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4433036858093851719L;
	private long cdsId;
	private String cdsCod;
	private String descripion;
	private String durata;
	private String aaOrd;
	private List<PianoStudi> pds;
	private Dipartimento dipartimento;

	public long getId() {
		return cdsId;
	}

	public void setId(long id) {
		this.cdsId = id;
	}

	public Dipartimento getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(Dipartimento dipartimento) {
		this.dipartimento = dipartimento;
	}

	public long getCdsId() {
		return cdsId;
	}

	public void setCdsId(long cdsId) {
		this.cdsId = cdsId;
	}

	public String getCdsCod() {
		return cdsCod;
	}

	public void setCdsCod(String cdsCod) {
		this.cdsCod = cdsCod;
	}

	public String getDescripion() {
		return descripion;
	}

	public void setDescripion(String descripion) {
		this.descripion = descripion;
	}

	public String getDurata() {
		return durata;
	}

	public void setDurata(String durata) {
		this.durata = durata;
	}

	public String getAaOrd() {
		return aaOrd;
	}

	public void setAaOrd(String aaOrd) {
		this.aaOrd = aaOrd;
	}

	public List<PianoStudi> getPds() {
		return pds;
	}

	public void setPds(List<PianoStudi> pds) {
		this.pds = pds;
	}

}
