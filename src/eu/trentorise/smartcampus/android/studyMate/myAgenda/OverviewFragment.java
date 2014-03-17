package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventsHandler;
import eu.trentorise.smartcampus.studymate.R;

public class OverviewFragment extends SherlockFragment {

	public static ProgressDialog pd;
	public ListView listViewEventi;
	public static List<Evento> listaEventi;
	public EventsHandler eventsHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_overview,
				container, false);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onStart() {
		super.onStart();
		setHasOptionsMenu(true);
		getSherlockActivity().supportInvalidateOptionsMenu();

		new ProgressDialog(getActivity());
		OverviewFragment.pd = ProgressDialog.show(getActivity(),
				"Lista degli eventi personali", "Caricamento...");

		eventsHandler = new EventsHandler(
				getActivity().getApplicationContext(), getActivity());
		eventsHandler.execute();

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.clear();
		inflater.inflate(R.menu.agenda, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case R.id.add_event:
			Intent intentEvent = new Intent(getActivity(),
					AddEventActivity.class);
			intentEvent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentEvent);
			return true;
		default:
			break;
		}
		return false;// super.onOptionsItemSelected(item);
	}
}
