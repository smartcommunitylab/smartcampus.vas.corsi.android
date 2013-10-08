package eu.trentorise.smartcampus.android.studyMate.models;

import java.util.ArrayList;

public class RisorsaPhl {

	private String error;
	private CwdPHL cwd;
	private ArrayList<CwdPHL> cdc;

	public CwdPHL getCwd() {
		return cwd;
	}

	public void setCwd(CwdPHL cwd) {
		this.cwd = cwd;
	}

	public ArrayList<CwdPHL> getCdc() {
		return cdc;
	}

	public void setCdc(ArrayList<CwdPHL> cdc) {
		this.cdc = cdc;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
