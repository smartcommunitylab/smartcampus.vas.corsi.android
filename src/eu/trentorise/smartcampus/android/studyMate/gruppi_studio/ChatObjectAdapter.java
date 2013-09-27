package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import smartcampus.android.template.standalone.R;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.studyMate.models.ChatAttachment;
import eu.trentorise.smartcampus.android.studyMate.models.ChatMessage;
import eu.trentorise.smartcampus.android.studyMate.models.ChatObject;

public class ChatObjectAdapter extends ArrayAdapter<ChatObject> {

	private ArrayList<ChatObject> entries;
	Context context;

	public ChatObjectAdapter(Context context, int textViewResourceId,
			ArrayList<ChatObject> objects) {
		super(context, textViewResourceId, objects);
		this.entries = objects;
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View bubble = convertView;
		ChatObject currentBubble = getItem(position);

		if (bubble == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (currentBubble instanceof ChatMessage) {
				bubble = inflater.inflate(R.layout.chatmessage_bubble, null);
			} else if (currentBubble instanceof ChatAttachment) {
				bubble = inflater.inflate(R.layout.chatattachment_bubble, null);
			}
		}

		if (currentBubble instanceof ChatMessage) {
			TextView message_view = (TextView) bubble
					.findViewById(R.id.text_message);
			message_view.setText(((ChatMessage) currentBubble).getMessage());
			LinearLayout wrapper = (LinearLayout) bubble
					.findViewById(R.id.wrapper);
			boolean ismine = currentBubble.isMine(1);
			if (ismine) {
				message_view.setBackgroundResource(R.drawable.bubble_green);
				wrapper.setGravity(Gravity.RIGHT);
			} else {
				message_view.setBackgroundResource(R.drawable.bubble_yellow);
				wrapper.setGravity(Gravity.LEFT);
			}

			return bubble;
		} else if (currentBubble instanceof ChatAttachment) {
			TextView attach_view = (TextView) bubble
					.findViewById(R.id.text_attachment);
			attach_view.setText("Questo � un allegato...");
			return bubble;
		}

		return bubble;
	}

	// public View getView(int position, View convertView, ViewGroup parent) {
	// View row = convertView;
	// if (row == null) {
	// LayoutInflater inflater = (LayoutInflater)
	// this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// row = inflater.inflate(R.layout.listitem_discuss, parent, false);
	// }
	//
	// wrapper = (LinearLayout) row.findViewById(R.id.wrapper);
	//
	// OneComment coment = getItem(position);
	//
	// countryName = (TextView) row.findViewById(R.id.comment);
	//
	// countryName.setText(coment.comment);
	//
	// countryName.setBackgroundResource(coment.left ? R.drawable.bubble_yellow
	// : R.drawable.bubble_green);
	// wrapper.setGravity(coment.left ? Gravity.LEFT : Gravity.RIGHT);
	//
	// return row;
	// }
	//
	// public Bitmap decodeToBitmap(byte[] decodedByte) {
	// return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	// }

}
