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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.models.EventoId;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

public class EditEventActivity extends SherlockFragmentActivity {
	private int mYear;
	private int mMonth;
	private int mDay;

	private int hour;
	private int minute;

	// private TextView mDateDisplay;
	private EditText mPickDate;
	private EditText mPickTime;
	static final int DATE_DIALOG_ID = 0;

	public static ProgressDialog pd;
	private Evento eventoModificato;
	private Evento evento;
	// public CorsoCarriera courseSelected;
	Spinner coursesSpinner;
	String cN;
	private EditText title;
	private EditText description;
	private EventoId eId;
	private Date date;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event_4_course);
		Intent intent = getIntent();
		evento = (Evento) intent.getSerializableExtra("modEv");
		eId = new EventoId();
		eventoModificato = evento;
		date = new Date();
		mPickDate = (EditText) findViewById(R.id.myDatePickerButton4Course);
		mPickTime = (EditText) findViewById(R.id.myTimePickerButton4Course);
		// get the ex date of previous event
		mYear = evento.getEventoId().getDate().getYear() + 1900;
		mMonth = evento.getEventoId().getDate().getMonth();
		mDay = evento.getEventoId().getDate().getDay();
		// get the current Time
		hour = evento.getEventoId().getStart().getHours();
		minute = evento.getEventoId().getStart().getMinutes();
		// display the current date
		updateDisplay();
		title = (EditText) findViewById(R.id.editTextTitle4Course);
		title.setText(evento.getType());
		description = (EditText) findViewById(R.id.editTextDescription4Course);
		description.setText(evento.getPersonalDescription());
		coursesSpinner = (Spinner) findViewById(R.id.spinnerCorsi4Course);
		List<String> resultStrings = new ArrayList<String>();
		resultStrings.add(evento.getTitle());
		ArrayAdapter<String> adapterInitialList = new ArrayAdapter<String>(
				EditEventActivity.this,
				R.layout.list_studymate_row_list_simple, resultStrings);
		coursesSpinner.setAdapter(adapterInitialList);
		coursesSpinner.setEnabled(false);
		coursesSpinner.setActivated(false);

	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	@Override
	protected void onStart() {

		super.onStart();
		Button button_ok = (Button) findViewById(R.id.button_ok4Course);
		Button button_cancel = (Button) findViewById(R.id.button_annulla4Course);
		button_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				eventoModificato.setType(title.getText().toString());
				eventoModificato.setPersonalDescription(description.getText()
						.toString());
				eId.setDate(date);
				eventoModificato.setEventoId(eId);
				new ChangeEvent(evento, EditEventActivity.this,
						eventoModificato).execute();
				onBackPressed();
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
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.test, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getSupportFragmentManager(), "timePicker");
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
			return new DatePickerDialog(getActivity(), this, mYear, mMonth,
					mDay);
		}

		@SuppressWarnings("deprecation")
		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user;
			((EditText) findViewById(R.id.myDatePickerButton4Course))
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
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		@SuppressWarnings("deprecation")
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			if (minute < 10) {
				((EditText) findViewById(R.id.myTimePickerButton4Course))
						.setText(hourOfDay + ":0" + minute);
			} else {
				((EditText) findViewById(R.id.myTimePickerButton4Course))
						.setText(hourOfDay + ":" + minute);

			}
			eId.setStart(new Time(hourOfDay, minute, 0));
			eId.setStop(new Time(hourOfDay, minute, 0));
		}
	}

	private class ChangeEvent extends AsyncTask<Evento, Void, Boolean> {

		public ProgressDialog pd;
		public ProtocolCarrier mProtocolCarrier;
		Evento ev;
		Evento evModificato;
		private Context context;

		public ChangeEvent(Evento ev, Context context, Evento evModificato) {
			this.context = context;
			this.ev = ev;
			this.evModificato = evModificato;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(EditEventActivity.this);
			pd = ProgressDialog.show(EditEventActivity.this,
					"Sto aggiornando le modifiche al tuo evento..",
					"Caricamento...");
		}

		private boolean changeEvent(Evento ev, long date, long from, long to) {
			mProtocolCarrier = new ProtocolCarrier(context,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS
							.POST_WS_CHANGE_PERSONAL_EVENT(date, from, to));
			request.setMethod(Method.POST);

			MessageResponse response;
			try {
				String evJSON = Utils.convertToJSON(evModificato);
				request.setBody(evJSON);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return true;
		}

		@Override
		protected Boolean doInBackground(Evento... params) {
			// TODO Auto-generated method stub
			return changeEvent(ev, ev.getEventoId().getDate().getTime(), ev
					.getEventoId().getStart().getTime(), ev.getEventoId()
					.getStop().getTime());
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			if (result)
				Toast.makeText(getApplicationContext(),
						"Evento modificato con successo", Toast.LENGTH_SHORT)
						.show();
			else
				Toast.makeText(getApplicationContext(),
						"Ops! Qualcosa è andato storto.", Toast.LENGTH_SHORT)
						.show();
		}

	}

}
