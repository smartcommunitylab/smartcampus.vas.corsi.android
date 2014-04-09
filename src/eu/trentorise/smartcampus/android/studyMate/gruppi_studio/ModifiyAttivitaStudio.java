package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
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
import eu.trentorise.smartcampus.studymate.R;

public class ModifiyAttivitaStudio extends FragmentActivity {
	private Evento attivitaDiStudioOld;
	private ProtocolCarrier mProtocolCarrier;
	private EditText etLocation;
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
		attivitaDiStudioOld = (Evento) myextras
				.getSerializable("impegno_da_modificare");
		etLocation = (EditText) findViewById(R.id.editText_location_impegno);
		etLocation.setText(attivitaDiStudioOld.getPersonalDescription());

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
		descrizione_tv.setText(attivitaDiStudioOld.getPersonalDescription());
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
			String location = etLocation.getText().toString();

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
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}

			/*
			 * salvataggio modifche in attivitaDiStudio
			 */
			Evento newattivitaDiStudio = new Evento();

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
			newattivitaDiStudio.setRoom(location);
			// nuova_attivitaStudio.setEvent_location(edificio);
			newattivitaDiStudio.setPersonalDescription(descrizione);
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
		Evento oldone, newone;

		public ModifyAS(Context taskcontext, Evento oldone,
				Evento newone) {
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
				intent.putExtra("contextualAttivitaStudio", newone);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

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
