package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.models.EventoId;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.PostEvent;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import it.smartcampuslab.studymate.R;

public class AddEventFragment extends SherlockFragment {
	private int mYear;
	private int mMonth;
	private int mDay;

	private int hour;
	private int minute;

	// private TextView mDateDisplay;
	private EditText mPickDate;
	private EditText mPickTime;
	static final int DATE_DIALOG_ID = 0;
	public View fview;

	private List<CorsoCarriera> cC;
	public static ProgressDialog pd;
	private Evento evento = null;
	Spinner coursesSpinner;
	private EventoId eId;
	private Date date;
	private EditText title;
	private EditText description;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fview = inflater.inflate(R.layout.activity_add_event, container, false);
		return fview;
	}

	@Override
	public void onStart() {
		super.onStart();
		evento = new Evento();
		eId = new EventoId();
		date = new Date();
		mPickDate = (EditText) fview.findViewById(R.id.myDatePickerButton);
		mPickTime = (EditText) fview.findViewById(R.id.myTimePickerButton);
		mPickDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePickerDialog(fview);
			}
		});

		mPickTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showTimePickerDialog(fview);
			}
		});
		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		// get the current Time
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		// display the current date
		updateDisplay();

		int customYear = mYear - 1900;
		date.setYear(customYear);
		date.setMonth(mMonth);
		date.setDate(mDay);
		eId.setStart(new Time(hour, minute, 0));
		eId.setStop(new Time(hour, minute, 0));
		title = (EditText) fview.findViewById(R.id.editTextTitle);
		description = (EditText) fview.findViewById(R.id.editTextDescription);
		coursesSpinner = (Spinner) fview.findViewById(R.id.spinnerCorsi);
		new CoursesLoader().execute();
		Button button_ok = (Button) fview.findViewById(R.id.button_ok);
		Button button_cancel = (Button) fview.findViewById(R.id.button_annulla);
		button_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getSherlockActivity().onBackPressed();
			}
		});
		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				evento.setType(title.getText().toString());
				evento.setTitle(cC
						.get(coursesSpinner.getSelectedItemPosition())
						.getName());
				// evento.setTeacher("IO");
				evento.setPersonalDescription(description.getText().toString());
				evento.setEventoId(eId);
				evento.setAdCod(Long.parseLong(cC.get(
						coursesSpinner.getSelectedItemPosition()).getCod()));
				long dateR = 10000 * (date.getTime() / 10000);
				eId.setDate(new Date(dateR));

				new PostEvent(getSherlockActivity(), evento).execute();
				Toast.makeText(
						getSherlockActivity(),
						getSherlockActivity().getResources().getString(
								R.string.toast_event_added), Toast.LENGTH_SHORT)
						.show();
				getSherlockActivity().onBackPressed();
			}
		});

	}

	public void updateDisplay() {
		this.mPickDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mDay).append("-").append(mMonth + 1).append("-")
				.append(mYear).append(" "));
		if (minute < 10) {
			this.mPickTime.setText(new StringBuilder().append(hour)
					.append(":0").append(minute));
		} else {
			this.mPickTime.setText(new StringBuilder().append(hour).append(":")
					.append(minute));

		}
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSherlockActivity().getSupportFragmentManager(),
				"datePicker");
	}

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getSherlockActivity().getSupportFragmentManager(),
				"timePicker");
	}

	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getSherlockActivity(), this, mYear,
					mMonth, mDay);
		}

		@SuppressWarnings("deprecation")
		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user;
			((EditText) fview.findViewById(R.id.myDatePickerButton))
			// Month is 0 based so add 1
					.setText(day + "-" + (month + 1) + "-" + year);
			date.setYear(year - 1900);
			date.setMonth(month);
			date.setDate(day);
		}

	}

	public class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getSherlockActivity(), this, hour,
					minute, DateFormat.is24HourFormat(getSherlockActivity()));
		}

		@SuppressWarnings("deprecation")
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			if (minute < 10) {
				((EditText) fview.findViewById(R.id.myTimePickerButton))
						.setText(hourOfDay + ":0" + minute);
			} else {
				((EditText) fview.findViewById(R.id.myTimePickerButton))
						.setText(hourOfDay + ":" + minute);

			}
			eId.setStart(new Time(hourOfDay, minute, 0));
			eId.setStop(new Time(hourOfDay, minute, 0));
		}
	}

	private class CoursesLoader extends
			AsyncTask<Void, Void, List<CorsoCarriera>> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		String body;

		@Override
		protected List<CorsoCarriera> doInBackground(Void... params) {
			return getFollowingCourses();
		}

		private List<CorsoCarriera> getFollowingCourses() {
			mProtocolCarrier = new ProtocolCarrier(context,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_MY_COURSES_NOT_PASSED);
			request.setMethod(Method.GET);
			@SuppressWarnings("unused")
			BasicProfile bp = new BasicProfile();
			MessageResponse response;
			try {
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {

					body = response.getBody();

				} else {
					return null;
				}
			} catch (ConnectionException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (AACException e) {
				e.printStackTrace();
			}

			return Utils.convertJSONToObjects(body, CorsoCarriera.class);
		}

		@Override
		protected void onPostExecute(List<CorsoCarriera> result) {
			super.onPostExecute(result);
			pd.dismiss();
			if ((result == null)||(result.size()==0)) {
				Toast.makeText(getActivity(), R.string.invalid_career,
						Toast.LENGTH_SHORT).show();
				getSherlockActivity().finish();
			} else {
				cC = result;
				List<String> resultStrings = new ArrayList<String>();

				for (CorsoCarriera cl : result) {
					resultStrings.add(cl.getName());
				}

				ArrayAdapter<String> adapterInitialList = new ArrayAdapter<String>(
						getSherlockActivity(),
						R.layout.list_studymate_row_list_simple, resultStrings);

				coursesSpinner.setAdapter(adapterInitialList);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			new ProgressDialog(getSherlockActivity());
			pd = ProgressDialog.show(
					getSherlockActivity(),
					getSherlockActivity().getResources().getString(
							R.string.dialog_courses_events),
					getSherlockActivity().getResources().getString(
							R.string.dialog_loading));
		}

	}

}
