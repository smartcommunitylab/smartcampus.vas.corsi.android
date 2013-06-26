package smartcampus.android.template.standalone;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import eu.trentorise.smartcampus.smartuni.models.RatingRowGroup;
import eu.trentorise.smartcampus.smartuni.utilities.AdapterFeedbackList;

public class AddRateActivity extends FragmentActivity {

	ExpandableListView list;
	AdapterFeedbackList mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// View view = findViewById(R.layout.activity_add_rating);
		setContentView(R.layout.activity_add_rating);
		// }
		//
		// @Override
		// public View onCreateView(LayoutInflater inflater, ViewGroup
		// container,
		// Bundle savedInstanceState) {
		// // TODO Auto-generated method stub
		// View view = inflater.inflate(R.layout.activity_add_rating,
		// container, false);
		//TextView titleCourseRating = (TextView) findViewById(R.id.textViewTitleRatingCourse);
		//titleCourseRating.setText(FindHomeCourseActivity.courseInfo.getNome());

		ArrayList<RatingRowGroup> ratings = new ArrayList<RatingRowGroup>();
		ExpandableListView list = (ExpandableListView) findViewById(R.id.expandableListViewRating);
		
		for (int i = 0; i < 5; i++) {
			RatingRowGroup rrg = new RatingRowGroup();
			rrg.setRating(0);
			ratings.add(rrg);
		}
		AdapterRating mAdapter = new AdapterRating(this, ratings);

		list.setAdapter(mAdapter);
		list.setGroupIndicator(null);
		list.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				if (parent.isGroupExpanded(groupPosition))
					parent.collapseGroup(groupPosition);
				else
					parent.expandGroup(groupPosition, true);
				return true;
			}
		});

		// //return view;
	}

}
