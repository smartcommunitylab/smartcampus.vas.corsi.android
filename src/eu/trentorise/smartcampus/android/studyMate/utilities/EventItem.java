package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.Calendar;

import android.content.Context;
import android.content.res.Resources;
import eu.trentorise.smartcampus.android.studyMate.models.EventItem4Adapter;

public class EventItem extends EventItem4Adapter {

	private AdptDetailedEvent object;
	private String day;
	private Resources res;
	private Context context;

	public EventItem(AdptDetailedEvent obj, Context context) {
		super();
		this.res = context.getResources();
		this.object = obj;
		this.context = context;
	}

	@Override
	public String getTitle() {
		Calendar c = Calendar.getInstance();
		c.setTime(object.getDate());

		DatesUtil dateUtil = new DatesUtil(context);
		day = dateUtil.dateToAgendaFormat(object.getDate());
		return this.toString();
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
		return object.getRoom();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		super.toString();
		return day;
	}
}
