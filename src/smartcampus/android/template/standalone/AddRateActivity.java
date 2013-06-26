package smartcampus.android.template.standalone;

import java.util.ArrayList;

import android.database.MatrixCursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import eu.trentorise.smartcampus.smartuni.models.RatingRowGroup;
import eu.trentorise.smartcampus.smartuni.utilities.AdapterFeedbackList;

public class AddRateActivity extends FragmentActivity {

	ExpandableListView list;
	AdapterFeedbackList mAdapter;
	ArrayList<RatingRowGroup> ratings;

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
		// TextView titleCourseRating = (TextView)
		// findViewById(R.id.textViewTitleRatingCourse);
		// titleCourseRating.setText(FindHomeCourseActivity.courseInfo.getNome());

		ratings = new ArrayList<RatingRowGroup>();
		final ExpandableListView list = (ExpandableListView) findViewById(R.id.expandableListViewRating);

		// setto i campi della valutazione
		setRatingContexts();

		final AdapterRating mAdapter = new AdapterRating(this, ratings);

		list.setAdapter(mAdapter);
		list.setGroupIndicator(null);
		list.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				int count =  mAdapter.getGroupCount();
				// TODO Auto-generated method stub
				if (parent.isGroupExpanded(groupPosition)){
					
					for (int i = 0; i <count ; i++)
						parent.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white_smartn_theme));

					parent.collapseGroup(groupPosition);
					
				}else{
					
					
					for (int i = 0; i <count ; i++){
					  parent.collapseGroup(i);
					
					  if(i!=groupPosition){
						  parent.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white_smartn_theme));
					  }
					}
					
					
					parent.expandGroup(groupPosition, true);
					parent.getChildAt(groupPosition).setBackgroundColor(getResources().getColor(R.color.pressed_smartn_theme));

				}
				
				
				return true;
			}
			
		
		});
		
		
		list.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				
				
				
				
				return true;
			}
		});
		
		

		// //return view;
	}

	private void setRatingContexts() {
		// TODO Auto-generated method stub

		// CONTENUTI
		RatingRowGroup rrg = new RatingRowGroup();
		rrg.setRating(0);
		rrg.setContext(getResources().getString(
				R.string.rating_context_contenuti));
		rrg.setExplainContext(getResources().getString(
				R.string.rating_contextExplain_contenuti));
		ratings.add(rrg);

		// CARICO DI STUDIO
		rrg = new RatingRowGroup();
		rrg.setRating(0);
		rrg.setContext(getResources().getString(
				R.string.rating_context_carico_studio));
		rrg.setExplainContext(getResources().getString(
				R.string.rating_contextExplain_carico_studio));
		ratings.add(rrg);

		// LEZIONI
		rrg = new RatingRowGroup();
		rrg.setRating(0);
		rrg.setContext(getResources()
				.getString(R.string.rating_context_lezioni));
		rrg.setExplainContext(getResources().getString(
				R.string.rating_contextExplain_lezioni));
		ratings.add(rrg);

		// MATERIALI
		rrg = new RatingRowGroup();
		rrg.setRating(0);
		rrg.setContext(getResources().getString(
				R.string.rating_context_materiali));
		rrg.setExplainContext(getResources().getString(
				R.string.rating_contextExplain_materiali));
		ratings.add(rrg);

		// CARICO DI STUDIO
		rrg = new RatingRowGroup();
		rrg.setRating(0);
		rrg.setContext(getResources().getString(
				R.string.rating_context_carico_studio));
		rrg.setExplainContext(getResources().getString(
				R.string.rating_contextExplain_carico_studio));
		ratings.add(rrg);

		// CARICO DI STUDIO
		rrg = new RatingRowGroup();
		rrg.setRating(0);
		rrg.setContext(getResources().getString(
				R.string.rating_context_esame));
		rrg.setExplainContext(getResources().getString(
				R.string.rating_contextExplain_esame));
		ratings.add(rrg);
	}

}
