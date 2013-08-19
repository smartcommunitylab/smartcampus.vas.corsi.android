package eu.trentorise.smartcampus.studyMate.models;

public class EventItem4Adapter {
	private String title;
	private String content;
	private String description;
	private String ora;
	private String room; // /prova

	public EventItem4Adapter() {
		this.title = null;
		this.content = null;
		this.description = null;
		this.ora = null;
		this.room = null; // ///prova
	}

	public EventItem4Adapter(String title, String content, String description,
			String ora, String room) {
		super();
		this.title = title;
		this.content = content;
		;
		this.description = description;
		this.ora = ora;
		this.room = room; // /// prova
	}

	public EventItem4Adapter(String title, String content, String description,
			String ora) {
		super();
		this.title = title;
		this.content = content;
		;
		this.description = description;
		this.ora = ora;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOra() {
		return ora;
	}

	public void setOra(String ora) {
		this.ora = ora;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

}
