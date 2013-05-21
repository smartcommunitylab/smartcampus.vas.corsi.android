package smartcampus.android.template.standalone;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	private EditText mPickDate;
	private int year;
	private int month;
	private int day;

//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		View v = super.onCreateView(inflater, container, savedInstanceState);
//		EditText et= (EditText)v.findViewById(R.id.myDatePickerButton);
//		
//	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// mPickDate = (EditText)
		// getView().findViewById(R.id.myDatePickerButton);
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		// // updateDisplay();
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// mPickDate = (EditText)
		// getView().findViewById(R.id.myDatePickerButton);
		// updateDisplay();
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user

		// mPickDate = (EditText)
		// getView().findViewById(R.id.myDatePickerButton);
		// updateDisplay();

	}

	public void updateDisplay() {
		mPickDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(month + 1).append("-").append(day).append("-")
				.append(year).append(" "));
	}
}