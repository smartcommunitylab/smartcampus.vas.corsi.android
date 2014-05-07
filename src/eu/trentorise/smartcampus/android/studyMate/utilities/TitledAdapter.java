package eu.trentorise.smartcampus.android.studyMate.utilities;

import it.smartcampuslab.studymate.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// in EventsListingFragment
public class TitledAdapter extends ArrayAdapter<TitledItem> {

	private Context context;
	private int layoutResourceId;

	public TitledAdapter(Context context, TitledItem[] arr) {
		super(context, R.layout.titled_row, arr);
		this.context = context;
		this.layoutResourceId = R.layout.titled_row;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		TitledItem item = getItem(position);

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(layoutResourceId, parent, false);
		}

		TextView title = (TextView) row.findViewById(R.id.title_text);
		TextView content = (TextView) row.findViewById(R.id.item_text);

		title.setText(item.getTitle());
		content.setText(item.getContent());
		content.setPadding(
				(int) context.getResources().getDimension(
						R.dimen.activity_horizontal_margin),
				(int) context.getResources().getDimension(
						R.dimen.adpt_activity_vertical_margin),
				(int) context.getResources().getDimension(
						R.dimen.activity_horizontal_margin),
				(int) context.getResources().getDimension(
						R.dimen.adpt_activity_vertical_margin));
		TitledItem prev = null;
		if (position > 0)
			prev = getItem(position - 1);

		if (prev == null || !(prev.getTitle().equals(item.getTitle()))) {
			title.setVisibility(View.VISIBLE);
		} else {
			title.setVisibility(View.GONE);
		}
		return row;
	}

}
