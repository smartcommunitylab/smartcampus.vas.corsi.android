package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.ChatMessage;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.Message;
import eu.trentorise.smartcampus.android.studyMate.utilities.ChatAdapter;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.NotificationCenterGds;
import eu.trentorise.smartcampus.android.studyMate.utilities.SharedUtils;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class Chat_Fragment extends SherlockFragment {

	private View view;

	ArrayList<Message> messages;
	ChatAdapter adapter;
	EditText text;
	static Random rand = new Random();
	static String sender;
	private ListView chat;
	private GruppoDiStudio contextualGDS;
	public static ProgressDialog pd;
	// This intent filter will be set to filter on the string
	// "GCM_RECEIVED_ACTION"
	IntentFilter gcmFilter;

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

		// Create our IntentFilter, which will be used in conjunction with a
		// broadcast receiver.
		gcmFilter = new IntentFilter();
		gcmFilter.addAction("GCM_RECEIVED_ACTION");

		text = (EditText) getActivity().findViewById(R.id.text);
		GetListMessages getMessagesTask = new GetListMessages();
		getMessagesTask.execute();

		Button sendBtn = (Button) getActivity().findViewById(R.id.send_chat);
		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMessage(v);
			}
		});
		super.onStart();
		getActivity().registerReceiver(gcmReceiver, gcmFilter);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getSherlockActivity().unregisterReceiver(gcmReceiver);
	}

	// A BroadcastReceiver must override the onReceive() event.
	private BroadcastReceiver gcmReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String message = intent.getExtras().getString("message");

			if (message != null) {
				// display our received message
				addNewMessage(new Message(message.toString().trim(), false));

				NotificationCenterGds notifCenter = new NotificationCenterGds(
						getActivity().getApplicationContext());
				notifCenter.deleteNotificationGds(contextualGDS.getId());
				NotificationManager nMgr = (NotificationManager) getActivity()
						.getApplicationContext().getSystemService(
								Context.NOTIFICATION_SERVICE);
				nMgr.cancelAll();
			}
		}
	};

	public void sendMessage(View v) {
		// String newMessage = text.getText().toString().trim();
		if (text.getText().toString().length() > 0) {
			// addNewMessage(new Message(newMessage, true));
			new SendMessage().execute();
		}
	}

	private class SendMessage extends AsyncTask<Void, Void, Boolean> {

		private ProtocolCarrier mProtocolCarrier;

		@Override
		protected Boolean doInBackground(Void... params) {
			mProtocolCarrier = new ProtocolCarrier(getActivity(),
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.POST_WS_MESSAGE_CHAT_GDS(
							contextualGDS.getId(), text.getText().toString()
									.trim()));
			request.setMethod(Method.POST);

			MessageResponse response;
			try {

				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {

					Boolean state = Boolean.valueOf(response.getBody());

					if (state != null) {
						return state;
					} else {
						return false;
					}

				}

			} catch (ConnectionException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (AACException e) {
				e.printStackTrace();
			}
			return false;

		}

		@Override
		protected void onPostExecute(Boolean operation) {

			if (operation) {

				if (messages.size() >= 1) {

					if (messages.get(messages.size() - 1).isStatusMessage()) {
						messages.remove(messages.size() - 1);
					}

					addNewMessage(new Message(text.getText().toString().trim(),
							true)); // add the orignal
					// message
					// from server.

				} else {
					text.setText("");
				}

			} else {
				text.setText("");
			}
		}

	}

	private class GetListMessages extends
			AsyncTask<Void, String, List<ChatMessage>> {

		private ProtocolCarrier mProtocolCarrier;
		// private List<ChatMessage> listMessages;
		private String bodyResponse;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			new ProgressDialog(getActivity());
			pd = ProgressDialog.show(
					getActivity(),
					getActivity().getResources().getString(
							R.string.chat_messages),
					getActivity().getResources().getString(
							R.string.dialog_loading));
		}

		@Override
		protected List<ChatMessage> doInBackground(Void... params) {

			mProtocolCarrier = new ProtocolCarrier(getActivity(),
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_MESSAGE_CHAT_GDS(contextualGDS
							.getId()));
			request.setMethod(Method.GET);

			MessageResponse response;
			try {

				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {

					bodyResponse = response.getBody();

				}

			} catch (ConnectionException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (AACException e) {
				e.printStackTrace();
			}

			return Utils.convertJSONToObjects(bodyResponse, ChatMessage.class);

		}

		@Override
		protected void onPostExecute(List<ChatMessage> listMessages) {
			// TODO Auto-generated method stub
			super.onPostExecute(listMessages);

			messages = new ArrayList<Message>();

			for (ChatMessage chatMessage : listMessages) {
				if (String.valueOf(chatMessage.getId_studente()).equals(
						SharedUtils.getStudentInfo(
								getActivity().getApplicationContext())
								.getUserId())) {
					messages.add(new Message(chatMessage.getTesto(), true));
				} else {
					messages.add(new Message(chatMessage.getTesto(), false));
				}
			}

			if (messages != null) {
				adapter = new ChatAdapter(getActivity(), messages);
				chat = (ListView) getActivity().findViewById(R.id.list_chat);
				chat.setAdapter(adapter);
				chat.setSelection(chat.getCount() - 1);
				// setListAdapter(adapter);
			}

			pd.dismiss();
		}

	}

	void addNewMessage(Message m) {
		messages.add(m);
		adapter.notifyDataSetChanged();
		chat.setSelection(messages.size() - 1);
		text.setText("");
	}

	@Override
	public void onResume() {
		super.onResume();

		NotificationCenterGds notifCenter = new NotificationCenterGds(
				getActivity().getApplicationContext());
		notifCenter.deleteNotificationGds(contextualGDS.getId());

	}
}
