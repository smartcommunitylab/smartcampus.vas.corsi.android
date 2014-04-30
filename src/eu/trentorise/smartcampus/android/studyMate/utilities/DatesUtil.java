package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import eu.trentorise.smartcampus.studymate.R;

public class DatesUtil {

	private Context context;
	private String[] days;
	private String[] months;

	public DatesUtil(Context context) {
		this.context = context;

		final String[] d = { context.getResources().getString(R.string.Sunday),
				context.getResources().getString(R.string.Monday),
				context.getResources().getString(R.string.Tuesday),
				context.getResources().getString(R.string.Wednesday),
				context.getResources().getString(R.string.Thursday),
				context.getResources().getString(R.string.Friday),
				context.getResources().getString(R.string.Saturday) };

		days = d;

		final String[] m = {
				context.getResources().getString(R.string.January),
				context.getResources().getString(R.string.February),
				context.getResources().getString(R.string.March),
				context.getResources().getString(R.string.April),
				context.getResources().getString(R.string.May),
				context.getResources().getString(R.string.June),
				context.getResources().getString(R.string.July),
				context.getResources().getString(R.string.July),
				context.getResources().getString(R.string.August),
				context.getResources().getString(R.string.September),
				context.getResources().getString(R.string.October),
				context.getResources().getString(R.string.November),
				context.getResources().getString(R.string.December) };

		months = m;
	}

	public String dateToAgendaFormat(Date date) {
		String dateFormatSM = null;

		Calendar cal = Calendar.getInstance();
		cal.setTime(date); // someDate is a Date
		int day = cal.get(Calendar.DAY_OF_MONTH);

		String dayS = intToDay(date.getDay());
		String monthS = intToMonth(date.getMonth());
		String yearS = String.valueOf(date.getYear() + 1900);

		dateFormatSM = dayS + " " + day + " " + monthS + " " + yearS;

		return dateFormatSM;
	}

	public String intToDay(int day) {

		return days[day];
	}

	public String intToMonth(int month) {

		return months[month];
	}
}
