package eu.trentorise.smartcampus.android.studyMate.models;

import java.io.Serializable;

public class PianoStudi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3232470231053839963L;

	private long pdsId;

	private String pdsCod;

	public String getPdsCod() {
		return pdsCod;
	}

	public void setPdsCod(String pdsCod) {
		this.pdsCod = pdsCod;
	}

	public long getPdsId() {
		return pdsId;
	}

	public void setPdsId(long pdsId) {
		this.pdsId = pdsId;
	}

}
