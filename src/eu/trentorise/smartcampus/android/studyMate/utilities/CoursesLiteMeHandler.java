package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoLite;

public class CoursesLiteMeHandler extends
		AsyncTask<Void, Void, List<CorsoLite>> {
	
	public Context context;
	public String appToken = "test smartcampus";
	public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	public static ArrayList<CorsoLite> coursesFiltered;
	ListView listView;
	TextView tvTitleNotices;

	@Override
	protected List<CorsoLite> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}

}
