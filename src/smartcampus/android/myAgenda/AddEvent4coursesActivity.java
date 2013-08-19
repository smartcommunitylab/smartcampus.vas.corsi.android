package smartcampus.android.myAgenda;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import smartcampus.android.template.standalone.R;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studyMate.models.Corso;
import eu.trentorise.smartcampus.studyMate.models.CorsoLite;
import eu.trentorise.smartcampus.studyMate.models.Evento;
import eu.trentorise.smartcampus.studyMate.utilities.CoursesHandler;
import eu.trentorise.smartcampus.studyMate.utilities.PostEvent;
import eu.trentorise.smartcampus.studyMate.utilities.SmartUniDataWS;

public class AddEvent4coursesActivity extends FragmentActivity {
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
	public Corso courseSelected;
	Spinner coursesSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event_4_course);
		// mDateDisplay = (TextView) findViewById(R.id.showMyDate);
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
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Button button_ok = (Button) findViewById(R.id.button_ok4Course);

		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new PostEvent(getApplicationContext(), evento).execute();
			}
		});

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
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

	private class CoursesLoader extends AsyncTask<Void, Void, List<CorsoLite>> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		String body;

		@Override
		protected List<CorsoLite> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return getFollowingCourses();
		}

		private List<CorsoLite> getFollowingCourses() {
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
				response = mProtocolCarrier.invokeSync(request,
						SmartUniDataWS.TOKEN_NAME, SmartUniDataWS.TOKEN);

				if (response.getHttpStatus() == 200) {

					body = response.getBody();

				} else {
					return null;
				}
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return Utils.convertJSONToObjects(body, CorsoLite.class);
		}

		@Override
		protected void onPostExecute(List<CorsoLite> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();

			List<String> resultStrings = new ArrayList<String>();
			
			courseSelected = new Corso();
			courseSelected = (Corso) CoursesHandler.corsoSelezionato;
			
			
			
			resultStrings.add(courseSelected.getNome());
//			for (CorsoLite cl : result) {
//				resultStrings.add(cl.getNome());
//			}

			ArrayAdapter<String> adapterInitialList = new ArrayAdapter<String>(
					AddEvent4coursesActivity.this,
					R.layout.list_studymate_row_list_simple, resultStrings);

			coursesSpinner.setAdapter(adapterInitialList);
			coursesSpinner.setEnabled(false);
			coursesSpinner.setActivated(false);
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			new ProgressDialog(AddEvent4coursesActivity.this);
			pd = ProgressDialog.show(AddEvent4coursesActivity.this,
					"Caricamento della lista dei corsi ", "Caricamento...");
		}

	}

}
