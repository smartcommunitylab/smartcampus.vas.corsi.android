package smartcampus.android.template.standalone;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RatingBar;
import eu.trentorise.smartcampus.smartuni.models.RatingRowGroup;

public class AdapterRating extends BaseExpandableListAdapter {

	Context mcontext;
	ArrayList<RatingRowGroup> ratings;

	public AdapterRating(Context context, ArrayList<RatingRowGroup> ratings) {
		this.mcontext = context;
		this.ratings = ratings;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return ratings.get(groupPosition).getRating();
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
		//RatingRowGroup inf = new RatingRowGroup();
		//inf.setComment((String) getChild(groupPosition, childPosition));
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) mcontext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.rating_list_row_child, null);
		}

		// TextView textComment = (TextView)
		// view.findViewById(R.id.textViewComment);
		// textComment.setText(inf.getComment().toString());

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

		//RatingRowGroup headerInfo = (RatingRowGroup) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) mcontext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.rating_list_row_header, null);
		}

		// TextView heading = (TextView)
		// view.findViewById(R.id.textViewAuthorRating);
		// heading.setText(headerInfo.getAuthor().getName().trim());

		RatingBar rb = (RatingBar) view.findViewById(R.id.ratingBarRowRating);
		rb.setNumStars(5);
//		rb.setRating(headerInfo.getRating());

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
