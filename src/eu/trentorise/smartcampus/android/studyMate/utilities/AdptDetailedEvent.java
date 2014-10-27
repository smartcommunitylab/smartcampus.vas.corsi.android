package eu.trentorise.smartcampus.android.studyMate.utilities;

import eu.trentorise.smartcampus.android.studyMate.models.EventoId;

public class AdptDetailedEvent {
	private EventoId evId;
	private String name;
	private String description;
	private String room; // prova

	public AdptDetailedEvent(EventoId evId, String name, String description, String room) {
		super();
		this.evId = evId;
		this.name = name;
		this.description = description;
		this.room = room; // prova
	}

	public AdptDetailedEvent(EventoId evId, String name, String description) {
		super();
		this.evId = evId;
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
