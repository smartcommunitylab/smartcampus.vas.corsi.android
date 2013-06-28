package eu.trentorise.smartcampus.smartuni.utilities;

import java.util.ArrayList;

import smartcampus.android.template.standalone.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import eu.trentorise.smartcampus.smartuni.models.FeedbackRowGroup;

public class AdapterFeedbackList extends BaseExpandableListAdapter
{

	Context						mcontext;
	ArrayList<FeedbackRowGroup>	ratings;

	public AdapterFeedbackList(Context context,
			ArrayList<FeedbackRowGroup> ratings)
	{
		this.mcontext = context;
		this.ratings = ratings;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		// ArrayList<DetailInfo> productList =
		// deptList.get(groupPosition).getProductList();
		return ratings.get(groupPosition).getComment();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent)
	{

		// DetailInfo detailInfo = (DetailInfo) getChild(groupPosition,
		// childPosition);
		FeedbackRowGroup inf = new FeedbackRowGroup();
		inf.setComment((String) getChild(groupPosition, childPosition));
		if (view == null)
		{
			LayoutInflater infalInflater = (LayoutInflater) mcontext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater
					.inflate(R.layout.feedback_list_row_child, null);
		}

		TextView textComment = (TextView) view
				.findViewById(R.id.textViewComment);
		textComment.setText(inf.getComment().toString());

		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return 1;

	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return ratings.get(groupPosition);
	}

	@Override
	public int getGroupCount()
	{
		return ratings.size();
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isLastChild, View view,
			ViewGroup parent)
	{

		FeedbackRowGroup headerInfo = (FeedbackRowGroup) getGroup(groupPosition);
		if (view == null)
		{
			LayoutInflater inf = (LayoutInflater) mcontext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.feedback_list_row_header, null);
		}

		TextView heading = (TextView) view
				.findViewById(R.id.textViewAuthorRating);
		heading.setText(headerInfo.getAuthor().getName().trim());

		RatingBar rb = (RatingBar) view.findViewById(R.id.ratingBarRow);
		// rb.setNumStars(headerInfo.getRating());
		rb.setRating(headerInfo.getRating());

		return view;
	}

	@Override
	public boolean hasStableIds()
	{
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}

}
