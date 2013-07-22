package smartcampus.android.template.standalone;

import smartcampus.android.template.standalone.MyAgendaActivity.MenuKind;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.smartuni.utilities.CoursesHandler;

public class CorsiFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_corsi,
				container, false);
		// // do your view initialization here
		return view;
	}

	public void onStart() {
		super.onStart();
		MyAgendaActivity parent = (MyAgendaActivity) getActivity();
		parent.setAgendaState(MenuKind.BASE_MENU);

		ListView listViewCorsi = (ListView) getSherlockActivity().findViewById(
				R.id.listViewCorsi);
		getSherlockActivity().invalidateOptionsMenu();
		// String[] corsi = getResources().getStringArray(R.array.Corsi);
		// String[] corsiInt = getResources().getStringArray(
		// R.array.CorsiInteresse);
		// TitledItem[] items = new TitledItem[corsi.length + corsiInt.length];

		Bundle arguments = new Bundle();
		CoursesHandler handlerPersonalCourses = new CoursesHandler(
				getActivity().getApplicationContext(), listViewCorsi,
				getActivity(), getSherlockActivity());
		handlerPersonalCourses.execute(arguments);

	}
}
