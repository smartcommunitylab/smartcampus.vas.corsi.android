package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import java.util.ArrayList;
import java.util.Calendar;
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
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.CoursesHandler;
import eu.trentorise.smartcampus.android.studyMate.utilities.PostEvent;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

public class AddEvent4coursesActivity extends SherlockFragmentActivity {
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
	private Evento evento = null;
	//public CorsoCarriera courseSelected;
	Spinner coursesSpinner;
	String cN;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event_4_course);
		mPickDate = (EditText) findViewById(R.id.myDatePickerButton4Course);
		mPickTime = (EditText) findViewById(R.id.myTimePickerButton4Course);
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

		@SuppressWarnings("unused")
		EditText title = (EditText) findViewById(R.id.editTextTitle4Course);
		@SuppressWarnings("unused")
		EditText description = (EditText) findViewById(R.id.editTextDescription4Course);
		coursesSpinner = (Spinner) findViewById(R.id.spinnerCorsi4Course);
		new CoursesLoader().execute();

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
				new PostEvent(getApplicationContext(), evento).execute();
				Toast.makeText(getApplicationContext(), "Evento aggiunto", Toast.LENGTH_SHORT).show();
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

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user;
			((EditText) findViewById(R.id.myDatePickerButton4Course))
			// Month is 0 based so add 1
					.setText(day + "-" + (month + 1) + "-" + year);

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

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			if (minute < 10) {
				((EditText) findViewById(R.id.myTimePickerButton4Course))
						.setText(hourOfDay + ":0" + minute);
			} else {
				((EditText) findViewById(R.id.myTimePickerButton4Course))
						.setText(hourOfDay + ":" + minute);

			}
		}
	}

	private class CoursesLoader extends AsyncTask<Void, Void, List<CorsoCarriera>> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		String body;

		@Override
		protected List<CorsoCarriera> doInBackground(Void... params) {
			return getFollowingCourses();
		}

		private List<CorsoCarriera> getFollowingCourses() {
			mProtocolCarrier = new ProtocolCarrier(context,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_FREQUENTEDCOURSES);
			request.setMethod(Method.GET);
			@SuppressWarnings("unused")
			BasicProfile bp = new BasicProfile();
			MessageResponse response;
			try {
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {

					body = response.getBody();

				} else {
					return null;
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

			return Utils.convertJSONToObjects(body, CorsoCarriera.class);
		}

		@Override
		protected void onPostExecute(List<CorsoCarriera> result) {
			super.onPostExecute(result);
			pd.dismiss();

			List<String> resultStrings = new ArrayList<String>();

//			courseSelected = new CorsoCarriera();
//			courseSelected = (CorsoCarriera) CoursesHandler.corsoSelezionato;

			resultStrings.add(OverviewFilterFragment.nomeCorsoOW);//courseSelected.getName());

			ArrayAdapter<String> adapterInitialList = new ArrayAdapter<String>(
					AddEvent4coursesActivity.this,
					R.layout.list_studymate_row_list_simple, resultStrings);

			coursesSpinner.setAdapter(adapterInitialList);
			coursesSpinner.setEnabled(false);
			coursesSpinner.setActivated(false);

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			new ProgressDialog(AddEvent4coursesActivity.this);
			pd = ProgressDialog.show(AddEvent4coursesActivity.this,
					"Caricamento della lista dei corsi ", "Caricamento...");
		}

	}

}
