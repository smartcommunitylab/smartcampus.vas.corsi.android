package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.EventoId;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.studymate.R;

public class ModifiyAttivitaStudio extends FragmentActivity {
	private AttivitaDiStudio attivitaDiStudioOld;
	private ProtocolCarrier mProtocolCarrier;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_attivita_studio_activity);

		// personalizzazioje actionabar
		android.app.ActionBar actionbar = getActionBar();
		actionbar.setTitle("Modifica impegno");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		// recupero gds da modificare per impostare i campi di testo ecc da
		// modificare con i valori preesistenti dell'attivitadistudio
		Bundle myextras = getIntent().getExtras();
		attivitaDiStudioOld = (AttivitaDiStudio) myextras
				.getSerializable("impegno_da_modificare");

		// customizzazione del layout di questa activity. Si vuole mostrare
		// nelle view disponibili i dati contenuti nella attivitadistudio che
		// l'utente vuole modificare

		// customizzazzione spinner: riempimento
		ArrayList<String> edifici_values = new ArrayList<String>();
		edifici_values.add("Povo, polo Ferraris");
		edifici_values.add("Povo, polo 0");
		edifici_values.add("Povo, nuovo polo");

		ArrayList<String> room_values = new ArrayList<String>();
		for (int i = 101; i < 115; i++) {
			room_values.add("a" + i);
		}
		for (int i = 201; i < 215; i++) {
			room_values.add("a" + i);
		}

		// customizzazzione spinner: setting up (riposizionare gli spinner su
		// vecchia scelta di edificio e aula)
		// se non si torna ad avere una
		// event.location non serve a niente fare la ricerca and selection degli
		// spinner perchè non si distinguono più edificio=location da aula=room

		Spinner spinner_edificio = (Spinner) findViewById(R.id.spinner_edificio);
		ArrayAdapter<String> adapter_spinner_ed = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, edifici_values);
		adapter_spinner_ed
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_edificio.setAdapter(adapter_spinner_ed);

		// String location_actual = attivitaDiStudio.getRoom();
		// int spinnerPositionedificio = adapter_spinner_ed
		// .getPosition(location_actual);
		// spinner_edificio.setSelection(spinnerPositionedificio);

		Spinner spinner_aula = (Spinner) findViewById(R.id.spinner_aula);
		ArrayAdapter<String> adapter_spinner_aule = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, room_values);
		adapter_spinner_aule
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_aula.setAdapter(adapter_spinner_aule);

		// String room_actual = attivitaDiStudio.getRoom();
		// int spinnerPositionaula =
		// adapter_spinner_aule.getPosition(room_actual);
		// spinner_edificio.setSelection(spinnerPositionaula);

		// retrieving & initializing some button
		Button btn_data = (Button) findViewById(R.id.data_button_gds);
		Button btn_time = (Button) findViewById(R.id.ora_button_gds);

		Date data = attivitaDiStudioOld.getEventoId().getDate();
		SimpleDateFormat formatgiornoanno = new SimpleDateFormat("dd/MM/yyyy");
		btn_data.setText(formatgiornoanno.format(data));

		SimpleDateFormat formatorariogiornata = new SimpleDateFormat("HH:mm");
		btn_time.setText(formatorariogiornata.format(data));

		// retrieving textview_oggetto
		TextView oggetto_tv = (TextView) this
				.findViewById(R.id.editText_oggetto);
		oggetto_tv.setText(attivitaDiStudioOld.getTitle());

		TextView descrizione_tv = (TextView) this
				.findViewById(R.id.editText_descrizione_impegno);
		descrizione_tv.setText(attivitaDiStudioOld.getTopic());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
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
			Spinner spinner_edificio = (Spinner) findViewById(R.id.spinner_edificio);
			Spinner spinner_aula = (Spinner) findViewById(R.id.spinner_aula);
			Button btn_data = (Button) findViewById(R.id.data_button_gds);
			Button btn_time = (Button) findViewById(R.id.ora_button_gds);
			TextView oggetto_tv = (TextView) this
					.findViewById(R.id.editText_oggetto);
			TextView descrizione_tv = (TextView) this
					.findViewById(R.id.editText_descrizione_impegno);
			/*
			 * recupero informazioni dagli elementi grafici e aggiornamento di
			 * attivitaDiStudio
			 */
			String edificio = spinner_edificio.getSelectedItem().toString();
			String aula = spinner_aula.getSelectedItem().toString();

			String oggetto = oggetto_tv.getText().toString();
			String descrizione = descrizione_tv.getText().toString();

			String stringdata = btn_data.getText().toString();
			String ora = btn_time.getText().toString();
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

			/*
			 * salvataggio modifche in attivitaDiStudio
			 */
			AttivitaDiStudio newattivitaDiStudio = new AttivitaDiStudio();

			newattivitaDiStudio.setTitle(oggetto);
			// Date data = new Date();
			if (data != null) {
				EventoId eventoId = new EventoId();
				long dateR = 10000 * (data.getTime() / 10000);
				eventoId.setDate(new Date(dateR));
				newattivitaDiStudio.setEventoId(eventoId);
				Time time = new Time(data.getTime());
				eventoId.setStart(time);
				eventoId.setStop(time);
				// nuova_attivitaStudio.getEventoId().setDate(data);
				newattivitaDiStudio.setEventoId(eventoId);
			}

			// nuova_attivitaStudio.setStart(start);
			newattivitaDiStudio.setRoom(edificio + " - " + aula);
			// nuova_attivitaStudio.setEvent_location(edificio);
			newattivitaDiStudio.setTopic(descrizione);
			newattivitaDiStudio.setGruppo(attivitaDiStudioOld.getGruppo());

			ModifyAS salvamodificheAS = new ModifyAS(
					ModifiyAttivitaStudio.this, attivitaDiStudioOld,
					newattivitaDiStudio);
			salvamodificheAS.execute();
			return super.onOptionsItemSelected(item);
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

			String phrase_date = (String) ((Button) ModifiyAttivitaStudio.this
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
			Button b = (Button) ModifiyAttivitaStudio.this
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
			Button b = (Button) ModifiyAttivitaStudio.this
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

	private class ModifyAS extends AsyncTask<Void, Void, Boolean> {
		Context taskcontext;
		public ProgressDialog pd;
		Boolean allright;
		AttivitaDiStudio oldone, newone;

		public ModifyAS(Context taskcontext, AttivitaDiStudio oldone,
				AttivitaDiStudio newone) {
			super();
			this.taskcontext = taskcontext;
			this.oldone = oldone;
			this.newone = newone;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					"Salvataggio modifiche in corso", "...");
		}

		private boolean modificaAS() {
			mProtocolCarrier = new ProtocolCarrier(ModifiyAttivitaStudio.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageResponse response;

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.POST_WS_CHANGE_ATTIVITASTUDIO(oldone
							.getEventoId().getDate().getTime(), oldone
							.getEventoId().getStart().getTime(), oldone
							.getEventoId().getStop().getTime()));
			request.setMethod(Method.POST);

			Boolean resultPost = false;

			try {

				String AttivitaJSON = Utils.convertToJSON(newone);
				System.out
						.println("Il json dell'attività di studio che sto modificando è: "
								+ AttivitaJSON);
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
			allright = modificaAS();
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			if (allright) {
				ModifiyAttivitaStudio.this.finish();
			} else {
				// merda
				Toast.makeText(ModifiyAttivitaStudio.this,
						"errore nella modifica dell'AS", Toast.LENGTH_SHORT)
						.show();
				ModifiyAttivitaStudio.this.finish();
			}

		}

	}

}
