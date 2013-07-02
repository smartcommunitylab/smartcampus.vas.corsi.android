package eu.trentorise.smartcampus.smartuni.utilities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.smartuni.models.CorsoLaurea;
import eu.trentorise.smartcampus.smartuni.models.Dipartimento;

public class FindCoursesDegreeHandler extends
		AsyncTask<Void, Void, List<CorsoLaurea>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body = null;
	Spinner spinnerCorsiLaurea;
	Dipartimento departSelected = null;
	List<CorsoLaurea> listCourseDegree = null;
	public static ProgressDialog pd;
	String departSelectedName;
	public List<Dipartimento> listDep;
	FindDepartmentsHandler findDepHandler;
	AdapterView<?> parent;
	int pos;
	public static CorsoLaurea corsoLaureaSelected = null;
	public String courseSelected = null;
	public List<CorsoLaurea> listCorLaurea;
	FindCoursesDegreeHandler findDegHandler;
	Activity currentActivity;
	FindDepartmentsHandler findDepartHandler;

	public FindCoursesDegreeHandler(Context applicationContext,
			Spinner spinnerCorsiLaurea, String departSelectedName,
			AdapterView<?> parent, int pos, Activity currentActivity,
			FindDepartmentsHandler findDepartHandler) {
		this.context = applicationContext;
		this.spinnerCorsiLaurea = spinnerCorsiLaurea;
		this.departSelectedName = departSelectedName;
		this.parent = parent;
		this.pos = pos;
		this.currentActivity = currentActivity;
		this.findDepartHandler = findDepartHandler;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		new ProgressDialog(currentActivity);
		pd = ProgressDialog.show(currentActivity, "Lista dei corsi di laurea",
				"Caricamento...");

	}

	@SuppressWarnings("static-access")
	@Override
	protected List<CorsoLaurea> doInBackground(Void... params) {
		// TODO Auto-generated method stub

		departSelectedName = parent.getItemAtPosition(pos).toString();

		departSelected = new Dipartimento();

		departSelected = findDepartHandler.listaDip.get(pos);

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS
						.GET_WS_COURSESDEGREE_OF_DEPARTMENT(departSelected
								.getId()));
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

		listCourseDegree = Utils.convertJSONToObjects(body, CorsoLaurea.class);

		// aggiungo l'item "tutto" alla lista
		CorsoLaurea courseTutto = new CorsoLaurea();
		courseTutto.setNome("Tutto");
		listCourseDegree.add(0, courseTutto);

		return listCourseDegree;

	}

	@Override
	protected void onPostExecute(final List<CorsoLaurea> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		ArrayList<String> listStringDegree = new ArrayList<String>();

		for (CorsoLaurea d : result) {
			listStringDegree.add(d.getNome());
		}

		// setto i corsi di laurea nello spinner
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayAdapter adapter = new ArrayAdapter(
				context,
				smartcampus.android.template.standalone.R.layout.list_studymate_row_list_simple,
				listStringDegree);
		spinnerCorsiLaurea.setAdapter(adapter);

		// listener spinner corso laurea
		spinnerCorsiLaurea
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {

						corsoLaureaSelected = new CorsoLaurea();

						corsoLaureaSelected = result.get(pos);

						courseSelected = parent.getItemAtPosition(pos)
								.toString();

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		pd.dismiss();

	}

	public CorsoLaurea getCorsoLaureaSelected() {
		return corsoLaureaSelected;
	}

}
