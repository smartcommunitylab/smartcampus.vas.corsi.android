package eu.trentorise.smartcampus.android.studyMate.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.studyMate.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.EventItem4Adapter;
import eu.trentorise.smartcampus.android.studyMate.models.EventItem4AdapterGDS;

// in EventsListingFragment
public class EventAdapterGDS extends ArrayAdapter<EventItem4AdapterGDS> {

	private Context context;
	private int layoutResourceId;

	public EventAdapterGDS(Context context, EventItem4AdapterGDS[] arr) {
		super(context, R.layout.event_row, arr);
		this.context = context;
		this.layoutResourceId = R.layout.event_row;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		EventItem4AdapterGDS item = getItem(position);

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(layoutResourceId, parent, false);
		}

		TextView title = (TextView) row.findViewById(R.id.title_ev_text);
		TextView content = (TextView) row.findViewById(R.id.item_ev_text);
		TextView description = (TextView) row
				.findViewById(R.id.description_ev_text);
		TextView ora = (TextView) row.findViewById(R.id.time_ev_edit);
		TextView room = (TextView) row.findViewById(R.id.room_ev_edit);
		ImageView imageViewMyEvent = (ImageView) row.findViewById(R.id.icMyEvent);
		title.setText(item.getTitle());
		content.setText(item.getContent());
		description.setText(item.getDescription());
		ora.setText(item.getOra());
		room.setText(item.getRoom());
		
		content.setPadding(
				10,
				8,
				(int) context.getResources().getDimension(
						R.dimen.activity_horizontal_margin), 0);

		description.setPadding(20, 0, 0, (int) context.getResources()
				.getDimension(R.dimen.adpt_activity_vertical_margin));
		room.setPadding(
				20,
				0,
				0,
				(int) context.getResources().getDimension(
						R.dimen.adpt_activity_vertical_margin));
		ora.setPadding(0, 0, 10, 0);
		EventItem4AdapterGDS prev = null;
		if (position > 0)
			prev = getItem(position - 1);

		if (prev == null || !(prev.getTitle().compareTo(item.getTitle()) == 0)) {
			title.setVisibility(View.VISIBLE);
		} else {
			title.setVisibility(View.GONE);
		}
		return row;
	}

}
