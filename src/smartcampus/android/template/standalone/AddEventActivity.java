package smartcampus.android.template.standalone;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class AddEventActivity extends FragmentActivity {
	private int mYear;
	private int mMonth;
	private int mDay;

	@SuppressWarnings("unused")
	private TextView mDateDisplay;
	private EditText mPickDate;

	static final int DATE_DIALOG_ID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		//mDateDisplay = (TextView) findViewById(R.id.showMyDate);
		//mPickDate = (EditText) findViewById(R.id.myDatePickerButton);
//
//		mPickDate.setOnClickListener(new View.OnClickListener() {
//			@SuppressWarnings("deprecation")
//			public void onClick(View v) {
//				showDialog(DATE_DIALOG_ID);
//			}
//		});
//
		// get the current date
//		final Calendar c = Calendar.getInstance();
//		mYear = c.get(Calendar.YEAR);
//		mMonth = c.get(Calendar.MONTH);
//		mDay = c.get(Calendar.DAY_OF_MONTH);

		// display the current date
//		updateDisplay();
	}

//	public void updateDisplay() {
//		this.mPickDate.setText(new StringBuilder()
//				// Month is 0 based so add 1
//				.append(mMonth + 1).append("-").append(mDay).append("-")
//				.append(mYear).append(" "));
//	}

//	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//		public void onDateSet(DatePicker view, int year, int monthOfYear,
//				int dayOfMonth) {
//			mYear = year;
//			mMonth = monthOfYear;
//			mDay = dayOfMonth;
//			updateDisplay();
//		}
//	};
//
//	protected Dialog onCreateDialog(int id) {
//		switch (id) {
//		case DATE_DIALOG_ID:
//			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
//					mDay);
//		}
//		return null;
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_event, menu);
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

}
