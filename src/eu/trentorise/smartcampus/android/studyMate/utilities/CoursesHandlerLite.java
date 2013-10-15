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
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.finder.FindHomeCourseActivity;
import eu.trentorise.smartcampus.android.studyMate.finder.ResultSearchedActivity;
import eu.trentorise.smartcampus.android.studyMate.models.Corso;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoLaurea;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoLite;
import eu.trentorise.smartcampus.android.studyMate.models.Dipartimento;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class CoursesHandlerLite extends AsyncTask<Void, Void, List<Corso>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	public String appToken = "test smartcampus";
	public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	private Dipartimento department;
	private CorsoLaurea degree;
	private String course;
	private String body;
	public static ArrayList<Corso> coursesFiltered;
	ListView listView;
	TextView tvTitleNotices;
	Activity currentAct;
	public static Corso corsoSelezionato;

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
	private List<Corso> getAllCourses() {
		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_ALLCOURSES);
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, SmartUniDataWS.TOKEN);

			if (response.getHttpStatus() == 200) {

				body = response.getBody();

			} else {
				return null;
			}
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Utils.convertJSONToObjects(body, Corso.class);
	}

	// return all courses of a department
	private List<Corso> getAllCoursesOfDepartment(Dipartimento dep) {

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_ALLCOURSES_OF_DEPARTMENT(dep.getId()));
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, SmartUniDataWS.TOKEN);

			if (response.getHttpStatus() == 200) {

				body = response.getBody();

			} else {
				return null;
			}
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Utils.convertJSONToObjects(body, Corso.class);
	}

	@SuppressWarnings("unused")
	private List<CorsoLite> getFrequentedCourses() {

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_FREQUENTEDCOURSES);
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, SmartUniDataWS.TOKEN);

			if (response.getHttpStatus() == 200) {

				body = response.getBody();

			} else {
				return null;
			}
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Utils.convertJSONToObjects(body, CorsoLite.class);
	}

	@Override
	protected List<Corso> doInBackground(Void... params) {
		// TODO Auto-generated method stub

		if (department.getNome().equals("Tutto")) {
			return getAllCourses();

		} else {
			if (degree.getNome().equals("Tutto")) {
				return getAllCoursesOfDepartment(department);
			} else {
				return getAllCoursesOfFaculty(degree);
			}
		}

	}

	// return all courses of a degree
	private List<Corso> getAllCoursesOfFaculty(CorsoLaurea deg) {
		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_ALLCOURSES_OF_DEGREE(deg.getId()));
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, SmartUniDataWS.TOKEN);

			if (response.getHttpStatus() == 200) {

				body = response.getBody();

			} else {
				return null;
			}
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Utils.convertJSONToObjects(body, Corso.class);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if (course.equals("")) {
			if (department.equals("Tutto"))
				tvTitleNotices.setText(tvTitleNotices.getText() + "Tutto");
			else if (degree.equals("Tutto"))
				tvTitleNotices
						.setText(tvTitleNotices.getText() + " Dipartimento di "
								+ department.getNome().toString());
			else
				tvTitleNotices
						.setText(tvTitleNotices.getText() + " Dipartimento di "
								+ department.getNome().toString()
								+ ", corso di laurea in "
								+ degree.getNome().toString());
		} else {
			if (department.equals("Tutto")) {
				if (degree.equals("Tutto"))
					tvTitleNotices.setText(tvTitleNotices.getText() + " "
							+ course.toString());
				else
					tvTitleNotices.setText(tvTitleNotices.getText() + " "
							+ course.toString() + " del corso di laurea in "
							+ degree.getNome().toString());
			} else {
				if (degree.equals("Tutto"))
					tvTitleNotices.setText(tvTitleNotices.getText() + " "
							+ course.toString() + " del dipartimento di "
							+ department.getNome().toString());
				else
					tvTitleNotices.setText(tvTitleNotices.getText() + " "
							+ course.toString() + " del dipartimento di "
							+ department.getNome().toString()
							+ ", corso di laurea in "
							+ degree.getNome().toString());
			}

		}
	}

	@Override
	protected void onPostExecute(List<Corso> courses) {
		// TODO Auto-generated method stub
		super.onPostExecute(courses);

		ResultSearchedActivity.pd.dismiss();

		if (courses == null) {
			setVoidCourses();
		} else
			setListCourses(courses);

	}

	// setto la lista dei corsi e la filtro
	private void setListCourses(final List<Corso> courses) {

		FilterSearched filter = new FilterSearched();
		coursesFiltered = filter.filterListWithCourseSearched(course, courses);

		ArrayList<String> coursesFiltered_onlyName = new ArrayList<String>();

		for (int i = 0; i < coursesFiltered.size(); i++) {
			coursesFiltered_onlyName.add(i, coursesFiltered.get(i).getNome());
		}

		ArrayAdapter<String> adapterCursesList = new ArrayAdapter<String>(
				context, R.layout.list_studymate_row_list_simple,
				coursesFiltered_onlyName);

		listView.setAdapter(adapterCursesList);

		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) { // TODO Auto-generated method stub String
				String courseSelectedName = (String) arg0
						.getItemAtPosition(arg2);
				Intent i = new Intent(context, FindHomeCourseActivity.class);

				Corso corsoSelezionato = new Corso();

				corsoSelezionato.setId(courses.get(arg2).getId());
				corsoSelezionato.setNome(courses.get(arg2).getNome());
				corsoSelezionato.setId_dipartimento(courses.get(arg2)
						.getId_dipartimento());

				CoursesHandler.corsoSelezionato = (Corso) corsoSelezionato;

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
		// TODO Auto-generated method stub
		return coursesFiltered.get(position).getId();
	}

}
