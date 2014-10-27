package eu.trentorise.smartcampus.android.studyMate.models;

public class EventItem4Adapter {
	private String title;
	private String content;
	private String description;
	private EventoId evId;
	private String ora;
	private String room;

	public EventItem4Adapter() {
		this.title = null;
		this.content = null;
		this.description = null;
		this.evId = null;
		this.ora = null;
		this.room = null; // ///prova
	}

	public EventItem4Adapter(String title, String content, String description,
			String ora, String room, EventoId evId) {
		super();
		this.title = title;
		this.content = content;
		this.description = description;
		this.ora = ora;
		this.evId = evId;
		this.room = room; 
	}

	public EventItem4Adapter(String title, String content, String description,
			String ora, EventoId evId) {
		super();
		this.title = title;
		this.content = content;
		this.description = description;
		this.ora = ora;
		this.evId = evId;
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

	public EventoId getEvId() {
		return evId;
	}

	public void setEvId(EventoId evId) {
		this.evId = evId;
	}

}
