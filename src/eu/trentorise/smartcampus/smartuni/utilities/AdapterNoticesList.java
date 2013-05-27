package eu.trentorise.smartcampus.smartuni.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import smartcampus.android.template.standalone.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import eu.trentorise.smartcampus.smartuni.models.Notice;


public class AdapterNoticesList extends ArrayAdapter<Notice>{
	@SuppressWarnings("unused")
	private List<Notice> notices;
	private Context context = null;

	public AdapterNoticesList(Context mcontext, int layoutId, List<Notice> n) {
		super(mcontext, layoutId, n);
		notices = n;
		context = mcontext;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*LayoutInflater vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = vi.inflate(R.layout.notices_detail_row_list, null);*/

		
		View row = convertView;
		Notice item = getItem(position);

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.notices_detail_row_list, parent, false);
		}

		TextView title = (TextView) row.findViewById(R.id.textViewTitle);
		TextView date = (TextView) row.findViewById(R.id.textViewDatetimeRow);
		TextView content = (TextView) row.findViewById(R.id.textViewDescriptionRow);
		TextView author = (TextView) row.findViewById(R.id.textViewUserRow);

		title.setText(item.getTitle());
		
		String dateTS = item.getTimestamp();
		
		Date d = new Date(Long.parseLong(dateTS));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
				Locale.ITALY);

		String dateString = dateFormat.format(d);
		date.setText(dateString);
		content.setText(item.getDescription());
		content.setPadding(
				(int) context.getResources().getDimension(
						R.dimen.activity_horizontal_margin),
				0,
				(int) context.getResources().getDimension(
						R.dimen.activity_horizontal_margin), 0);
		Notice prev = null;
		if (position > 0)
			prev = getItem(position - 1);

		if (prev == null || !(prev.getTitle().equals(item.getTitle()))) {
			date.setVisibility(View.VISIBLE);
		} else {
			date.setVisibility(View.GONE);
		}
		
		
		// row.setPadding(
		// (int)context.getResources().getDimension(
		// R.dimen.activity_horizontal_margin),
		// 0,
		// (int)context.getResources().getDimension(
		// R.dimen.activity_horizontal_margin), 0);
		
		author.setText(item.getUser());
		
		return row;
		
		
		/*Notice notice = notices.get(position);

		Notice noticePrec = notices.get(position-1);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String dateToday = dateFormat.format(date);
		
		
		if(noticePrec.getTimestamp() == notice.getTimestamp()){
			
		}
		TextView noticeUser = (TextView) view
				.findViewById(smartcampus.android.template.standalone.R.id.textViewUserRow);
		noticeUser.setText(notice.getAuthor().getName());
		
		TextView noticeDatetime = (TextView) view
				.findViewById(smartcampus.android.template.standalone.R.id.textViewDatetimeRow);
		noticeDatetime.setText(notice.getUpdateTime());
		
		TextView noticeDescription = (TextView) view
				.findViewById(smartcampus.android.template.standalone.R.id.textViewDescriptionRow);
		noticeDescription.setText(notice.getDescription());

		return view;*/
	}
	

}
