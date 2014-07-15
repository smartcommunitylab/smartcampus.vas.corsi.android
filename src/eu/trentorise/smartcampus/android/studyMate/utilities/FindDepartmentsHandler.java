package eu.trentorise.smartcampus.android.studyMate.utilities;

import eu.trentorise.smartcampus.android.studyMate.R;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
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

	public FindDepartmentsHandler(Context applicationContext,
			Spinner spinnerDepartments, Spinner spinnerDegree,
			Activity currentActivity) {
		this.context = applicationContext;
		this.spinnerDepartments = spinnerDepartments;
		this.spinnerDegree = spinnerDegree;
		this.currentActivity = currentActivity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		new ProgressDialog(currentActivity);
		pd = ProgressDialog.show(currentActivity, context.getResources()
				.getString(R.string.dialog_searching_list_departments), context
				.getResources().getString(R.string.dialog_loading));

	}

	@Override
	protected List<Dipartimento> doInBackground(Void... params) {

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_DEPARTMENTS_ALL);
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

		listDepartments = Utils.convertJSONToObjects(body, Dipartimento.class);
		if (listDepartments == null) {
			currentActivity.finish();
		}
		return listDepartments;

	}

	@Override
	protected void onPostExecute(final List<Dipartimento> result) {
		super.onPostExecute(result);
		listaDip = result;
		if (result == null) {

			Toast.makeText(context,
					context.getResources().getString(R.string.dialog_error),
					Toast.LENGTH_SHORT).show();
			currentActivity.finish();
		} else {
			ArrayList<String> listStringDepartments = new ArrayList<String>();

			for (Dipartimento d : result) {
				listStringDepartments.add(d.getDescription());
			}

			// setto i dipartimenti nello spinner
			@SuppressWarnings({ "rawtypes", "unchecked" })
			ArrayAdapter adapter = new ArrayAdapter(context,
					R.layout.list_studymate_row_list_simple,
					listStringDepartments);
			spinnerDepartments.setAdapter(adapter);
			pd.dismiss();

			departSelected = SharedUtils
					.getDipartimentoStudente(currentActivity);
			int posSelected = SharedUtils.getPosListFromShared(
					listStringDepartments, departSelected.getDescription());
			spinnerDepartments.setSelection(SharedUtils.getPosListFromShared(
					listStringDepartments, departSelected.getDescription()),
					true);

			if (departSelected != null) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					findDegHandler = (FindCoursesDegreeHandler) new FindCoursesDegreeHandler(
							context, spinnerDegree, departSelectedName,
							posSelected, currentActivity,
							FindDepartmentsHandler.this).executeOnExecutor(
							AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
				} else {
					findDegHandler = (FindCoursesDegreeHandler) new FindCoursesDegreeHandler(
							context, spinnerDegree, departSelectedName,
							posSelected, currentActivity,
							FindDepartmentsHandler.this).execute();
				}
			}

			spinnerDepartments
					.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?> parent,
								View view, int pos, long id) {

							// chiamo l'handler per il caricamento dei corsi di
							// laurea
							departSelected = result.get(pos);

							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
								findDegHandler = (FindCoursesDegreeHandler) new FindCoursesDegreeHandler(
										context, spinnerDegree,
										departSelectedName, pos,
										currentActivity,
										FindDepartmentsHandler.this)
										.executeOnExecutor(
												AsyncTask.THREAD_POOL_EXECUTOR,
												(Void[]) null);
							} else {
								findDegHandler = (FindCoursesDegreeHandler) new FindCoursesDegreeHandler(
										context, spinnerDegree,
										departSelectedName, pos,
										currentActivity,
										FindDepartmentsHandler.this).execute();
							}

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
