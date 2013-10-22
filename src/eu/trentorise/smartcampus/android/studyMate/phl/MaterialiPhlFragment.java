package eu.trentorise.smartcampus.android.studyMate.phl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.android.studyMate.R;

public class MaterialiPhlFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_phl_materiali,
				container, false);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("MATERIALI PHL FRAGMENT");
		ListView listViewCorsi = (ListView) getSherlockActivity().findViewById(
				R.id.listViewPhlMateriali);

		Bundle arguments = new Bundle();
		PHLengine4Course handlerCoursesMaterial = new PHLengine4Course(
				getActivity().getApplicationContext(), getActivity(),
				listViewCorsi, getSherlockActivity());
		handlerCoursesMaterial.execute(arguments);
	}
}