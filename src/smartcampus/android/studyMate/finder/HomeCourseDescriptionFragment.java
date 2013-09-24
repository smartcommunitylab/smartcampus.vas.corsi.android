package smartcampus.android.studyMate.finder;

import smartcampus.android.template.standalone.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import eu.trentorise.smartcampus.studyMate.utilities.FeedbackHandler;

public class HomeCourseDescriptionFragment extends SherlockFragment {

	public FeedbackHandler feedbackHandler;
	public SherlockFragmentActivity act;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final View view = inflater.inflate(
				R.layout.fragment_home_course_description, container, false);

		return view;
	}

	@Override
	public void onStart() {
		act = this.getSherlockActivity();
		TextView descriptionCourse = (TextView) getSherlockActivity()
				.findViewById(R.id.textViewDescriptioonCourseHome);
		final RatingBar ratingAverage = (RatingBar) getSherlockActivity()
				.findViewById(R.id.ratingBarCourseAverage);
		Button switchFollow = (Button) getSherlockActivity().findViewById(
				R.id.switchFollow);
		feedbackHandler = (FeedbackHandler) new FeedbackHandler(
				getSherlockActivity(),
				FindHomeCourseActivity.corsoAttuale.getId(), act,
				ratingAverage, descriptionCourse, switchFollow).execute();
		final TextView txtMonitor = (TextView) getSherlockActivity()
				.findViewById(R.id.txt_monitor);
		if (txtMonitor.isPressed())
			txtMonitor.setText(getResources().getText(
					R.string.label_txtMonitor_on));
		else
			txtMonitor.setText(getResources().getText(
					R.string.label_txtMonitor_off));

		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		// // TODO Auto-generated method stub
		// if (isChecked) {
		// txtMonitor.setText(getResources().getText(
		// R.string.label_txtMonitor_on));
		// } else {
		// txtMonitor.setText(getResources().getText(
		// R.string.label_txtMonitor_off));
		// }
		// }
		// });

		super.onStart();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	}

	// @Override
	// public void onAttach(Activity activity) {
	// // TODO Auto-generated method stub
	// super.onAttach(activity);
	// act = activity;
	// }
}