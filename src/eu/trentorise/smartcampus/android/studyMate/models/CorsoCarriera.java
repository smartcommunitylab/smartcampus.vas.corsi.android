package eu.trentorise.smartcampus.android.studyMate.models;

import java.io.Serializable;
import java.util.Date;

public class CorsoCarriera implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1306548062859361763L;

	private long id;
	private String cod;
	private String name;
	private String result;
	private boolean lode;
	private String weight;
	private Date date;
	private long studenteId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean isLode() {
		return lode;
	}

	public void setLode(boolean lode) {
		this.lode = lode;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getStudente() {
		return studenteId;
	}

	public void setStudente(long studenteId) {
		this.studenteId = studenteId;
	}

}
