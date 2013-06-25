package smartcampus.android.template.standalone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

public class HomeCourseDescriptionFragment extends SherlockFragment {



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_home_course_description, container, false);

//		Intent intent = getActivity().getIntent();
//		TextView tvCourseName = (TextView) view.findViewById(R.id.textViewNameCourseHome);
//		String courseName = intent.getStringExtra("courseSelectedName");
//		//tvCourseName.setText(courseName);
//		
//		
//		TextView descriptionCourse = (TextView) getActivity().findViewById(R.id.textViewDescriptioonCourse);
//		RatingBar ratingAverage = (RatingBar)getActivity().findViewById(R.id.ratingBarCourse);
//		ExpandableListView listComments = (ExpandableListView)getActivity().findViewById(R.id.expandableListViewFeedback);
//		
//		pd = ProgressDialog.show(getActivity(), "Informazioni del corso di "+courseName, "Caricamento...",
//                true);
//		
//		String idCourse = intent.getStringExtra("courseSelectedId");
//		new CourseCompleteDataHandler(getActivity(), idCourse, tvCourseName, descriptionCourse,ratingAverage, listComments).execute();
		TextView descriptionCourse = (TextView) view.findViewById(R.id.textViewDescriptioonCourseHome);
		RatingBar ratingAverage = (RatingBar)view.findViewById(R.id.ratingBarCourseAverage);
		
		getActivity().getActionBar().setTitle(FindHomeCourseActivity.feedbackInfoList.get(0).getCorso().getNome());
		ratingAverage.setRating((float)FindHomeCourseActivity.feedbackInfoList.get(0).getCorso().getValutazione_media());
		descriptionCourse.setText(FindHomeCourseActivity.feedbackInfoList.get(0).getCorso().getDescrizione());

		
		
		Switch switchFollow = (Switch)view.findViewById(R.id.switchFollow);
		final TextView txtMonitor = (TextView)view.findViewById(R.id.txt_monitor);
		if(txtMonitor.isPressed())
			txtMonitor.setText(getResources().getText(R.string.label_txtMonitor_on));
		else
			txtMonitor.setText(getResources().getText(R.string.label_txtMonitor_off));
		
		switchFollow.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					txtMonitor.setText(getResources().getText(R.string.label_txtMonitor_on));
				}else{
					txtMonitor.setText(getResources().getText(R.string.label_txtMonitor_off));
				}
			}
		});
		
		
		/*List<Comment> comments = FindHomeCourseActivity.courseInfo.getCommenti();

		ArrayList<FeedbackRowGroup> ratings = new ArrayList<FeedbackRowGroup>();
		
		for (int i = 0; i < comments.size(); i++) {
			FeedbackRowGroup feedb = new FeedbackRowGroup();
			Author auth = new Author();
			auth.setName(comments.get(i).getAutore().getNome());
			feedb.setAuthor(auth);
			feedb.setRating(comments.get(i).getValutazione());
			feedb.setComment(comments.get(i).getTesto());
			ratings.add(feedb);
		}

		AdapterFeedbackList mAdapter = new AdapterFeedbackList(context, ratings);

		
		
		listComments.setAdapter(mAdapter);
		listComments.setGroupIndicator(null);
		listComments.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				if(parent.isGroupExpanded(groupPosition))
					parent.collapseGroup(groupPosition);
				else
					parent.expandGroup(groupPosition, true);
				return true;
			}
		});
		*/

		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	}


}