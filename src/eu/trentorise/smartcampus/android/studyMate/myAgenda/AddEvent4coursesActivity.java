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
import android.content.Intent;
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

import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.models.EventoId;
import eu.trentorise.smartcampus.android.studyMate.utilities.PostEvent;
import eu.trentorise.smartcampus.studymate.R;

public class AddEvent4coursesActivity extends SherlockFragment {
	private int mYear;
	private int mMonth;
	private int mDay;

	private int hour;
	private int minute;

	// private TextView mDateDisplay;
	private EditText mPickDate;
	private EditText mPickTime;
	static final int DATE_DIALOG_ID = 0;
	private View fview;
	public static ProgressDialog pd;
	private Evento evento = null;
	// public CorsoCarriera courseSelected;
	Spinner coursesSpinner;
	String cN;
	private CorsoCarriera cc;
	private EditText title;
	private EditText description;
	private EventoId eId;
	private Date date;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fview = inflater.inflate(R.layout.activity_add_event_4_course,
				container, false);
		cc = (CorsoCarriera) getArguments().getSerializable("ccSelected");
		return fview;
	}

	// @SuppressWarnings("deprecation")
	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.activity_add_event_4_course);
	//
	// }

	@Override
	public void onStart() {

		super.onStart();

		// Intent intent = getIntent();
		// cc = (CorsoCarriera) intent.getSerializableExtra("corsoCarrieraS");
		evento = new Evento();
		eId = new EventoId();
		date = new Date();
		mPickDate = (EditText) fview
				.findViewById(R.id.myDatePickerButton4Course);
		mPickTime = (EditText) fview
				.findViewById(R.id.myTimePickerButton4Course);
		mPickDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePickerDialog(fview);
				// TODO Auto-generated method stub

			}
		});

		mPickTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showTimePickerDialog(fview);
				// TODO Auto-generated method stub

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

		title = (EditText) fview.findViewById(R.id.editTextTitle4Course);
		description = (EditText) fview
				.findViewById(R.id.editTextDescription4Course);
		coursesSpinner = (Spinner) fview.findViewById(R.id.spinnerCorsi4Course);
		List<String> resultStrings = new ArrayList<String>();
		resultStrings.add(cc.getName());
		ArrayAdapter<String> adapterInitialList = new ArrayAdapter<String>(
				getActivity(), R.layout.list_studymate_row_list_simple,
				resultStrings);
		coursesSpinner.setAdapter(adapterInitialList);
		coursesSpinner.setEnabled(false);
		coursesSpinner.setActivated(false);

		Button button_ok = (Button) fview.findViewById(R.id.button_ok4Course);
		Button button_cancel = (Button) fview
				.findViewById(R.id.button_annulla4Course);
		button_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});
		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				evento.setType(title.getText().toString());
				evento.setTitle(cc.getName());
				// evento.setTeacher("IO");
				evento.setPersonalDescription(description.getText().toString());
				evento.setEventoId(eId);
				evento.setAdCod(Long.parseLong(cc.getCod()));

				long dateR = 10000*(date.getTime()/10000);
				eId.setDate(new Date(dateR));
				new PostEvent(getActivity(), evento).execute();
				Toast.makeText(getActivity(), "Evento aggiunto",
						Toast.LENGTH_SHORT).show();
				getActivity().onBackPressed();
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

	// @Override
	// public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu)
	// {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getSupportMenuInflater().inflate(R.menu.test, menu);
	// return super.onCreateOptionsMenu(menu);
	// }

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getActivity().getSupportFragmentManager(),
				"datePicker");
	}

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getActivity().getSupportFragmentManager(),
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
			return new DatePickerDialog(getActivity(), this, mYear, mMonth,
					mDay);
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
				((EditText) fview.findViewById(R.id.myTimePickerButton4Course))
						.setText(hourOfDay + ":0" + minute);
			} else {
				((EditText) fview.findViewById(R.id.myTimePickerButton4Course))
						.setText(hourOfDay + ":" + minute);

			}
			eId.setStart(new Time(hourOfDay, minute, 0));
			eId.setStop(new Time(hourOfDay, minute, 0));
		}
	}

}
