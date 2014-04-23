package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.view.LayoutInflater;
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
import eu.trentorise.smartcampus.android.studyMate.utilities.CoursesHandler.ItemMenuCourseListener;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

public class AsyncSetSharedDip extends AsyncTask<Void, Void, Boolean> {

	private Context context;
	private ProtocolCarrier mProtocolCarrier;
	public List<Dipartimento> listDipartimenti;
	public static ProgressDialog pd;
	private SherlockFragmentActivity act;
	Spinner spinnerDepartments;
	Spinner spinnerDegree;
	public String departSelectedName = null;
	public String courseSelected = null;
	Dipartimento departSelected = null;
	CorsoLaurea corsoLaureaSelected;
	private AsyncSetSharedCds asyncGetCds;
	int i;

	public AsyncSetSharedDip(Context c, Spinner spDep, Spinner spDeg,
			SherlockFragmentActivity act) {
		this.context = c;
		this.act = act;
		this.spinnerDegree = spDeg;
		this.spinnerDepartments = spDep;
		i = 0;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		new ProgressDialog(act);
		pd = ProgressDialog.show(
				act,
				context.getResources().getString(
						R.string.dialog_searching_courses), context
						.getResources().getString(R.string.dialog_loading));

	}

	@Override
	protected Boolean doInBackground(Void... params) {

		String body = null;

		mProtocolCarrier = new ProtocolCarrier(act, SmartUniDataWS.TOKEN_NAME);

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

		listDipartimenti = Utils.convertJSONToObjects(body, Dipartimento.class);

		if (listDipartimenti != null) {
			return true;
		} else {
			return false;
		}
		// TODO Auto-generated method stub

	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		if (result == false) {

			Toast.makeText(act,
					act.getResources().getString(R.string.dialog_error),
					Toast.LENGTH_SHORT).show();
			act.finish();
		} else {

			ArrayList<String> listStringDepartments = new ArrayList<String>();

			for (Dipartimento d : listDipartimenti) {
				listStringDepartments.add(d.getDescription());
			}

			// setto i dipartimenti nello spinner
			ArrayAdapter<String> adapterDep = new ArrayAdapter<String>(
					act,
					eu.trentorise.smartcampus.studymate.R.layout.list_studymate_row_list_simple,
					listStringDepartments);
			spinnerDepartments.setAdapter(adapterDep);

			pd.dismiss();

			spinnerDepartments
					.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?> parent,
								View view, int pos, long id) {

							// chiamo l'handler per il caricamento dei corsi di
							// laurea
							departSelected = listDipartimenti.get(pos);

							spinnerDegree.setClickable(true);

							asyncGetCds = (AsyncSetSharedCds) new AsyncSetSharedCds(
									context, spinnerDegree, departSelected, act)
									.execute();

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});

		}

	}

}