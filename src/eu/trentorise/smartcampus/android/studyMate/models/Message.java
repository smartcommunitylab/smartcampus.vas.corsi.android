package eu.trentorise.smartcampus.android.studyMate.models;

public class Message extends ChatObj {
	// id del gruppo

	// Nome del gruppo

	private String testo;

	public Message() {
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

}
