package eu.trentorise.smartcampus.smartuni.utilities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.smartuni.models.CorsoLaurea;
import eu.trentorise.smartcampus.smartuni.models.CorsoLite;
import eu.trentorise.smartcampus.smartuni.models.Dipartimento;

public class CoursesLiteMeHandler extends
		AsyncTask<Void, Void, List<CorsoLite>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	public String appToken = "test smartcampus";
	public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	private Dipartimento department;
	private CorsoLaurea degree;
	private String course;
	private String body;
	public static ArrayList<CorsoLite> coursesFiltered;
	ListView listView;
	TextView tvTitleNotices;

	@Override
	protected List<CorsoLite> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}

}
