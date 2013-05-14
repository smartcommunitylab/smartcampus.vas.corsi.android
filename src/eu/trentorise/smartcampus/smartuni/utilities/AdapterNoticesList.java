package eu.trentorise.smartcampus.smartuni.utilities;

import java.util.ArrayList;
import java.util.List;

import smartcampus.android.template.standalone.R;

import eu.trentorise.smartcampus.smartuni.models.Notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class AdapterNoticesList extends ArrayAdapter<Notice>{
	private List<Notice> notices;
	private Context mContext = null;

	public AdapterNoticesList(Context context, int layoutId, List<Notice> n) {
		super(context, layoutId, n);
		notices = n;
		mContext = context;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = vi.inflate(R.layout.notices_detail_row_list, null);

		Notice notice = notices.get(position);

		TextView noticeUser = (TextView) view
				.findViewById(smartcampus.android.template.standalone.R.id.textViewUserRow);
		noticeUser.setText(notice.getAuthor().getName());
		
		TextView noticeDatetime = (TextView) view
				.findViewById(smartcampus.android.template.standalone.R.id.textViewDatetimeRow);
		noticeDatetime.setText(notice.getUpdateTime());
		
		TextView noticeDescription = (TextView) view
				.findViewById(smartcampus.android.template.standalone.R.id.textViewDescriptionRow);
		noticeDescription.setText(notice.getDescription());

		return view;
	}
}
