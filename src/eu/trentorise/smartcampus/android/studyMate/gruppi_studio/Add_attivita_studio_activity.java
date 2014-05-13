package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import it.smartcampuslab.studymate.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.models.EventoId;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;

public class Add_attivita_studio_activity extends FragmentActivity {
	private ProtocolCarrier mProtocolCarrier;
	public String body;
	public Evento nuova_attivitaStudio = new Evento();
	GruppoDiStudio gds;
	private EditText etLocation;
	private EditText mPickDate;
	private EditText mPickTime;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int hour;
	private int minute;
	Spinner coursesSpinner;
	private EventoId eId;
	private Date date;
	private EditText description, editTextTitleGDS;
	@SuppressWarnings("unused")
	private EditText eventlocation;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_attivita_studio_activity);

		editTextTitleGDS = (EditText) findViewById(R.id.editTextTitleGDS);
		Bundle myextras = getIntent().getExtras();
		gds = (GruppoDiStudio) myextras.getSerializable(Constants.GDS);
		ActionBar actionbar = getActionBar();

		actionbar.setTitle(R.string.new_att_stud);
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		// evento = new Evento();
		eId = new EventoId();
		date = new Date();

		// retrieving actual data and time
		final Calendar c = Calendar.getInstance();

		etLocation = (EditText) findViewById(R.id.editText_eventlocation);
		// retrieving & initializing some button
		mPickDate = (EditText) findViewById(R.id.myDatePickerButton);
		mPickTime = (EditText) findViewById(R.id.myTimePickerButton);
		mPickDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePickerDialog();
			}
		});

		mPickTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showTimePickerDialog();
			}
		});

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
		description = (EditText) findViewById(R.id.editTextDescription);
		coursesSpinner = (Spinner) findViewById(R.id.spinnerCorsi);
		eventlocation = (EditText) findViewById(R.id.editText_eventlocation);
		List<String> course = new ArrayList<String>();
		course.add(new String(gds.getMateria()));
		ArrayAdapter<String> adapterCourse = new ArrayAdapter<String>(
				Add_attivita_studio_activity.this,
				R.layout.list_studymate_row_list_simple, course);
		coursesSpinner.setAdapter(adapterCourse);
		coursesSpinner.setClickable(false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.add_attivita_studio_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_done: {
			/*
			 * crea e aggiugni agli impegni l'attività di studio appena creata
			 */

			description = (EditText) findViewById(R.id.editTextDescription);
			coursesSpinner = (Spinner) findViewById(R.id.spinnerCorsi);

			mPickDate = (EditText) findViewById(R.id.myDatePickerButton);
			mPickTime = (EditText) findViewById(R.id.myTimePickerButton);

			String stringdata = mPickDate.getText().toString();
			String ora = mPickTime.getText().toString();

			stringdata = stringdata + " " + ora;
			Date data = null;

			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			try {
				data = format.parse(stringdata);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}

			String location = etLocation.getText().toString();
			String name = editTextTitleGDS.getText().toString();
			if (name.equals("")) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.no_att_name),
						Toast.LENGTH_SHORT).show();
				return false;
			}
			nuova_attivitaStudio.setTitle(name);// gds.getMateria()
			// Date data = new Date();
			if (data != null) {
				EventoId eventoId = new EventoId();
				long dateR = 10000 * (date.getTime() / 10000);
				eventoId.setDate(new Date(dateR));
				Time time = new Time(data.getTime());
				eventoId.setStart(time);
				eventoId.setIdEventAd(-2);
				eventoId.setStop(time);
				// nuova_attivitaStudio.getEventoId().setDate(data);
				nuova_attivitaStudio.setEventoId(eventoId);
			}

			// nuova_attivitaStudio.setStart(start);
			nuova_attivitaStudio.setRoom(location);
			// nuova_attivitaStudio.setEvent_location(edificio);
			nuova_attivitaStudio.setPersonalDescription(description.getText()
					.toString());
			nuova_attivitaStudio.setType(getResources().getString(
					R.string.attivitadistudio_string));

			nuova_attivitaStudio.setGruppo(gds);

			AddAttivitaHandler addAttivitaAsyncTask = new AddAttivitaHandler(
					Add_attivita_studio_activity.this);
			addAttivitaAsyncTask.execute();

			return false;
		}
		case android.R.id.home: {
			Add_attivita_studio_activity.this.finish();
			return false;
		}

		default:
			return super.onOptionsItemSelected(item);
		}
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
			return new DatePickerDialog(Add_attivita_studio_activity.this,
					this, mYear, mMonth, mDay);
		}

		@SuppressWarnings("deprecation")
		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user;
			((EditText) findViewById(R.id.myDatePickerButton))
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
			return new TimePickerDialog(Add_attivita_studio_activity.this,
					this, hour, minute,
					DateFormat
							.is24HourFormat(Add_attivita_studio_activity.this));
		}

		@SuppressWarnings("deprecation")
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			if (minute < 10) {
				((EditText) findViewById(R.id.myTimePickerButton))
						.setText(hourOfDay + ":0" + minute);
			} else {
				((EditText) findViewById(R.id.myTimePickerButton))
						.setText(hourOfDay + ":" + minute);

			}
			eId.setStart(new Time(hourOfDay, minute, 0));
			eId.setStop(new Time(hourOfDay, minute, 0));
		}
	}

	private class AddAttivitaHandler extends AsyncTask<Void, Void, Boolean> {

		Context taskcontext;
		public ProgressDialog pd;

		public AddAttivitaHandler(Context taskcontext) {

			this.taskcontext = taskcontext;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					getResources().getString(R.string.dialog_saving_feedback),
					getResources().getString(R.string.dialog_loading));
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			pd.dismiss();
			Toast.makeText(
					taskcontext,
					taskcontext.getResources().getString(
							R.string.toast_event_added), Toast.LENGTH_SHORT)
					.show();
			// Add_attivita_studio_activity.this.finish();
			Intent intent = new Intent(Add_attivita_studio_activity.this,
					Overview_GDS.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra(Constants.CONTESTUAL_GDS, gds);
			startActivity(intent);
		}

		private boolean addAttivitaonweb() {
			mProtocolCarrier = new ProtocolCarrier(
					Add_attivita_studio_activity.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageResponse response;

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.POST_ATTIVITASTUDIO_ADD);
			request.setMethod(Method.POST);

			Boolean resultPost = false;

			try {

				String AttivitaJSON = Utils.convertToJSON(nuova_attivitaStudio);
				System.out.println(AttivitaJSON);
				request.setBody(AttivitaJSON);
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {

					body = response.getBody();
					resultPost = Utils.convertJSONToObject(body, Boolean.class);

				}

			} catch (ConnectionException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (AACException e) {
				e.printStackTrace();
			} catch (eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException e) {
				e.printStackTrace();
			}

			return resultPost;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			addAttivitaonweb();

			return null;
		}

	}

	public void showDatePickerDialog() {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	public void showTimePickerDialog() {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getSupportFragmentManager(), "timePicker");
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

}
