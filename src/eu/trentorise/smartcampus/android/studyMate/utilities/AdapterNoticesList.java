package eu.trentorise.smartcampus.android.studyMate.utilities;

import eu.trentorise.smartcampus.android.studyMate.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.studyMate.models.Notification;

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

		View row = convertView;
		Notification item = getItem(position);

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.notices_detail_row_list, parent,
					false);
		}

		TextView title = (TextView) row.findViewById(R.id.textViewNoticeTitle);
		TextView date = (TextView) row
				.findViewById(R.id.textViewDatetimeNoticeRow);
		TextView content = (TextView) row
				.findViewById(R.id.textViewDescriptionRow);

		long dateTS = item.getTimestamp();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm",
				Locale.ITALY);

		String dateString = dateFormat.format(dateTS);

		title.setText(item.getTitle());
		date.setText(dateString + " (" + item.getType() + ")");
		content.setText(item.getDescription());
		if (item.getDescription().equals(""))
			content.setVisibility(View.GONE);
		else {
			content.setVisibility(View.VISIBLE);
		}

		if (item.getTitle().equals(""))
			title.setVisibility(View.GONE);
		else
			title.setVisibility(View.VISIBLE);

		return row;

	}

}
