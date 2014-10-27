package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.Calendar;

import android.content.Context;
import android.content.res.Resources;
import eu.trentorise.smartcampus.android.studyMate.models.EventItem4Adapter;
import eu.trentorise.smartcampus.android.studyMate.models.EventoId;

public class EventItem extends EventItem4Adapter {

	private AdptDetailedEvent object;
	private String day;
	private Resources res;
	private Context context;

	public EventItem(AdptDetailedEvent obj, Context context) {
		super();
		this.setRes(context.getResources());
		this.object = obj;
		this.context = context;
	}

	@Override
	public String getTitle() {
		Calendar c = Calendar.getInstance();
		c.setTime(object.getEvId().getDate());

		DatesUtil dateUtil = new DatesUtil(context);
		day = dateUtil.dateToAgendaFormat(object.getEvId().getDate());
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
		String time = object.getEvId().getStart().toString();
		return object.getEvId().getStart().toString().substring(0, (time.length()) - 3);
	}

	@Override
	public String getRoom() {
		return object.getRoom();
	}
	
	@Override
	public EventoId getEvId() {
		return object.getEvId();
	}

	@Override
	public String toString() {
		super.toString();
		return day;
	}

	public Resources getRes() {
		return res;
	}

	public void setRes(Resources res) {
		this.res = res;
	}
	
	public AdptDetailedEvent getObject(){
		return this.object;
	}
}
