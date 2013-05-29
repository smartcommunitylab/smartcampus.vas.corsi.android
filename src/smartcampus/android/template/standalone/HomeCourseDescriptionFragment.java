package smartcampus.android.template.standalone;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.smartuni.models.Author;
import eu.trentorise.smartcampus.smartuni.models.Comment;
import eu.trentorise.smartcampus.smartuni.models.FeedbackRowGroup;
import eu.trentorise.smartcampus.smartuni.utilities.AdapterFeedbackList;
import eu.trentorise.smartcampus.smartuni.utilities.CourseCompleteDataHandler;

public class HomeCourseDescriptionFragment extends SherlockFragment {


	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_home_course_description, container, false);
		

		Intent intent = getActivity().getIntent();

		ProgressDialog pd = ProgressDialog.show(getActivity(), "Informazioni del corso", "Caricamento...",true);

		TextView tvCourseName = (TextView) view.findViewById(R.id.textViewNameCourseHome);
		TextView descriptionCourse = (TextView) view.findViewById(R.id.textViewDescriptioonCourse);
		RatingBar ratingAverage = (RatingBar)view.findViewById(R.id.ratingBarCourse);
		ExpandableListView listComments = (ExpandableListView)view.findViewById(R.id.expandableListViewFeedback);
		
		
		tvCourseName.setText(FindHomeCourseActivity.courseInfo.getNome());
		ratingAverage.setRating((float)FindHomeCourseActivity.courseInfo.getValutazione_media());
		descriptionCourse.setText(FindHomeCourseActivity.courseInfo.getDescrizione());

		return view;
	}
	
	
}
