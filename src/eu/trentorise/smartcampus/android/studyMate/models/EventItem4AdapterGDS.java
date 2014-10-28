package eu.trentorise.smartcampus.android.studyMate.models;

public class EventItem4AdapterGDS {
	private String title;
	private String content;
	private String description;
	private String ora;
	private String room;

	public EventItem4AdapterGDS() {
		this.title = null;
		this.content = null;
		this.description = null;
		this.ora = null;
		this.room = null; // ///prova
	}

	public EventItem4AdapterGDS(String title, String content, String description,
			String ora, String room, EventoId evId) {
		super();
		this.title = title;
		this.content = content;
		this.description = description;
		this.ora = ora;
		this.room = room; 
	}

	public EventItem4AdapterGDS(String title, String content, String description,
			String ora, EventoId evId) {
		super();
		this.title = title;
		this.content = content;
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
