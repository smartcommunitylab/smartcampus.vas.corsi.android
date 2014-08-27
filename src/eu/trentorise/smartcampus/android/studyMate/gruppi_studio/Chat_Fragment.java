package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
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

import eu.trentorise.smartcampus.android.studyMate.models.ChatMessage;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.Message;
import eu.trentorise.smartcampus.android.studyMate.utilities.ChatAdapter;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.NotificationCenterGds;
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

		text = (EditText) getActivity().findViewById(R.id.text);

		sender = UtilityProvvisoria.sender[rand
				.nextInt(UtilityProvvisoria.sender.length - 1)];
		getActivity().setTitle(sender);
		messages = new ArrayList<Message>();

		messages.add(new Message("Test", false));
		messages.add(new Message("Test?", true));
		messages.add(new Message("YUP", false));
		messages.add(new Message("LOL", true));
		messages.add(new Message("???!", true));
		messages.add(new Message("funge!", false));

		adapter = new ChatAdapter(getActivity(), messages);
		chat = (ListView) getActivity().findViewById(R.id.list_chat);
		chat.setAdapter(adapter);
		// setListAdapter(adapter);
		addNewMessage(new Message("=)", true));

		Button sendBtn = (Button) getActivity().findViewById(R.id.send_chat);
		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMessage(v);
			}
		});
		super.onStart();
	}

	public void sendMessage(View v) {
		//String newMessage = text.getText().toString().trim();
		if (text.getText().toString().length() > 0) {
			//addNewMessage(new Message(newMessage, true));
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

					if(state != null){
					return state; 
					}else{
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

				if (messages.get(messages.size() - 1).isStatusMessage())
				{
					messages.remove(messages.size() - 1);
				}

				addNewMessage(new Message(text.getText().toString().trim(), true)); // add the orignal
															// message
															// from server.

			}else{
				text.setText("");
			}
		}

	}

	private class GetListMessages extends
			AsyncTask<Void, String, List<ChatMessage>> {
		@Override
		protected List<ChatMessage> doInBackground(Void... params) {

			return null;

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
