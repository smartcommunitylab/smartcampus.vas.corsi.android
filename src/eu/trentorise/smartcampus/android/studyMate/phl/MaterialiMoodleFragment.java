package eu.trentorise.smartcampus.android.studyMate.phl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.android.studyMate.R;

public class MaterialiMoodleFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_phl_materiali_moodle,
				container, false);
		return view;
	}

	public void onStart() {
		super.onStart();
	}
}