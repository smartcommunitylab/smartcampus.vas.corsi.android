package eu.trentorise.smartcampus.android.studyMate.phl;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class Materiali4LevelPhlFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_phl_materiali_lv,
				container, false);
		// // do your view initialization here
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("PHL TEST");
		ListView listViewCorsi = (ListView) getSherlockActivity().findViewById(
				R.id.listViewPhlMateriali_lv);
		Bundle arguments = new Bundle();
		PHLengine4Material handlerCoursesMaterial = new PHLengine4Material(
				getActivity().getApplicationContext(), getActivity(),
				listViewCorsi, getSherlockActivity(), this.getArguments()
						.getString("res"));

		handlerCoursesMaterial.execute(arguments);

	}

}