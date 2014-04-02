package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DialogCourseFragment extends DialogFragment {

	
	private CorsoCarriera corsoSelezionato;
	
	public DialogCourseFragment(CorsoCarriera cs) {
		this.corsoSelezionato = cs;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
