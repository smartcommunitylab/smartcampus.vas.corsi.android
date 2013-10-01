package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.Calendar;

import smartcampus.android.template.standalone.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class Add_attivita_studio_activity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_attivita_studio_activity);

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

		// retrieving & initializing some button
		Button btn_data = (Button) findViewById(R.id.data_button);
		Button btn_time = (Button) findViewById(R.id.ora_button);

		btn_data.setText(mDay + "/" + mMonth + "/" + mYear);
		if (mMinute < 10) {
			btn_time.setText(mHour + ":0" + mMinute);
		}
		btn_time.setText(mHour + ":" + mMinute);

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
			Toast.makeText(getApplicationContext(), "attività studio creata",
					Toast.LENGTH_SHORT).show();
			Add_attivita_studio_activity.this.finish();
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
					.findViewById(R.id.data_button);
			b.setText(day + "/" + month + "/" + year);
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
					.findViewById(R.id.ora_button);
			if (minute < 10) {
				b.setText(hourOfDay + ":0" + minute);
			}
			b.setText(hourOfDay + ":" + minute);
			// b.refreshDrawableState();

		}
	}

}