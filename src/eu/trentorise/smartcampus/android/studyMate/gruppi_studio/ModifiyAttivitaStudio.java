package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.sql.Time;
import java.text.SimpleDateFormat;
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
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.Menu;
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
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.studymate.R;

public class ModifiyAttivitaStudio extends FragmentActivity {
	private ProtocolCarrier mProtocolCarrier;
	private EditText etLocation;
	private EditText mPickDate;
	private EditText mPickTime;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mMinute;
	private int mHour;
	private int hour;
	private int minute;
	private Evento evento = null;
	Spinner coursesSpinner;
	private EventoId eId;
	private Date date;
	private EditText description;
	private EditText eventlocation;
	private EditText descrizione_tv;
	private Evento eventoModificato;
	private long dateInitial;
	private long timeFromInitial;
	private long timeToInitial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_attivita_studio_activity);

		// personalizzazioje actionabar
		android.app.ActionBar actionbar = getActionBar();
		actionbar.setTitle(R.string.mod_att);
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		// recupero gds da modificare per impostare i campi di testo ecc da
		// modificare con i valori preesistenti dell'attivitadistudio
		Bundle myextras = getIntent().getExtras();
		evento = (Evento) myextras.getSerializable(Constants.IMPEGNO_MOD);
		dateInitial = evento.getEventoId().getDate().getTime();
		timeFromInitial = evento.getEventoId().getStart().getTime();
		timeToInitial = evento.getEventoId().getStop().getTime();

		eId = new EventoId();
		eventoModificato = evento;
		date = new Date();
		mPickDate = (EditText) findViewById(R.id.myDatePickerButton);
		mPickDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePickerDialog();

			}
		});
		mPickTime = (EditText) findViewById(R.id.myTimePickerButton);
		mPickTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showTimePickerDialog();

			}
		});

		// get the ex date of previous event
		mYear = evento.getEventoId().getDate().getYear() + 1900;
		mMonth = evento.getEventoId().getDate().getMonth();
		mDay = evento.getEventoId().getDate().getDate();
		// get the current Time
		hour = evento.getEventoId().getStart().getHours();
		minute = evento.getEventoId().getStart().getMinutes();
		eId.setStart(new Time(hour, minute, 0));
		eId.setStop(new Time(hour, minute, 0));
		// display the current date
		updateDisplay();

		description = (EditText) findViewById(R.id.editTextDescription);
		coursesSpinner = (Spinner) findViewById(R.id.spinnerCorsi);
		description.setText(evento.getPersonalDescription());
		// retrieving textview_oggetto
		List<String> course = new ArrayList<String>();
		course.add(new String(evento.getTitle()));
		ArrayAdapter<String> adapterCourse = new ArrayAdapter<String>(
				ModifiyAttivitaStudio.this,
				R.layout.list_studymate_row_list_simple, course);
		coursesSpinner.setAdapter(adapterCourse);
		coursesSpinner.setClickable(false);
		etLocation = (EditText) findViewById(R.id.editText_eventlocation);
		etLocation.setText(evento.getRoom());
		descrizione_tv = (EditText) this.findViewById(R.id.editTextDescription);
		descrizione_tv.setText(evento.getPersonalDescription());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		android.view.MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.modifiy_attivita_studio, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {

			ModifiyAttivitaStudio.this.finish();
			return super.onOptionsItemSelected(item);

		}
		case R.id.action_done: {
			/*
			 * recupero elementi grafici
			 */

			mPickDate = (EditText) findViewById(R.id.myDatePickerButton);
			mPickTime = (EditText) findViewById(R.id.myTimePickerButton);

			/*
			 * recupero informazioni dagli elementi grafici e aggiornamento di
			 * attivitaDiStudio
			 */
			String location = etLocation.getText().toString();

			// String descrizione = descrizione_tv.getText().toString();

			String stringdata = mPickDate.getText().toString();
			String ora = mPickTime.getText().toString();
			stringdata = stringdata + " " + ora;
			Date data = null;
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			try {
				data = format.parse(stringdata);
				System.out.println(data);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}

			/*
			 * salvataggio modifche in attivitaDiStudio
			 */

			eventoModificato.setTitle(evento.getTitle());
			// Date data = new Date();
			if (data != null) {
				EventoId eventoId = new EventoId();
				long dateR = 10000 * (date.getTime() / 10000);
				eventoId.setDate(new Date(dateR));
				eventoModificato.setEventoId(eventoId);
				Time time = new Time(data.getTime());
				eventoId.setStart(time);
				eventoId.setStop(time);
				// nuova_attivitaStudio.getEventoId().setDate(data);
				eventoModificato.setEventoId(eventoId);
			}

			// nuova_attivitaStudio.setStart(start);
			eventoModificato.setRoom(location);
			// nuova_attivitaStudio.setEvent_location(edificio);
			eventoModificato.setPersonalDescription(descrizione_tv.getText()
					.toString());
			eventoModificato.setGruppo(evento.getGruppo());

			ModifyAS salvamodificheAS = new ModifyAS(
					ModifiyAttivitaStudio.this, evento, eventoModificato);
			salvamodificheAS.execute();
			return super.onOptionsItemSelected(item);
		}
		default:
			return super.onOptionsItemSelected(item);
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
			return new DatePickerDialog(ModifiyAttivitaStudio.this, this,
					mYear, mMonth, mDay);
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

			eId.setDate(date);

			eventoModificato.setEventoId(eId);
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
			return new TimePickerDialog(ModifiyAttivitaStudio.this, this, hour,
					minute,
					DateFormat.is24HourFormat(ModifiyAttivitaStudio.this));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			if (minute < 10) {
				((EditText) findViewById(R.id.myTimePickerButton))
						.setText(hourOfDay + ":0" + minute);
			} else {
				((EditText) findViewById(R.id.myTimePickerButton))
						.setText(hourOfDay + ":" + minute);

			}
			hour = hourOfDay;
			ModifiyAttivitaStudio.this.minute = minute;
		}
	}

	private class ModifyAS extends AsyncTask<Void, Void, Boolean> {
		Context taskcontext;
		public ProgressDialog pd;
		Boolean allright;
		Evento oldone, newone;

		public ModifyAS(Context taskcontext, Evento oldone, Evento newone) {
			super();
			this.taskcontext = taskcontext;
			this.oldone = oldone;
			this.newone = newone;
			System.out.println("date: "
					+ oldone.getEventoId().getDate().getTime() + "\nstart: "
					+ oldone.getEventoId().getStart().getTime() + "\nstop: "
					+ oldone.getEventoId().getStop().getTime());
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					getResources().getString(R.string.dialog_saving_feedback),
					"");
		}

		private boolean modificaAS() {
			mProtocolCarrier = new ProtocolCarrier(ModifiyAttivitaStudio.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageResponse response;

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.POST_WS_CHANGE_ATTIVITASTUDIO(dateInitial,
							timeFromInitial, timeToInitial));
			request.setMethod(Method.POST);

			Boolean resultPost = false;

			try {

				String AttivitaJSON = Utils.convertToJSON(newone);
				request.setBody(AttivitaJSON);

				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {

					String body = response.getBody();
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
			allright = modificaAS();
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pd.dismiss();
			if (allright) {
				Intent intent = new Intent(ModifiyAttivitaStudio.this,
						ShowImpegnoGDS.class);
				intent.putExtra(Constants.CONTEXTUAL_ATT, newone);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			} else {
				Toast.makeText(ModifiyAttivitaStudio.this,
						getResources().getString(R.string.dialog_error),
						Toast.LENGTH_SHORT).show();
				ModifiyAttivitaStudio.this.finish();
			}

		}

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

}
