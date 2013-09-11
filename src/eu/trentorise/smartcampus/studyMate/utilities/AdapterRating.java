package eu.trentorise.smartcampus.studyMate.utilities;

import java.util.ArrayList;

import smartcampus.android.template.standalone.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import eu.trentorise.smartcampus.studyMate.models.RatingRowGroup;

public class AdapterRating extends BaseExpandableListAdapter {

	Context mcontext;
	ArrayList<RatingRowGroup> ratings;

	public AdapterRating(Context context, ArrayList<RatingRowGroup> ratings) {
		this.mcontext = context;
		this.ratings = ratings;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return ratings.get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {

		// DetailInfo detailInfo = (DetailInfo) getChild(groupPosition,
		// childPosition);

		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) mcontext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.rating_list_row_child, null);
		}

		RatingRowGroup inf = new RatingRowGroup();
		// if(childPosition==0)
		// inf.setExplainContext((String) getChild(groupPosition,
		// childPosition));
		// if(childPosition==1)
		// inf.setRating((Integer) getChild(groupPosition,
		// childPosition));
		inf = (RatingRowGroup) getChild(groupPosition, childPosition);

		RatingBar rbCont = (RatingBar) view
				.findViewById(R.id.ratingBarContextContenuti);
		RatingBar rbCfu = (RatingBar) view
				.findViewById(R.id.ratingBarContextCfu);
		RatingBar rbLez = (RatingBar) view
				.findViewById(R.id.ratingBarContextLezioni);
		RatingBar rbMat = (RatingBar) view
				.findViewById(R.id.ratingBarContextMateriali);
		RatingBar rbExam = (RatingBar) view
				.findViewById(R.id.ratingBarContextEsame);

		switch (groupPosition) {
		case 0:
			rbCont.setRating(inf.getRating());
			break;
		case 1:
			rbCfu.setRating(inf.getRating());
			break;
		case 2:
			rbLez.setRating(inf.getRating());
			break;
		case 3:
			rbMat.setRating(inf.getRating());
			break;
		case 4:
			rbCfu.setRating(inf.getRating());
			break;
		}

		TextView textExplain = (TextView) view
				.findViewById(R.id.textViewExplainRate);
		textExplain.setText(inf.getExplainContext());

		rbCont.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				ratings.get(0).setRating(rating);
				Log.i("ratings", ratings.toString());
			}
		});

		rbCfu.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				ratings.get(1).setRating(rating);
				Log.i("ratings", ratings.toString());
			}
		});

		rbLez.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				ratings.get(2).setRating(rating);
				Log.i("ratings", ratings.toString());
			}
		});

		rbMat.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				ratings.get(3).setRating(rating);
				Log.i("ratings", ratings.toString());
			}
		});
		rbExam.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				ratings.get(4).setRating(rating);
				Log.i("ratings", ratings.toString());
			}
		});

		switch (groupPosition) {
		case 0:
			rbCont.setVisibility(View.VISIBLE);
			rbCfu.setVisibility(View.GONE);
			rbLez.setVisibility(View.GONE);
			rbMat.setVisibility(View.GONE);
			rbExam.setVisibility(View.GONE);
			return view;
			// rbCont.setRating(inf.getRating());
		case 1:
			rbCont.setVisibility(View.GONE);
			rbCfu.setVisibility(View.VISIBLE);
			rbLez.setVisibility(View.GONE);
			rbMat.setVisibility(View.GONE);
			rbExam.setVisibility(View.GONE);
			return view;
			// rbCfu.setRating(inf.getRating());
		case 2:
			rbCont.setVisibility(View.GONE);
			rbCfu.setVisibility(View.GONE);
			rbLez.setVisibility(View.VISIBLE);
			rbMat.setVisibility(View.GONE);
			rbExam.setVisibility(View.GONE);
			return view;
			// rbLez.setRating(inf.getRating());
		case 3:
			rbCont.setVisibility(View.GONE);
			rbCfu.setVisibility(View.GONE);
			rbLez.setVisibility(View.GONE);
			rbMat.setVisibility(View.VISIBLE);
			rbExam.setVisibility(View.GONE);
			return view;

			// rbMat.setRating(inf.getRating());
		case 4:
			rbCont.setVisibility(View.GONE);
			rbCfu.setVisibility(View.GONE);
			rbLez.setVisibility(View.GONE);
			rbMat.setVisibility(View.GONE);
			rbExam.setVisibility(View.VISIBLE);
			return view;
		}

		// rbExam.setRating(inf.getRating());

		// inizializzo la lista dei

		//
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;

	}

	@Override
	public Object getGroup(int groupPosition) {
		return ratings.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return ratings.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isLastChild, View view,
			ViewGroup parent) {

		RatingRowGroup headerInfo = (RatingRowGroup) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) mcontext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.rating_list_row_header, null);
		}

		// TextView heading = (TextView)
		// view.findViewById(R.id.textViewAuthorRating);
		// heading.setText(headerInfo.getAuthor().getName().trim());
		TextView heading = (TextView) view
				.findViewById(R.id.textViewContextRating);
		heading.setText(headerInfo.getContext().trim());
		// rb.setRating(headerInfo.getRating());

		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}