package smartcampus.android.template.standalone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class DettailOfEventFragment4Courses extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_detail_of_event_4_course,
				container, false);
		return view;
	}
	@Override
	public void onStart() {
		MyAgendaActivity parent = (MyAgendaActivity) getActivity();
		parent.setAgendaState(3);
		getActivity().invalidateOptionsMenu();
		super.onStart();
	}
}
