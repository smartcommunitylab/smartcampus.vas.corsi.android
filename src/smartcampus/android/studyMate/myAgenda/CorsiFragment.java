package smartcampus.android.studyMate.myAgenda;

import smartcampus.android.studyMate.myAgenda.MyAgendaActivity.MenuKind;
import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.studyMate.utilities.CoursesHandler;

public class CorsiFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_corsi,
				container, false);
		return view;
	}

	public void onStart() {
		super.onStart();
		MyAgendaActivity parent = (MyAgendaActivity) getActivity();
		parent.setAgendaState(MenuKind.BASE_MENU);
		ListView listViewCorsi = (ListView) getSherlockActivity().findViewById(
				R.id.listViewCorsi);
		getSherlockActivity().supportInvalidateOptionsMenu();
		Bundle arguments = new Bundle();
		CoursesHandler handlerPersonalCourses = new CoursesHandler(
				getActivity().getApplicationContext(), listViewCorsi,
				getActivity(), getSherlockActivity());
		handlerPersonalCourses.execute(arguments);

	}
}
