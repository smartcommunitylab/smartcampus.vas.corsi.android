package eu.trentorise.smartcampus.android.studyMate.models;

import android.text.format.Time;

public class ChatMessage extends ChatObject {

	String message;

	public ChatMessage(Time publicationTime, String message) {
		super(publicationTime);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * @Override public boolean isMine(int param) { // TODO Auto-generated
	 * method stub return super.isMine(param); }
	 */

}
