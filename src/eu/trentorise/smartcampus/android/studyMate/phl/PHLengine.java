package eu.trentorise.smartcampus.android.studyMate.phl;

import java.util.List;

import smartcampus.android.template.standalone.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.Corso;
import eu.trentorise.smartcampus.android.studyMate.utilities.MaterialAdapter;
import eu.trentorise.smartcampus.android.studyMate.utilities.MaterialItem;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class PHLengine extends AsyncTask<Bundle, Void, List<Corso>> {

	private Context context;
	private Activity currentActivity;
	private SherlockFragmentActivity currentSherlock;
	private ListView listViewCorsiPersonali;

	public Bundle bundleParam;
	public static Corso corsoSelezionato;
	public static ProgressDialog pd;

	public PHLengine(Context applicationContext, Activity currentActivity,
			ListView listViewCorsi, SherlockFragmentActivity currentSherlock) {
		this.context = applicationContext;
		this.currentActivity = currentActivity;
		this.listViewCorsiPersonali = listViewCorsi;
		this.currentSherlock = currentSherlock;
	}

	public List<Corso> getFrequentedCourses() {

		Context context = null;
		ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_FREQUENTEDCOURSES);
		request.setMethod(Method.GET);

		MessageResponse response;
		String body = null;
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
		new ProgressDialog(currentActivity);
		pd = ProgressDialog.show(currentActivity,
				"Lista dei corsi da libretto", "Caricamento...");
	}

	@Override
	protected void onPostExecute(final List<Corso> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result == null) {

			Toast.makeText(context, "Ops! C'è stato un errore...",
					Toast.LENGTH_SHORT).show();
			currentActivity.finish();
		} else {
			// TitledItem[] items = new TitledItem[result.size()];
			MaterialItem[] items = new MaterialItem[result.size()];
			int i = 0;
			for (Corso s : result) {

				items[i++] = new MaterialItem("Corsi che seguo:", s.getNome(),
						R.drawable.ic_folder, "");
				// items[i++] = new TitledItem("Corsi da libretto",
				// s.getNome());
			}

			// TitledAdapter adapter = new TitledAdapter(currentSherlock,
			// items);
			MaterialAdapter adapter = new MaterialAdapter(currentSherlock,
					items);
			listViewCorsiPersonali.setAdapter(adapter);

			listViewCorsiPersonali
					.setOnItemClickListener(new ListView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {

							// Pass Data to
							corsoSelezionato = new Corso();
							corsoSelezionato = result.get(arg2);
							Intent intent = new Intent();
							intent.setClass(currentActivity, PHL4Courses.class);
							intent.putExtra("NomeCorso",
									corsoSelezionato.getNome());
							intent.putExtra("IdCorso", corsoSelezionato.getId());
							currentActivity.startActivity(intent);

						}
					});

			pd.dismiss();

		}
	}

	@Override
	protected List<Corso> doInBackground(Bundle... params) {
		// TODO Auto-generated method stub
		bundleParam = params[0];

		return getFrequentedCourses();
	}
}
