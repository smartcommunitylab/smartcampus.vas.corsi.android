package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.Calendar;

import smartcampus.android.template.standalone.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaStudio;
import eu.trentorise.smartcampus.android.studyMate.models.MyDate;

public class Add_attivita_studio_activity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_attivita_studio_activity);

		ActionBar actionbar = getActionBar();
		actionbar.setTitle("Nuova attivit� di studio");
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
		// TODO Auto-generated method stub
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
			AttivitaStudio nuova_attivitaStudio = new AttivitaStudio();
			String oggetto = ((TextView) this
					.findViewById(R.id.editText_oggetto)).getText().toString();

			String data = ((Button) this.findViewById(R.id.data_button_gds))
					.getText().toString();
			String ora = ((Button) this.findViewById(R.id.ora_button_gds))
					.getText().toString();

			String descrizione = ((TextView) this
					.findViewById(R.id.editText_descrizione_impegno)).getText()
					.toString();
			boolean prenotazione_aule = ((CheckBox) this
					.findViewById(R.id.CheckBox1_prenotazione_aule))
					.isChecked();
			boolean mensa = ((CheckBox) this.findViewById(R.id.CheckBox2_mensa))
					.isChecked();
			boolean tutoring = ((CheckBox) this
					.findViewById(R.id.CheckBox3_tutoring)).isChecked();
			boolean biblioteca = ((CheckBox) this
					.findViewById(R.id.CheckBox4_biblioteca)).isChecked();

			nuova_attivitaStudio.setOggetto(oggetto);
			nuova_attivitaStudio.setData(data);
			nuova_attivitaStudio.setStart(ora);
			nuova_attivitaStudio.setDescrizione(descrizione);
			nuova_attivitaStudio.setPrenotazione_aule(prenotazione_aule);
			nuova_attivitaStudio.setMensa(mensa);
			nuova_attivitaStudio.setTutoring(tutoring);
			nuova_attivitaStudio.setBiblioteca(biblioteca);

			MyApplication.getContextualCollection().add(nuova_attivitaStudio);

			Toast.makeText(getApplicationContext(), "attività studio creata",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(Add_attivita_studio_activity.this,
					Overview_GDS.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		case android.R.id.home: {
			Add_attivita_studio_activity.this.finish();
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

			MyDate data = MyDate.parseFromString(phrase_date);
			int mDay = data.getDay();
			int mMonth = data.getMonth();
			int mYear = data.getYear();

			// // Use the current date as the default date in the picker
			// final Calendar c = Calendar.getInstance();
			// int mYear = c.get(Calendar.YEAR);
			// int mMonth = c.get(Calendar.MONTH);
			// int mDay = c.get(Calendar.DAY_OF_MONTH);
			// //Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, mYear, mMonth,
					mDay);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			Button b = (Button) Add_attivita_studio_activity.this
					.findViewById(R.id.data_button_gds);
			MyDate date = new MyDate(year, month, day);
			b.setText(date.toString());
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

}