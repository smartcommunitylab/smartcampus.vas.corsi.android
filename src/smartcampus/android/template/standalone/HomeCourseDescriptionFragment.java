package smartcampus.android.template.standalone;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import eu.trentorise.smartcampus.smartuni.models.Author;
import eu.trentorise.smartcampus.smartuni.models.FeedbackRowGroup;
import eu.trentorise.smartcampus.smartuni.utilities.AdapterFeedbackList;
import eu.trentorise.smartcampus.smartuni.utilities.FeedbackCourseHandler;

public class HomeCourseDescriptionFragment extends SherlockFragment
{

	public static ProgressDialog	pd;
	public FeedbackCourseHandler feedbackHandler;
	public Activity act;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		final View view = inflater.inflate(
				R.layout.fragment_home_course_description, container, false);

		// Intent intent = getActivity().getIntent();
		// TextView tvCourseName = (TextView)
		// view.findViewById(R.id.textViewNameCourseHome);
		// String courseName = intent.getStringExtra("courseSelectedName");
		// //tvCourseName.setText(courseName);
		//
		//
		// TextView descriptionCourse = (TextView)
		// getActivity().findViewById(R.id.textViewDescriptioonCourse);
		// RatingBar ratingAverage =
		// (RatingBar)getActivity().findViewById(R.id.ratingBarCourse);
		// ExpandableListView listComments =
		// (ExpandableListView)getActivity().findViewById(R.id.expandableListViewFeedback);
		//
		// pd = ProgressDialog.show(getActivity(),
		// "Informazioni del corso di "+courseName, "Caricamento...",
		// true);
		//
		// String idCourse = intent.getStringExtra("courseSelectedId");
		// new CourseCompleteDataHandler(getActivity(), idCourse, tvCourseName,
		// descriptionCourse,ratingAverage, listComments).execute();
		new ProgressDialog(getActivity());
		pd = ProgressDialog.show(getActivity(),
				"Informazioni del corso di " + FindHomeCourseActivity.courseName, "Caricamento...");
		
		TextView descriptionCourse = (TextView) view
				.findViewById(R.id.textViewDescriptioonCourseHome);
		final RatingBar ratingAverage = (RatingBar) view
				.findViewById(R.id.ratingBarCourseAverage);
		
		feedbackHandler = (FeedbackCourseHandler) new FeedbackCourseHandler(
				getActivity(), FindHomeCourseActivity.corsoAttuale.getId(),act, ratingAverage, descriptionCourse)
				.execute();
		




//		final ExpandableListView list = (ExpandableListView) view
//				.findViewById(R.id.list_view_expanded);
//
//		//ArrayList<FeedbackRowGroup> ratings = null;
//		//AdapterFeedbackList adpt = new AdapterFeedbackList(getActivity(), ratings);
//		ArrayList<FeedbackRowGroup> ratings = new ArrayList<FeedbackRowGroup>();
//		//List<Commento> comments = FeedbackCourseHandler.feedbackInfoList;
//		//for (int i = 0; i < comments.size(); i++) {
//			FeedbackRowGroup feedb = new FeedbackRowGroup();
//			Author auth = new Author();
//			auth.setName("");
//			feedb.setAuthor(auth);
//			
//			feedb.setRating_cfu(1);
//			feedb.setRating_contenuti(1);
//			feedb.setRating_esame(1);
//			feedb.setRating_lezioni(1);
//			feedb.setRating_materiale(1);
//			feedb.setComment("");
//			ratings.add(feedb);
//		//}
////		ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(),
////				android.R.layout.simple_list_item_1, new String[] { "A", "B",
////						"C" });
//		AdapterFeedbackList mAdapter = new AdapterFeedbackList(getActivity(), ratings);
//		list.setAdapter(mAdapter);

//		ratingAverage.setOnTouchListener(new OnTouchListener()
//		{
//			@Override
//			public boolean onTouch(View v, MotionEvent event)
//			{
//				if (event.getAction() == MotionEvent.ACTION_DOWN)
//				{
//					int vis = list.getVisibility();
//					if (vis != View.VISIBLE)
//					{
//						list.setVisibility(View.VISIBLE);
//					}
//					else
//					{
//						list.setVisibility(View.GONE);
//					}
//					view.invalidate();
//				}
//				return true;
//			}
//		});

		Switch switchFollow = (Switch) view.findViewById(R.id.switchFollow);
		final TextView txtMonitor = (TextView) view
				.findViewById(R.id.txt_monitor);
		if (txtMonitor.isPressed())
			txtMonitor.setText(getResources().getText(
					R.string.label_txtMonitor_on));
		else
			txtMonitor.setText(getResources().getText(
					R.string.label_txtMonitor_off));

		switchFollow.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked)
			{
				// TODO Auto-generated method stub
				if (isChecked)
				{
					txtMonitor.setText(getResources().getText(
							R.string.label_txtMonitor_on));
				}
				else
				{
					txtMonitor.setText(getResources().getText(
							R.string.label_txtMonitor_off));
				}
			}
		});

		/*
		 * List<Comment> comments =
		 * FindHomeCourseActivity.courseInfo.getCommenti();
		 * 
		 * ArrayList<FeedbackRowGroup> ratings = new
		 * ArrayList<FeedbackRowGroup>();
		 * 
		 * for (int i = 0; i < comments.size(); i++) { FeedbackRowGroup feedb =
		 * new FeedbackRowGroup(); Author auth = new Author();
		 * auth.setName(comments.get(i).getAutore().getNome());
		 * feedb.setAuthor(auth);
		 * feedb.setRating(comments.get(i).getValutazione());
		 * feedb.setComment(comments.get(i).getTesto()); ratings.add(feedb); }
		 * 
		 * AdapterFeedbackList mAdapter = new AdapterFeedbackList(context,
		 * ratings);
		 * 
		 * 
		 * 
		 * listComments.setAdapter(mAdapter);
		 * listComments.setGroupIndicator(null);
		 * listComments.setOnGroupClickListener(new OnGroupClickListener() {
		 * 
		 * @Override public boolean onGroupClick(ExpandableListView parent, View
		 * v, int groupPosition, long id) { // TODO Auto-generated method stub
		 * if(parent.isGroupExpanded(groupPosition))
		 * parent.collapseGroup(groupPosition); else
		 * parent.expandGroup(groupPosition, true); return true; } });
		 */

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	}

	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		act = activity;
	}
}