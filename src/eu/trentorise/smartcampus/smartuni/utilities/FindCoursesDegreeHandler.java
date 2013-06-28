package eu.trentorise.smartcampus.smartuni.utilities;

import java.util.ArrayList;
import java.util.List;

import smartcampus.android.template.standalone.FindHomeActivity;
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
import eu.trentorise.smartcampus.smartuni.models.CorsoLaurea;
import eu.trentorise.smartcampus.smartuni.models.Dipartimento;

public class FindCoursesDegreeHandler extends
		AsyncTask<Void, Void, List<CorsoLaurea>>
{

	private ProtocolCarrier	mProtocolCarrier;
	public Context			context;
	String					body				= null;
	Spinner					spinnerCorsiLaurea;
	Dipartimento			departSelected		= null;
	List<CorsoLaurea>		listCourseDegree	= null;
	ProgressDialog			pd;

	public FindCoursesDegreeHandler(Context applicationContext,
			Spinner spinnerCorsiLaurea, Dipartimento departSelected)
	{
		this.context = applicationContext;
		this.spinnerCorsiLaurea = spinnerCorsiLaurea;
		this.departSelected = departSelected;
	}

	@Override
	protected void onPreExecute()
	{
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected List<CorsoLaurea> doInBackground(Void... params)
	{
		// TODO Auto-generated method stub

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS
						.GET_WS_COURSESDEGREE_OF_DEPARTMENT(departSelected
								.getId()));
		request.setMethod(Method.GET);

		MessageResponse response;
		try
		{
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, SmartUniDataWS.TOKEN);

			if (response.getHttpStatus() == 200)
			{

				body = response.getBody();

			}
			else
			{
				return null;
			}
		}
		catch (ConnectionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ProtocolException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
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
	protected void onPostExecute(List<CorsoLaurea> result)
	{
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		ArrayList<String> listStringDegree = new ArrayList<String>();

		for (CorsoLaurea d : result)
		{
			listStringDegree.add(d.getNome());
		}

		// setto i corsi di laurea nello spinner
		ArrayAdapter adapter = new ArrayAdapter(
				context,
				smartcampus.android.template.standalone.R.layout.list_studymate_row_list_simple,
				listStringDegree);
		spinnerCorsiLaurea.setAdapter(adapter);

		FindHomeActivity.pd.dismiss();

	}

}
