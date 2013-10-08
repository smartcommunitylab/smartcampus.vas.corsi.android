package eu.trentorise.smartcampus.android.studyMate.utilities;

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
import android.widget.Toast;
import eu.trentorise.smartcampus.android.common.Utils;

import eu.trentorise.smartcampus.android.studyMate.models.CorsoLaurea;
import eu.trentorise.smartcampus.android.studyMate.models.Dipartimento;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class FindDepartmentsHandler extends
		AsyncTask<Void, Void, List<Dipartimento>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	Spinner spinnerDepartments;
	Spinner spinnerDegree;
	public static ProgressDialog pd;
	List<Dipartimento> listDepartments = new ArrayList<Dipartimento>();
	public static List<Dipartimento> listaDip = null;
	public String departSelectedName = null;
	public String courseSelected = null;
	Dipartimento departSelected = null;
	CorsoLaurea corsoLaureaSelected;
	public List<Dipartimento> listDep;
	public List<CorsoLaurea> listCorLaurea;
	FindCoursesDegreeHandler findDegHandler;
	Activity currentActivity;

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		new ProgressDialog(currentActivity);
		pd = ProgressDialog.show(currentActivity, "Lista dei dipartimenti",
				"Caricamento...");

	}

	public FindDepartmentsHandler(Context applicationContext,
			Spinner spinnerDepartments, Spinner spinnerDegree,
			Activity currentActivity) {
		// TODO Auto-generated constructor stub
		this.context = applicationContext;
		this.spinnerDepartments = spinnerDepartments;
		this.spinnerDegree = spinnerDegree;
		this.currentActivity = currentActivity;
	}

	@Override
	protected List<Dipartimento> doInBackground(Void... params) {
		// TODO Auto-generated method stub

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_DEPARTMENTS_ALL);
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

		listDepartments = Utils.convertJSONToObjects(body, Dipartimento.class);
		if (listDepartments == null) {
			currentActivity.finish();
		} else {
			// aggiungo l'item "tutto" alla lista
			Dipartimento depTutto = new Dipartimento();
			depTutto.setNome("Tutto");
			listDepartments.add(0, depTutto);
		}
		return listDepartments;

	}

	@Override
	protected void onPostExecute(final List<Dipartimento> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		listaDip = result;
		if (result == null) {

			Toast.makeText(context, "Ops! C'è stato un errore...",
					Toast.LENGTH_SHORT).show();
			currentActivity.finish();
		} else {
			ArrayList<String> listStringDepartments = new ArrayList<String>();

			for (Dipartimento d : result) {
				listStringDepartments.add(d.getNome());
			}

			// setto i dipartimenti nello spinner
			@SuppressWarnings({ "rawtypes", "unchecked" })
			ArrayAdapter adapter = new ArrayAdapter(
					context,
					smartcampus.android.template.standalone.R.layout.list_studymate_row_list_simple,
					listStringDepartments);
			spinnerDepartments.setAdapter(adapter);
			pd.dismiss();
			spinnerDepartments
					.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?> parent,
								View view, int pos, long id) {

							// chiamo l'handler per il caricamento dei corsi di
							// laurea
							departSelected = result.get(pos);
							findDegHandler = (FindCoursesDegreeHandler) new FindCoursesDegreeHandler(
									context, spinnerDegree, departSelectedName,
									parent, pos, currentActivity,
									FindDepartmentsHandler.this).execute();

						}

						public void onNothingSelected(AdapterView<?> parent) {
						}
					});

		}
	}

	public Dipartimento getDepartSelected() {
		return departSelected;
	}

}
