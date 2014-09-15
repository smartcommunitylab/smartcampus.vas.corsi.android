package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.Message;

public class ChatAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Message> mMessages;

	public ChatAdapter(Context context, ArrayList<Message> messages) {
		super();
		this.mContext = context;
		this.mMessages = messages;
	}

	@Override
	public int getCount() {
		return mMessages.size();
	}

	@Override
	public Object getItem(int position) {
		return mMessages.get(position);
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Message message = (Message) this.getItem(position);

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.sms_row, parent, false);
			holder.message = (TextView) convertView
					.findViewById(R.id.message_text);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		if(message.isMine()){
			holder.message.setText(message.getMessage());
		}else{
			holder.message.setText(Html.fromHtml("<small>"+ "<font color='#939393'>" +message.getName() + "</font>"+"</small>"+ "<br />"+ message.getMessage()));
		}
//		"<![CDATA[<font color='#145A14'>text</font>]]>"
		LayoutParams lp = (LayoutParams) holder.message.getLayoutParams();
		// check if it is a status message then remove background, and change
		// text color.
		if (message.isStatusMessage()) {
			holder.message.setBackgroundDrawable(null);
			lp.gravity = Gravity.LEFT;
			holder.message.setTextColor(Color.BLACK);
		} else {
			// Check whether message is mine to show green background and align
			// to right
			if (message.isMine()) {
				holder.message
						.setBackgroundResource(R.drawable.speech_bubble_orange);
				lp.gravity = Gravity.RIGHT;
			}
			// If not mine then it is from sender to show orange background and
			// align to left
			else {
				holder.message
						.setBackgroundResource(R.drawable.speech_bubble_cyan);
				lp.gravity = Gravity.LEFT;
			}
			holder.message.setLayoutParams(lp);
			holder.message.setTextColor(Color.BLACK);
		}
		return convertView;
	}

	private static class ViewHolder {
		TextView message;
	}

	@Override
	public long getItemId(int position) {
		// Unimplemented, because we aren't using Sqlite.
		return position;
	}

}