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
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.models.EventoId;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class EditEventFragment extends SherlockFragment {
	private int mYear;
	private int mMonth;
	private int mDay;

	public View fview;

	private int hour;
	private int minute;

	private EditText mPickDate;
	private EditText mPickTime;
	static final int DATE_DIALOG_ID = 0;

	private Evento eventoModificato;
	private Evento evento;
	Spinner coursesSpinner;
	String cN;
	private EditText title;
	private EditText description;
	private EventoId eId;
	private Date date;

	private long dateInitial;
	private long timeFromInitial;
	private long timeToInitial;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fview = inflater.inflate(R.layout.activity_add_event_4_course,
				container, false);
		evento = (Evento) getArguments().getSerializable("eventSelectedEdit");
		dateInitial = evento.getEventoId().getDate().getTime();
		timeFromInitial = evento.getEventoId().getStart().getTime();
		timeToInitial = evento.getEventoId().getStop().getTime();
		return fview;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart() {

		super.onStart();
		eId = new EventoId();
		eventoModificato = evento;
		date = new Date();
		mPickDate = (EditText) fview
				.findViewById(R.id.myDatePickerButton4Course);
		mPickDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePickerDialog(fview);

			}
		});
		mPickTime = (EditText) fview
				.findViewById(R.id.myTimePickerButton4Course);
		mPickTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showTimePickerDialog(fview);

			}
		});
		// get the ex date of previous event
		mYear = evento.getEventoId().getDate().getYear() + 1900;
		mMonth = evento.getEventoId().getDate().getMonth();
		mDay = evento.getEventoId().getDate().getDate();
		// get the current Time
		hour = evento.getEventoId().getStart().getHours();
		minute = evento.getEventoId().getStart().getMinutes();
		date.setYear(mYear - 1900);
		date.setMonth(mMonth);
		date.setDate(mDay);
		eId.setDate(date);
		eId.setStart(new Time(hour, minute, 0));
		eId.setStop(new Time(hour, minute, 0));
		// display the current date
		updateDisplay();
		title = (EditText) fview.findViewById(R.id.editTextTitle4Course);
		title.setText(evento.getType());
		description = (EditText) fview
				.findViewById(R.id.editTextDescription4Course);
		description.setText(evento.getPersonalDescription());
		coursesSpinner = (Spinner) fview.findViewById(R.id.spinnerCorsi4Course);
		List<String> resultStrings = new ArrayList<String>();
		resultStrings.add(evento.getTitle());
		ArrayAdapter<String> adapterInitialList = new ArrayAdapter<String>(
				getSherlockActivity(), R.layout.list_studymate_row_list_simple,
				resultStrings);
		coursesSpinner.setAdapter(adapterInitialList);
		coursesSpinner.setEnabled(false);
		// coursesSpinner.setActivated(false);

		Button button_ok = (Button) fview.findViewById(R.id.button_ok4Course);
		Button button_cancel = (Button) fview
				.findViewById(R.id.button_annulla4Course);
		button_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getSherlockActivity().onBackPressed();
			}
		});
		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				eventoModificato.setType(title.getText().toString());
				eventoModificato.setPersonalDescription(description.getText()
						.toString());
				eventoModificato.setEventoId(eId);

				new ChangeEvent(getSherlockActivity()).execute();
				getSherlockActivity().onBackPressed();
			}
		});

	}

	public void updateDisplay() {
		this.mPickDate.setText(new StringBuilder()

		.append(mDay).append("-").append(mMonth + 1).append("-").append(mYear)
				.append(" "));
		if (minute < 10) {
			this.mPickTime.setText(new StringBuilder().append(hour)
					.append(":0").append(minute));
		} else {
			this.mPickTime.setText(new StringBuilder().append(hour).append(":")
					.append(minute));

		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.test, menu);
		super.onCreateOptionsMenu(menu, inflater);
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
			((EditText) fview.findViewById(R.id.myDatePickerButton4Course))
			// Month is 0 based so add 1
					.setText(day + "-" + (month + 1) + "-" + year);
			date.setYear(year - 1900);
			date.setMonth(month);
			date.setDate(day);

			eId.setDate(date);
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
				((EditText) fview.findViewById(R.id.myTimePickerButton4Course))
						.setText(hourOfDay + ":0" + minute);
			} else {
				((EditText) fview.findViewById(R.id.myTimePickerButton4Course))
						.setText(hourOfDay + ":" + minute);

			}
			hour = hourOfDay;
			EditEventFragment.this.minute = minute;

			eId.setStart(new Time(hour, EditEventFragment.this.minute, 0));
			eId.setStop(new Time(hour, EditEventFragment.this.minute, 0));
		}
	}

	private class ChangeEvent extends AsyncTask<Evento, Void, Boolean> {

		public ProgressDialog pd;
		public ProtocolCarrier mProtocolCarrier;
		private Context context;

		public ChangeEvent(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(getSherlockActivity());
			pd = ProgressDialog.show(getSherlockActivity(), "", getResources()
					.getString(R.string.dialog_loading));
		}

		private boolean changeEvent(long date, long from, long to) {
			mProtocolCarrier = new ProtocolCarrier(context,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS
							.POST_WS_CHANGE_PERSONAL_EVENT(date, from, to));
			request.setMethod(Method.POST);

			MessageResponse response;
			try {
				String evJSON = Utils.convertToJSON(eventoModificato);
				request.setBody(evJSON);
				System.out.println(evJSON);
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {
					String body = response.getBody();

					Boolean result = Utils.convertJSONToObject(body,
							Boolean.class);
					if (result)
						return true;
					else
						return false;

				} else {
					return false;
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

			return true;
		}

		@Override
		protected Boolean doInBackground(Evento... params) {
			return changeEvent(dateInitial, timeFromInitial, timeToInitial);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pd.dismiss();
			if (result)
				Toast.makeText(
						context,
						context.getResources().getString(
								R.string.event_change_success),
						Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(
						context,
						context.getResources().getString(R.string.dialog_error),
						Toast.LENGTH_SHORT).show();
		}

	}

}
