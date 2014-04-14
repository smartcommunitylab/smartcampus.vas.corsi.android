package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.ArrayList;
import java.util.List;

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
import eu.trentorise.smartcampus.studymate.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class AsyncSetSharedCds extends AsyncTask<Void, Void, Boolean> {

	private Dipartimento dipartimento;
	private Context context;
	private AlertDialog.Builder builder;
	private Spinner spinner1;
	private Spinner spinnerDegree;
	public static ProgressDialog pd;

	private ProtocolCarrier mProtocolCarrier;
	String body = null;
	Spinner spinnerCorsiLaurea;
	List<CorsoLaurea> listCourseDegree = null;
	private Dipartimento departSelected;
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

	public AsyncSetSharedCds(Context context, Spinner spinnerDegree,
			Dipartimento departSelected, Activity currentActivity) {
		this.context = context;
		this.spinnerDegree = spinnerDegree;
		this.currentActivity = currentActivity;
		this.departSelected = departSelected;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		new ProgressDialog(currentActivity);
		pd = ProgressDialog.show(currentActivity, context.getResources()
				.getString(R.string.dialog_searching_list_departments), context
				.getResources().getString(R.string.dialog_loading));
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub

		mProtocolCarrier = new ProtocolCarrier(currentActivity,
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

		listCourseDegree = Utils.convertJSONToObjects(body, CorsoLaurea.class);

		if (listCourseDegree != null)
			return true;
		else
			return false;

	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		if (result) {
			ArrayList<String> listStringDegree = new ArrayList<String>();

			for (CorsoLaurea d : listCourseDegree) {
				listStringDegree.add(d.getDescripion());
			}

			// setto i corsi di laurea nello spinner
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					currentActivity,
					eu.trentorise.smartcampus.studymate.R.layout.list_studymate_row_list_simple,
					listStringDegree);

			spinnerDegree.setAdapter(adapter);

			// listener spinner corso laurea
			spinnerDegree
					.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?> parent,
								View view, int pos, long id) {

							corsoLaureaSelected = new CorsoLaurea();

							corsoLaureaSelected = listCourseDegree.get(pos);

							courseSelected = parent.getItemAtPosition(pos)
									.toString();

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {

						}
					});

			Button buttonSave = (Button) currentActivity
					.findViewById(R.id.buttonSave);

			buttonSave.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					boolean resultSharedDip = false;
					boolean resultSharedCds = false;

					try {
						resultSharedDip = SharedUtils.setDipartimentoStudente(
								currentActivity, departSelected);

						if (resultSharedDip) {
							resultSharedCds = SharedUtils.setCdsStudente(
									currentActivity, corsoLaureaSelected);
						} else {
							Toast.makeText(
									context,
									currentActivity.getResources().getString(
											R.string.dialog_error),
									Toast.LENGTH_SHORT).show();
							currentActivity.finish();
						}
						if (resultSharedCds) {
							Toast.makeText(
									context,
									currentActivity.getResources().getString(
											R.string.dialog_saved_pref),
									Toast.LENGTH_SHORT).show();
							Intent intentHome = new Intent(currentActivity,
									MyUniActivity.class);
							intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							currentActivity.startActivity(intentHome);
							currentActivity.finish();
						} else {
							Toast.makeText(
									context,
									currentActivity.getResources().getString(
											R.string.dialog_error),
									Toast.LENGTH_SHORT).show();
							currentActivity.finish();
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});

			pd.dismiss();
		} else {
			Toast.makeText(
					context,
					currentActivity.getResources().getString(
							R.string.dialog_error), Toast.LENGTH_SHORT).show();
			currentActivity.finish();
		}
	}

}
