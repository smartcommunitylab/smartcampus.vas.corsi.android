package eu.trentorise.smartcampus.studyMate.models;

import java.io.Serializable;
import java.util.ArrayList;

public class RisorsaPhl {

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
}
