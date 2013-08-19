package eu.trentorise.smartcampus.studyMate.utilities;

import java.text.SimpleDateFormat;

import eu.trentorise.smartcampus.studyMate.models.EventItem4Adapter;

public class EventItem extends EventItem4Adapter {

	private AdptDetailedEvent object;

	public EventItem(AdptDetailedEvent obj) {
		super();

		this.object = obj;
	}

	@Override
	public String getTitle() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(object.getDate());
	}

	@Override
	public String getContent() {
		return object.getName();
	}

	@Override
	public String getDescription() {
		return object.getDescription();
	}

	@Override
	public String getOra() {
		return object.getOra().substring(0, object.getOra().length() - 3);
	}

	@Override
	public String getRoom() {
		// TODO Auto-generated method stub
		return object.getRoom();
	}
}
