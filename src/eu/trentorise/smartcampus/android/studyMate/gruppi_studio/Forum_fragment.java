package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.Allegato;
import eu.trentorise.smartcampus.android.studyMate.models.ChatAttachment;
import eu.trentorise.smartcampus.android.studyMate.models.ChatMessage;
import eu.trentorise.smartcampus.android.studyMate.models.ChatObject;

public class Forum_fragment extends SherlockFragment {

	ArrayList<ChatObject> forum;
	TextView chateText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.forum_fragment, container, false);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		getSherlockActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		// logic to popolate fragmentview here
		forum = ((Overview_GDS) getActivity()).getContextualForum();

		ListView chat = (ListView) getSherlockActivity().findViewById(
				R.id.forum_listview);

		chateText = (TextView) getSherlockActivity().findViewById(
				R.id.forum_edittext);

		ImageButton smile_btn = (ImageButton) getSherlockActivity()
				.findViewById(R.id.smile_button);

		ImageButton send_btn = (ImageButton) getSherlockActivity()
				.findViewById(R.id.send_button);

		/*
		 * per prova visto che tutti i gruppi fake che ho finora non hanno il
		 * forum inizializzato ci piazzo qlcs io qui
		 */

		Time now = new Time();
		now.setToNow();
		Allegato allegato = new Allegato(null);
		ChatMessage msg1 = new ChatMessage(now, "Finto messaggio1");
		ChatAttachment attach1 = new ChatAttachment(now, allegato);
		forum.add(msg1);// occio qua quando si far� sul serio col forum
		forum.add(attach1);// occio qua quando si far� sul serio col forum

		/*
		 * ora bisogna fare un arrayadapter per arraylist<chatobject> (dinamico
		 * rispetto all'istanza di chatobject). Poi bisogna settarglielo a chat.
		 */

		ChatObjectAdapter adapter = new ChatObjectAdapter(
				getSherlockActivity(), R.id.forum_listview, forum);
		chat.setAdapter(adapter);

		// ChatAdapter adapter=new ChatAdapter()
		// chat.setadapter(adapter)

		send_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String text = chateText.getText().toString();
				Time nunc = new Time();
				nunc.setToNow();
				ChatMessage newmessage = new ChatMessage(nunc, text);
				forum.add(newmessage);
				chateText.setText("");

			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater = getSherlockActivity().getSupportMenuInflater();
		inflater.inflate(R.menu.forum_gds_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.aggiungi_allegato: {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("file/*");
			// startActivity(Intent.createChooser(intent, "File Browser"));
			startActivityForResult(intent,
					MyApplication.PICK_FILE_FROM_PHONE_MEMORY);
		}
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
