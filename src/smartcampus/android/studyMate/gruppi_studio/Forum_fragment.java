package smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.model_classes.Allegato;
import com.example.model_classes.ChatAttachment;
import com.example.model_classes.ChatMessage;
import com.example.model_classes.ChatObject;

public class Forum_fragment extends SherlockFragment {

	ArrayList<ChatObject> forum;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.forum_fragment, container, false);
		return view;
	}

	
	@Override
	public void onStart() {
		super.onStart();
		// logic to popolate fragmentview here
		forum = ((Overview_GDS) getActivity()).getContextualForum();

		ListView chat = (ListView) getSherlockActivity().findViewById(
				R.id.forum_listview);

		TextView chateText = (TextView) getSherlockActivity().findViewById(
				R.id.forum_edittext);

		/*
		 * per prova visto che tutti i gruppi fake che ho finora non hanno il
		 * forum inizializzato ci piazzo qlcs io qui
		 */

		Time now = new Time();
		now.setToNow();
		Allegato allegato = new Allegato(null);
		ChatMessage msg1 = new ChatMessage(now, "Finto messaggio1");
		ChatAttachment attach1 = new ChatAttachment(now, allegato);
		forum.add(msg1);// occio qua quando si farà sul serio col forum
		forum.add(attach1);// occio qua quando si farà sul serio col forum

		/*
		 * ora bisogna fare un arrayadapter per arraylist<chatobject> (dinamico
		 * rispetto all'istanza di chatobject). Poi bisogna settarglielo a chat.
		 */

		ChatObjectAdapter adapter = new ChatObjectAdapter(
				getSherlockActivity(), R.id.forum_listview, forum);
		chat.setAdapter(adapter);

		// ChatAdapter adapter=new ChatAdapter()
		// chat.setadapter(adapter)

	}

}
