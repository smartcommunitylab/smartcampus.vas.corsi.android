package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.MyDate;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.studymate.R;

public class Add_attivita_studio_activity extends FragmentActivity {
	private ProtocolCarrier mProtocolCarrier;
	public String body;
	public AttivitaDiStudio nuova_attivitaStudio = new AttivitaDiStudio();
	GruppoDiStudio gds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_attivita_studio_activity);

		Bundle myextras = getIntent().getExtras();
		gds = (GruppoDiStudio) myextras.getSerializable("gds");

		ActionBar actionbar = getActionBar();

		actionbar.setTitle("Nuova attività di studio");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		// retrieving actual data and time
		final Calendar c = Calendar.getInstance();
		final int mYear = c.get(Calendar.YEAR);
		final int mMonth = c.get(Calendar.MONTH);
		final int mDay = c.get(Calendar.DAY_OF_MONTH);
		final int mMinute = c.get(Calendar.MINUTE);
		final int mHour = c.get(Calendar.HOUR_OF_DAY);

		ArrayList<String> edifici_values = new ArrayList<String>();
		edifici_values.add("Povo, polo Ferraris");
		edifici_values.add("Povo, polo 0");
		edifici_values.add("Povo, nuovo polo");

		Spinner spinner_edificio = (Spinner) findViewById(R.id.spinner_edificio);
		ArrayAdapter<String> adapter_spinner_ed = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, edifici_values);
		spinner_edificio.setAdapter(adapter_spinner_ed);

		ArrayList<String> room_values = new ArrayList<String>();
		for (int i = 101; i < 115; i++) {
			room_values.add("a" + i);
		}
		for (int i = 201; i < 215; i++) {
			room_values.add("a" + i);
		}

		Spinner spinner_aula = (Spinner) findViewById(R.id.spinner_aula);
		ArrayAdapter<String> adapter_spinner_aule = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, room_values);
		spinner_aula.setAdapter(adapter_spinner_aule);

		// retrieving & initializing some button
		Button btn_data = (Button) findViewById(R.id.data_button_gds);
		Button btn_time = (Button) findViewById(R.id.ora_button_gds);

		if (mMinute < 10) {
			if (mHour < 10) {// minute and hour<10
				btn_time.setText("0" + mHour + ":0" + mMinute);
			} else
				// onlyminute<10
				btn_time.setText(mHour + ":0" + mMinute);
		} else {
			if (mHour < 10) {// only hour<10
				btn_time.setText("0" + mHour + ":" + mMinute);
			} else
				// minute and hour>10
				btn_time.setText(mHour + ":" + mMinute);
		}
		MyDate date = new MyDate(mYear, mMonth, mDay);
		btn_data.setText(date.toString());

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
			 * crea e aggiugni agli impegni l'attivit� di studio appena creata
			 */
			String oggetto = ((TextView) this
					.findViewById(R.id.editText_oggetto)).getText().toString();

			String stringdata = ((Button) this
					.findViewById(R.id.data_button_gds)).getText().toString();

			String ora = ((Button) this.findViewById(R.id.ora_button_gds))
					.getText().toString();

			stringdata = stringdata + " " + ora;
			Date data = null;

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			try {
				data = format.parse(stringdata);
				System.out.println(data);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String descrizione = ((TextView) this
					.findViewById(R.id.editText_descrizione_impegno)).getText()
					.toString();

			String room = ((Spinner) Add_attivita_studio_activity.this
					.findViewById(R.id.spinner_aula)).getSelectedItem()
					.toString();

			String edificio = ((Spinner) Add_attivita_studio_activity.this
					.findViewById(R.id.spinner_edificio)).getSelectedItem()
					.toString();

			// boolean prenotazione_aule = ((CheckBox) this
			// .findViewById(R.id.CheckBox1_prenotazione_aule))
			// .isChecked();
			// boolean mensa = ((CheckBox)
			// this.findViewById(R.id.CheckBox2_mensa))
			// .isChecked();
			// boolean tutoring = ((CheckBox) this
			// .findViewById(R.id.CheckBox3_tutoring)).isChecked();
			// boolean biblioteca = ((CheckBox) this
			// .findViewById(R.id.CheckBox4_biblioteca)).isChecked();

			nuova_attivitaStudio.setTitle(oggetto);
			// Date data = new Date();
			if (data != null) {
				nuova_attivitaStudio.getEventoId().setDate(data);
			}

			// nuova_attivitaStudio.setStart(start);
			nuova_attivitaStudio.setRoom(edificio + " - " + room);
			// nuova_attivitaStudio.setEvent_location(edificio);
			nuova_attivitaStudio.setTopic(descrizione);

			nuova_attivitaStudio.setGruppo(gds.getId());

			// nuova_attivitaStudio.setPrenotazione_aule(prenotazione_aule);
			// nuova_attivitaStudio.setMensa(mensa);
			// nuova_attivitaStudio.setTutoring(tutoring);
			// nuova_attivitaStudio.setBiblioteca(biblioteca);

			// MyApplication.getContextualCollection().add(nuova_attivitaStudio);

			AddAttivitaHandler addAttivitaAsyncTask = new AddAttivitaHandler(
					Add_attivita_studio_activity.this);
			addAttivitaAsyncTask.execute();

			return true;
		}
		case android.R.id.home: {
			Add_attivita_studio_activity.this.finish();
			return true;
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

	@SuppressLint("ValidFragment")
	final class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			String phrase_date = (String) ((Button) Add_attivita_studio_activity.this
					.findViewById(R.id.data_button_gds)).getText();

			// MyDate data = MyDate.parseFromString(phrase_date);
			// int mDay = data.getDay();
			// int mMonth = data.getMonth();
			// int mYear = data.getYear();

			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int mYear = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH);
			int mDay = c.get(Calendar.DAY_OF_MONTH);
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, mYear, mMonth,
					mDay);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			Button b = (Button) Add_attivita_studio_activity.this
					.findViewById(R.id.data_button_gds);
			// MyDate date = new MyDate(year, month, day);
			b.setText("" + day + "/" + month + "/" + year);
			// b.refreshDrawableState();

		}
	}

	@SuppressLint("ValidFragment")
	final class TimePickerFragment extends DialogFragment implements
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

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			Button b = (Button) Add_attivita_studio_activity.this
					.findViewById(R.id.ora_button_gds);
			if (minute < 10) {
				if (hourOfDay < 10) {
					b.setText("0" + hourOfDay + ":0" + minute);
				} else
					b.setText(hourOfDay + ":0" + minute);
			} else {
				if (hourOfDay < 10) {
					b.setText("0" + hourOfDay + ":" + minute);
				} else
					b.setText(hourOfDay + ":" + minute);
			}
			// b.refreshDrawableState();

		}
	}

	private class AddAttivitaHandler extends AsyncTask<Void, Void, Boolean> {

		Context taskcontext;
		public ProgressDialog pd;

		public AddAttivitaHandler(Context taskcontext) {
			super();
			this.taskcontext = taskcontext;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					"Salvataggio del nuovo impegno", "Caricamento...");
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			pd.dismiss();
			Add_attivita_studio_activity.this.finish();
			// Intent intent = new Intent(Add_attivita_studio_activity.this,
			// Overview_GDS.class);
			// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivity(intent);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return resultPost;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			addAttivitaonweb();
			return null;
		}

	}

}
