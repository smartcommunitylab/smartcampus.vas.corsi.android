package smartcampus.android.template.standalone;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.smartuni.models.Author;
import eu.trentorise.smartcampus.smartuni.models.FeedbackRowGroup;
import eu.trentorise.smartcampus.smartuni.utilities.AdapterFeedbackList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.RatingBar;
import android.widget.TextView;

public class FeedbackFragment extends SherlockFragment {

	ExpandableListView list;
	AdapterFeedbackList mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_home_course_feedback,
				container, false);

		Intent intent = getActivity().getIntent();
		TextView tvCourseName = (TextView) view
				.findViewById(R.id.textViewTitleFeedback);
		String courseName = intent.getStringExtra("courseSelectedName");
		tvCourseName.setText(tvCourseName.getText() + " " + courseName);

		ArrayList<FeedbackRowGroup> ratings = new ArrayList<FeedbackRowGroup>();

		ArrayList<String> comments = new ArrayList<String>();

		for (int i = 0; i < 14; i++) {
			FeedbackRowGroup feedb = new FeedbackRowGroup();
			Author auth = new Author();
			auth.setName("Nome" + i);
			feedb.setAuthor(auth);
			feedb.setRating(i);
			feedb.setComment("Commento numero " + i);
			ratings.add(feedb);
		}

		mAdapter = new AdapterFeedbackList(getActivity(), ratings);

		list = (ExpandableListView) view
				.findViewById(R.id.expandableListViewFeedback);
		
		list.setAdapter(mAdapter);
		list.setGroupIndicator(null);
		list.setOnGroupClickListener(new OnGroupClickListener() {
			
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
		
		

		return view;
	}
	

}
