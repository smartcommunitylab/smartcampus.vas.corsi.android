package eu.trentorise.smartcampus.android.studyMate.models;

public class Message {
	String message;

	boolean isMine;
	boolean isStatusMessage;
	String name;

	public Message(String message, boolean isMine, String name) {
		super();
		this.message = message;
		this.isMine = isMine;
		this.isStatusMessage = false;
		this.name = name;
	}

	public Message(String message, boolean isMine) {
		super();
		this.message = message;
		this.isMine = isMine;
		this.isStatusMessage = false;
		this.name = null;
	}

	public Message(boolean status, String message) {
		super();
		this.message = message;
		this.isMine = false;
		this.isStatusMessage = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isMine() {
		return isMine;
	}

	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	public boolean isStatusMessage() {
		return isStatusMessage;
	}

	public void setStatusMessage(boolean isStatusMessage) {
		this.isStatusMessage = isStatusMessage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}