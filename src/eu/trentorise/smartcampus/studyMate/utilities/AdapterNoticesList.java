package eu.trentorise.smartcampus.studyMate.utilities;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import smartcampus.android.template.standalone.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import eu.trentorise.smartcampus.communicator.model.Notification;

public class AdapterNoticesList extends ArrayAdapter<Notification> {
	@SuppressWarnings("unused")
	private List<Notification> notices;
	private Context context = null;

	public AdapterNoticesList(Context mcontext, int layoutId,
			List<Notification> n) {
		super(mcontext, layoutId, n);
		notices = n;
		context = mcontext;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*
		 * LayoutInflater vi = (LayoutInflater) context
		 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); convertView =
		 * vi.inflate(R.layout.notices_detail_row_list, null);
		 */

		View row = convertView;
		Notification item = getItem(position);

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.notices_detail_row_list, parent,
					false);
		}

		TextView title = (TextView) row.findViewById(R.id.textViewTitle);
		TextView date = (TextView) row.findViewById(R.id.textViewDatetimeRow);
		TextView content = (TextView) row
				.findViewById(R.id.textViewDescriptionRow);
		TextView author = (TextView) row.findViewById(R.id.textViewUserRow);

		title.setText(item.getTitle());

		long dateTS = item.getTimestamp();

		// Date d = new Date(Long.parseLong(dateTS));
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss", Locale.ITALY);

		String dateString = dateFormat.format(dateTS);
		date.setText(dateString);
		content.setText(item.getDescription());
		content.setPadding(
				(int) context.getResources().getDimension(
						R.dimen.activity_horizontal_margin),
				0,
				(int) context.getResources().getDimension(
						R.dimen.activity_horizontal_margin), 0);
		Notification prev = null;
		if (position > 0)
			prev = getItem(position - 1);

		if (prev == null || !(prev.getTitle().equals(item.getTitle()))) {
			date.setVisibility(View.VISIBLE);
		} else {
			date.setVisibility(View.GONE);
		}

		author.setText(item.getUser());

		return row;

	}

}
