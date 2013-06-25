package eu.trentorise.smartcampus.smartuni.utilities;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
import eu.trentorise.smartcampus.smartuni.models.Dipartimento;

public class FindDepartmentsHandler extends
		AsyncTask<Void, Void, List<Dipartimento>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	Spinner spinnerDepartments;
	List<Dipartimento> listDepartments = new ArrayList<Dipartimento>();
	ProgressDialog pd;


	public FindDepartmentsHandler(Context applicationContext,
			Spinner spinnerDepartments) {
		// TODO Auto-generated constructor stub
		this.context = applicationContext;
		this.spinnerDepartments = spinnerDepartments;
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

		// aggiungo l'item "tutto" alla lista
		Dipartimento depTutto = new Dipartimento();
		depTutto.setNome("Tutto");
		listDepartments.add(0, depTutto);

		return listDepartments;

	}

	@Override
	protected void onPostExecute(List<Dipartimento> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		ArrayList<String> listStringDepartments = new ArrayList<String>();

		for (Dipartimento d : result) {
			listStringDepartments.add(d.getNome());
		}

		// setto i dipartimenti nello spinner
		ArrayAdapter adapter = new ArrayAdapter(
				context,
				smartcampus.android.template.standalone.R.layout.list_studymate_row_list_simple,
				listStringDepartments);
		spinnerDepartments.setAdapter(adapter);

		// FindHomeActivity.pd.dismiss();
	}

}
