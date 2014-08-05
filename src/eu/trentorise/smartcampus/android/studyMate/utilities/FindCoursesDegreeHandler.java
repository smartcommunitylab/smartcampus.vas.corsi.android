package eu.trentorise.smartcampus.android.studyMate.utilities;

import eu.trentorise.smartcampus.android.studyMate.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.R;

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
import eu.trentorise.smartcampus.ac.AACException;
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
	int pos;
	public static CorsoLaurea corsoLaureaSelected = null;
	public String courseSelected = null;
	public List<CorsoLaurea> listCorLaurea;
	FindCoursesDegreeHandler findDegHandler;
	Activity currentActivity;
	FindDepartmentsHandler findDepartHandler;

	public FindCoursesDegreeHandler(Context applicationContext,
			Spinner spinnerCorsiLaurea, String departSelectedName, int pos,
			Activity currentActivity, FindDepartmentsHandler findDepartHandler) {
		this.context = applicationContext;
		this.spinnerCorsiLaurea = spinnerCorsiLaurea;
		this.departSelectedName = departSelectedName;
		this.pos = pos;
		this.currentActivity = currentActivity;
		this.findDepartHandler = findDepartHandler;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		new ProgressDialog(currentActivity);
		pd = ProgressDialog.show(currentActivity, context.getResources()
				.getString(R.string.dialog_list_cds), context.getResources()
				.getString(R.string.dialog_loading));

	}

	@SuppressWarnings("static-access")
	@Override
	protected List<CorsoLaurea> doInBackground(Void... params) {

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

		// aggiungo l'item "tutto" alla lista
		// CorsoLaurea courseTutto = new CorsoLaurea();
		// courseTutto.setDescripion("Tutto");
		// listCourseDegree.add(0, courseTutto);

		return listCourseDegree;

	}

	@Override
	protected void onPostExecute(final List<CorsoLaurea> result) {
		super.onPostExecute(result);
		if (result == null) {

			Toast.makeText(context,
					context.getResources().getString(R.string.dialog_error),
					Toast.LENGTH_SHORT).show();
			currentActivity.finish();
		} else {
			ArrayList<String> listStringDegree = new ArrayList<String>();

			for (CorsoLaurea d : result) {
				listStringDegree.add(d.getDescripion());
			}

			spinnerCorsiLaurea.setEnabled(true);
			spinnerCorsiLaurea.setClickable(true);

			// setto i corsi di laurea nello spinner
			@SuppressWarnings({ "unchecked", "rawtypes" })
			ArrayAdapter adapter = new ArrayAdapter(context,
					R.layout.list_studymate_row_list_simple, listStringDegree);
			spinnerCorsiLaurea.setAdapter(adapter);
			corsoLaureaSelected = SharedUtils.getCdsStudente(currentActivity);
			spinnerCorsiLaurea.setSelection(SharedUtils.getPosListFromShared(
					listStringDegree, corsoLaureaSelected.getDescripion()),
					true);

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

						}
					});

			pd.dismiss();
		}
	}

	public CorsoLaurea getCorsoLaureaSelected() {
		return corsoLaureaSelected;
	}

}
