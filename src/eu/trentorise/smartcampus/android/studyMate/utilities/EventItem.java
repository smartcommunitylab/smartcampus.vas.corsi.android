package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.res.Resources;
import eu.trentorise.smartcampus.android.studyMate.models.EventItem4Adapter;
import eu.trentorise.smartcampus.studymate.R;

public class EventItem extends EventItem4Adapter {

	private AdptDetailedEvent object;
	private String day;
	private Resources res;

	public EventItem(AdptDetailedEvent obj, Resources res) {
		super();
		this.res = res;
		this.object = obj;
	}

	@Override
	public String getTitle() {
		Calendar c = Calendar.getInstance();
		c.setTime(object.getDate());
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		day = convertToDayOfWeek(dayOfWeek);
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

	public String convertToDayOfWeek(int day) {
		String dayOfWeek = new String();

		switch (day) {
		case 1:
			dayOfWeek = res.getString(R.string.Sunday);
			break;
		case 2:
			dayOfWeek = res.getString(R.string.Monday);
			break;
		case 3:
			dayOfWeek = res.getString(R.string.Tuesday);
			break;
		case 4:
			dayOfWeek = res.getString(R.string.Wednesday);
			break;
		case 5:
			dayOfWeek = res.getString(R.string.Thursday);
			break;
		case 6:
			dayOfWeek = res.getString(R.string.Friday);
			break;
		case 7:
			dayOfWeek = res.getString(R.string.Saturday);
			break;

		default:
			break;
		}

		return dayOfWeek;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		super.toString();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return day + " - " + dateFormat.format(object.getDate());
	}
}
