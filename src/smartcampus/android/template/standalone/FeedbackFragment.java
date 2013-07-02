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
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.smartuni.models.Author;
import eu.trentorise.smartcampus.smartuni.models.Commento;
import eu.trentorise.smartcampus.smartuni.models.CorsoLite;
import eu.trentorise.smartcampus.smartuni.models.FeedbackRowGroup;
import eu.trentorise.smartcampus.smartuni.utilities.AdapterFeedbackList;
import eu.trentorise.smartcampus.smartuni.utilities.FeedbackCourseHandler;

public class FeedbackFragment extends SherlockFragment {

	ExpandableListView list;
	AdapterFeedbackList mAdapter;
	public static ProgressDialog pd;
	List<Commento> commenti;

	@Override
	public void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_home_course_feedback,
				container, false);

		Intent intent = this.getActivity().getIntent();
		@SuppressWarnings("unused")
		CorsoLite corsoAttuale = new CorsoLite();
		corsoAttuale = (CorsoLite) intent
				.getSerializableExtra("courseSelected");
		@SuppressWarnings("unused")
		String idCourse = intent.getStringExtra("courseSelectedId");

		TextView titleCourseFeedback = (TextView) view
				.findViewById(R.id.textViewTitleFeedbackCourse);
		titleCourseFeedback.setText(FeedbackCourseHandler.feedbackInfoList
				.get(0).getCorso().getNome());

		List<Commento> comments = FeedbackCourseHandler.feedbackInfoList;

		ArrayList<FeedbackRowGroup> ratings = new ArrayList<FeedbackRowGroup>();

		for (int i = 0; i < comments.size(); i++) {
			FeedbackRowGroup feedb = new FeedbackRowGroup();
			Author auth = new Author();
			auth.setName(comments.get(i).getId_studente().getNome());
			feedb.setAuthor(auth);

			feedb.setRating((comments.get(i).getRating_carico_studio()
					+ comments.get(i).getRating_contenuto()
					+ comments.get(i).getRating_esame()
					+ comments.get(i).getRating_lezioni() + comments.get(i)
					.getRating_materiali()) / 5);
			feedb.setRating_cfu(comments.get(i).getRating_carico_studio());
			feedb.setRating_contenuti(comments.get(i).getRating_contenuto());
			feedb.setRating_esame(comments.get(i).getRating_esame());
			feedb.setRating_lezioni(comments.get(i).getRating_lezioni());
			feedb.setRating_materiale(comments.get(i).getRating_materiali());
			feedb.setComment(comments.get(i).getTesto());
			ratings.add(feedb);
		}

		ExpandableListView listComments = (ExpandableListView) view
				.findViewById(R.id.expandableListViewFeedback);
		AdapterFeedbackList mAdapter = new AdapterFeedbackList(getActivity(),
				ratings);

		listComments.setAdapter(mAdapter);
		listComments.setGroupIndicator(null);
		listComments.setOnGroupClickListener(new OnGroupClickListener() {

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

		return view;
	}

}