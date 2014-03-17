package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.studymate.R;

public class DettailOfEventFragment4Courses extends SherlockFragment

{
	public Evento eventSelected = null;
	public View view = null;

	public DettailOfEventFragment4Courses(Evento evento) {
		this.eventSelected = evento;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_detail_of_event, container,
				false);

		return view;
	}

	@Override
	public void onStart() {
		getSherlockActivity().supportInvalidateOptionsMenu();
		setHasOptionsMenu(true);
		TextView tvTitleEvent = (TextView) view
				.findViewById(R.id.textTitleEvent);
		tvTitleEvent.setText(eventSelected.getTitle());

		TextView tvDateEvent = (TextView) view.findViewById(R.id.textDataEvent);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		tvDateEvent.setText(dateFormat.format(eventSelected.getEventoId()
				.getDate()));

		TextView tvOraEvent = (TextView) view.findViewById(R.id.textOraEvent);
		tvOraEvent.setText(eventSelected
				.getEventoId()
				.getStart()
				.toString()
				.subSequence(
						0,
						eventSelected.getEventoId().getStart().toString()
								.length() - 3)
				+ " - "
				+ eventSelected
						.getEventoId()
						.getStop()
						.toString()
						.subSequence(
								0,
								eventSelected.getEventoId().getStart()
										.toString().length() - 3));

		TextView tvDescEvent = (TextView) view
				.findViewById(R.id.textDescriptionEvent);
		tvDescEvent.setText(eventSelected.getType());

		TextView tvAulaEvent = (TextView) view.findViewById(R.id.textDescEvent);
		tvAulaEvent.setText(eventSelected.getTeacher());

		TextView tvLocationEvent = (TextView) view
				.findViewById(R.id.textLocationEvent);
		tvLocationEvent.setText(eventSelected.getRoom());
		super.onStart();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		if (eventSelected.getIdStudente() == 0) {
			inflater.inflate(R.menu.det_event, menu);
		} else {
			menu.clear();
		}
		super.onCreateOptionsMenu(menu, inflater);}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case R.id.menu_add_note:
			Toast.makeText(getSherlockActivity(), "TEST", Toast.LENGTH_SHORT)
					.show();
			return true;
		case R.id.menu_modify_event:
			Toast.makeText(getSherlockActivity(), "Coming soon!",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_delete_event:
			Toast.makeText(getSherlockActivity(), "Coming soon!",
					Toast.LENGTH_SHORT).show();
			return true;
		default:
			break;
		}
		return false;// super.onOptionsItemSelected(item);
	}
}
