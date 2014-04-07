package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import com.actionbarsherlock.view.Window;

import eu.trentorise.smartcampus.android.studyMate.finder.FindHomeCourseActivity;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.studymate.R;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DialogCourseFragment extends DialogFragment {

	private CorsoCarriera corsoSelezionato;

	public DialogCourseFragment(CorsoCarriera cs) {
		this.corsoSelezionato = cs;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.dialog_courses_layout, container,
				true);
		//DialogCourseFragment.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.list_studymate_row_list_simple);

		ListView mLocationList = (ListView) v.findViewById(R.id.lv);

		if (corsoSelezionato.getResult().equals("-1")) {

			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_list_item_1,
					getActivity().getResources().getStringArray(
							R.array.dialogAgendaInterest));
			mLocationList.setAdapter(adapter);

		} else {
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_list_item_1,
					getActivity().getResources().getStringArray(
							R.array.dialogAgendaCareer));
			mLocationList.setAdapter(adapter);
		}
		
		getDialog().setTitle(corsoSelezionato.getName());
		
		getDialog().show();

		
		
		
		
		
		
//		mLocationList.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		
//		
//		Intent i = new Intent(context, FindHomeCourseActivity.class);
//
//		AttivitaDidattica corsoSelezionato = new AttivitaDidattica();
//
//		corsoSelezionato.setAdId(coursesFiltered.get(arg2).getAdId());
//		corsoSelezionato.setDescription(coursesFiltered.get(arg2)
//				.getDescription());
//		corsoSelezionato.setCds_id(coursesFiltered.get(arg2)
//				.getCds_id());
//		corsoSelezionato.setAdCod(coursesFiltered.get(arg2).getAdCod());
//
//		i.putExtra(Constants.COURSE_NAME,
//				corsoSelezionato.getDescription());
//		i.putExtra(Constants.COURSE_ID, corsoSelezionato.getAdId());
//		i.putExtra(Constants.AD_COD, corsoSelezionato.getAdCod());
//		currentAct.startActivity(i);
		
		
		

		return v;
	}
}
