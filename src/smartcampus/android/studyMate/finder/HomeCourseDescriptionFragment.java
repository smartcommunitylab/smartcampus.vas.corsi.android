package smartcampus.android.studyMate.finder;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import eu.trentorise.smartcampus.studyMate.utilities.FeedbackHandler;
import eu.trentorise.smartcampus.studyMate.utilities.SetCourseAsFollowHandler;

public class HomeCourseDescriptionFragment extends SherlockFragment {

	public FeedbackHandler feedbackHandler;
	public SherlockFragmentActivity act;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
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
		txtMonitor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (txtMonitor.getText() == getResources().getText(R.string.label_txtMonitor_off)){
					new SetCourseAsFollowHandler(getActivity().getParent(), txtMonitor).execute(FindHomeCourseActivity.corsoAttuale);
				}
				else
					txtMonitor.setText(getResources().getText(
							R.string.label_txtMonitor_off));
			}
		});


		super.onStart();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}
}