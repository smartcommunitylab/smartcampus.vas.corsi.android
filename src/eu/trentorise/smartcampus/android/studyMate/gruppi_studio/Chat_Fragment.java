package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import eu.trentorise.smartcampus.android.studyMate.R;

import java.util.ArrayList;
import java.util.Random;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.Message;
import eu.trentorise.smartcampus.android.studyMate.utilities.ChatAdapter;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.NotificationCenterGds;

public class Chat_Fragment extends SherlockFragment {

	private View view;

	ArrayList<Message> messages;
	ChatAdapter adapter;
	EditText text;
	static Random rand = new Random();
	static String sender;
	private ListView chat;
	private GruppoDiStudio contextualGDS;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_chat, container, false);
		return view;
	}

	@Override
	public void onStart() {
		
		Bundle myextras = getActivity().getIntent().getExtras();
		contextualGDS = (GruppoDiStudio) myextras.get(Constants.CONTESTUAL_GDS);

		NotificationCenterGds notifCenter = new NotificationCenterGds(getActivity().getApplicationContext());
		notifCenter.deleteNotificationGds(contextualGDS.getId());
		
		
		text = (EditText) getActivity().findViewById(R.id.text);

		sender = UtilityProvvisoria.sender[rand
				.nextInt(UtilityProvvisoria.sender.length - 1)];
		getActivity().setTitle(sender);
		messages = new ArrayList<Message>();

		messages.add(new Message("Test", false));
		messages.add(new Message("Test?", true));
		messages.add(new Message("YUP", false));
		messages.add(new Message("LOL",
				true));
		messages.add(new Message("???!", true));
		messages.add(new Message("funge!",
				false));

		adapter = new ChatAdapter(getActivity(), messages);
		chat = (ListView) getActivity().findViewById(R.id.list_chat);
		chat.setAdapter(adapter);
//		setListAdapter(adapter);
		addNewMessage(new Message(
				"=)", true));
		
		Button sendBtn = (Button) getActivity().findViewById(R.id.send_chat);
		sendBtn.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						sendMessage(v);
					}
				});
		super.onStart();
	}

	public void sendMessage(View v) {
		String newMessage = text.getText().toString().trim();
		if (newMessage.length() > 0) {
			text.setText("");
			addNewMessage(new Message(newMessage, true));
			new SendMessage().execute();
		}
	}

	private class SendMessage extends AsyncTask<Void, String, String> {
		@Override
		protected String doInBackground(Void... params) {
			try {
				Thread.sleep(2000); // simulate a network call
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			this.publishProgress(String.format("%s started writing", sender));
			try {
				Thread.sleep(2000); // simulate a network call
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.publishProgress(String.format("%s has entered text", sender));
			try {
				Thread.sleep(3000);// simulate a network call
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return UtilityProvvisoria.messages[rand
					.nextInt(UtilityProvvisoria.messages.length - 1)];

		}

		@Override
		public void onProgressUpdate(String... v) {

			if (messages.get(messages.size() - 1).isStatusMessage())// check
																	// wether we
																	// have
																	// already
																	// added a
																	// status
																	// message
			{
				messages.get(messages.size() - 1).setMessage(v[0]); // update
																	// the
																	// status
																	// for that
				adapter.notifyDataSetChanged();
				chat.setSelection(messages.size() - 1);
			} else {
				addNewMessage(new Message(true, v[0])); // add new message, if
														// there is no existing
														// status message
			}
		}

		@Override
		protected void onPostExecute(String text) {
			if (messages.get(messages.size() - 1).isStatusMessage())// check if
																	// there is
																	// any
																	// status
																	// message,
																	// now
																	// remove
																	// it.
			{
				messages.remove(messages.size() - 1);
			}

			addNewMessage(new Message(text, false)); // add the orignal message
														// from server.
		}

	}

	void addNewMessage(Message m) {
		messages.add(m);
		adapter.notifyDataSetChanged();
		chat.setSelection(messages.size() - 1);
	}
}
