package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.finder.FindHomeCourseActivity;
import eu.trentorise.smartcampus.android.studyMate.finder.ResultSearchedActivity;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoLaurea;
import eu.trentorise.smartcampus.android.studyMate.models.Dipartimento;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

public class CoursesHandlerLite extends AsyncTask<Void, Void, List<AttivitaDidattica>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	private Dipartimento department;
	private CorsoLaurea degree;
	private String course;
	private String body;
	public static ArrayList<AttivitaDidattica> coursesFiltered;
	ListView listView;
	TextView tvTitleNotices;
	Activity currentAct;
	public static AttivitaDidattica corsoSelezionato;

	public CoursesHandlerLite(Context applicationContext,
			Dipartimento department, CorsoLaurea degree, String course,
			ListView listView, TextView tvTitleNotices, Activity currentAct) {
		this.context = applicationContext;
		this.department = department;
		this.degree = degree;
		this.course = course;
		this.listView = listView;
		this.tvTitleNotices = tvTitleNotices;
		this.currentAct = currentAct;
	}

	// return list of all courses of all departments
	private List<AttivitaDidattica> getAllCourses() {
		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_ALLCOURSES_ATT_DIDATTICA);
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, MyUniActivity.getAuthToken());

			if (response.getHttpStatus() == 200) {

				body = response.getBody();

			} else {
				return null;
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (AACException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Utils.convertJSONToObjects(body, AttivitaDidattica.class);
	}

	// return all courses of a department
	private List<AttivitaDidattica> getAllCoursesOfDepartment(Dipartimento dep) {

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_ALLCOURSES_OF_DEPARTMENT(dep.getId()));
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, MyUniActivity.getAuthToken());

			if (response.getHttpStatus() == 200) {

				body = response.getBody();

			} else {
				return null;
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (AACException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Utils.convertJSONToObjects(body, AttivitaDidattica.class);
	}

	@SuppressWarnings("unused")
	private List<AttivitaDidattica> getFrequentedCourses() {

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_FREQUENTEDCOURSES);
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, MyUniActivity.getAuthToken());

			if (response.getHttpStatus() == 200) {

				body = response.getBody();

			} else {
				return null;
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (AACException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Utils.convertJSONToObjects(body, AttivitaDidattica.class);
	}

	@Override
	protected List<AttivitaDidattica> doInBackground(Void... params) {

		if (department.getDescription().contains("Tutto")) {
			return getAllCourses();

		} else {
			if (degree.getDescripion().equals("Tutto")) {
				return getAllCoursesOfDepartment(department);
			} else {
				return getAllCoursesOfFaculty(degree);
			}
		}

	}

	// return all courses of a degree
	private List<AttivitaDidattica> getAllCoursesOfFaculty(CorsoLaurea deg) {
		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_ALLCOURSES_OF_DEGREE(String.valueOf(deg.getCdsId())));
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, MyUniActivity.getAuthToken());

			if (response.getHttpStatus() == 200) {

				body = response.getBody();

			} else {
				return null;
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (AACException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Utils.convertJSONToObjects(body, AttivitaDidattica.class);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (course.equals("")) {
			if (department.equals("Tutto"))
				tvTitleNotices.setText(tvTitleNotices.getText() + "Tutto");
			else if (degree.equals("Tutto"))
				tvTitleNotices
						.setText(tvTitleNotices.getText() + " Dipartimento di "
								+ department.getDescription().toString());
			else
				tvTitleNotices
						.setText(tvTitleNotices.getText() + " Dipartimento di "
								+ department.getDescription().toString()
								+ ", corso di laurea in "
								+ degree.getDescripion());
		} else {
			if (department.equals("Tutto")) {
				if (degree.equals("Tutto"))
					tvTitleNotices.setText(tvTitleNotices.getText() + " "
							+ course.toString());
				else
					tvTitleNotices.setText(tvTitleNotices.getText() + " "
							+ course.toString() + " del corso di laurea in "
							+ degree.getDescripion());
			} else {
				if (degree.equals("Tutto"))
					tvTitleNotices.setText(tvTitleNotices.getText() + " "
							+ course.toString() + " del dipartimento di "
							+ department.getDescription().toString());
				else
					tvTitleNotices.setText(tvTitleNotices.getText() + " "
							+ course.toString() + " del dipartimento di "
							+ department.getDescription().toString()
							+ ", corso di laurea in "
							+ degree.getDescripion());
			}

		}
	}

	@Override
	protected void onPostExecute(List<AttivitaDidattica> courses) {
		super.onPostExecute(courses);

		ResultSearchedActivity.pd.dismiss();

		if (courses == null) {
			setVoidCourses();
		} else
			setListCourses(courses);

	}

	// setto la lista dei corsi e la filtro
	private void setListCourses(final List<AttivitaDidattica> courses) {

		FilterSearched filter = new FilterSearched();
		coursesFiltered = filter.filterListWithCourseSearched(course, courses);

		ArrayList<String> coursesFiltered_onlyName = new ArrayList<String>();

		for (int i = 0; i < coursesFiltered.size(); i++) {
			coursesFiltered_onlyName.add(i, coursesFiltered.get(i).getDescription());
		}

		ArrayAdapter<String> adapterCursesList = new ArrayAdapter<String>(
				context, R.layout.list_studymate_row_list_simple,
				coursesFiltered_onlyName);

		listView.setAdapter(adapterCursesList);

		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String courseSelectedName = (String) arg0
						.getItemAtPosition(arg2);
				Intent i = new Intent(context, FindHomeCourseActivity.class);

				AttivitaDidattica corsoSelezionato = new AttivitaDidattica();

				corsoSelezionato.setAdId(courses.get(arg2).getAdId());
				corsoSelezionato.setDescription(courses.get(arg2).getDescription());
				corsoSelezionato.setCds_id(courses.get(arg2)
						.getCds_id());
				corsoSelezionato.setAdCod(courses.get(arg2).getAdCod());
				CoursesHandlerLite.corsoSelezionato = corsoSelezionato; 
				//CoursesHandler.corsoSelezionato = (AttivitaDidattica) corsoSelezionato;

				i.putExtra("courseSelected", corsoSelezionato);
				i.putExtra("courseSelectedName", courseSelectedName);
				i.putExtra("courseSelectedId", getIDCourseSelected(arg2));
				currentAct.startActivity(i);
			}

		});

	}

	private void setVoidCourses() {

	}

	public static long getIDCourseSelected(int position) {
		return coursesFiltered.get(position).getAdId();
	}

}
