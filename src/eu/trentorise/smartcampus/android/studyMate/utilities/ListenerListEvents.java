package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.DettailOfEventFragment;

public class ListenerListEvents implements OnItemClickListener {

	FragmentActivity act;
	List<Evento> listEvents;
	int id;
	String tag;
	
	
	public ListenerListEvents(FragmentActivity act, List<Evento> listEvents, int id, String tag) {
		this.act = act;
		this.listEvents = listEvents;
		this.id = id;
		this.tag = tag;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		act.supportInvalidateOptionsMenu();

		Evento evento = listEvents.get(arg2);

		// Pass Data to other Fragment
		Bundle arguments = new Bundle();
		arguments.putSerializable(Constants.SELECTED_EVENT, evento);
//		FragmentTransaction ft = act
//				.getSupportFragmentManager().beginTransaction();
//		Fragment fragment = new DettailOfEventFragment();
//		fragment.setArguments(arguments);
//		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//		ft.replace(id, fragment, tag);
//		ft.addToBackStack(tag);
//		ft.commit();
	}

}