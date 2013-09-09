package smartcampus.android.studyMate.phl;

import java.util.List;

import smartcampus.android.template.standalone.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studyMate.models.Corso;
import eu.trentorise.smartcampus.studyMate.models.CwdPHL;
import eu.trentorise.smartcampus.studyMate.models.RisorsaPhl;
import eu.trentorise.smartcampus.studyMate.utilities.MaterialAdapter;
import eu.trentorise.smartcampus.studyMate.utilities.MaterialItem;
import eu.trentorise.smartcampus.studyMate.utilities.SmartUniDataWS;

public class PHLengine4Material extends AsyncTask<Bundle, Void, RisorsaPhl> {

	private Context context;
	private Activity currentActivity;
	private SherlockFragmentActivity currentSherlock;
	private ListView listViewCorsiPersonali;
	public Bundle bundleParam;
	public static Corso corsoSelezionato;
	public static ProgressDialog pd;
	public static RisorsaPhl risorsa;
	private String idPHL;

	public PHLengine4Material(Context applicationContext,
			Activity currentActivity, ListView listViewCorsi,
			SherlockFragmentActivity currentSherlock, String pHLid) {
		this.context = applicationContext;
		this.currentActivity = currentActivity;
		this.listViewCorsiPersonali = listViewCorsi;
		this.currentSherlock = currentSherlock;
		this.idPHL = pHLid;
	}

	public RisorsaPhl getMaterial4Dir() {

		Context context = null;
		ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);
		final long idCorso = currentActivity.getIntent().getLongExtra(
				"IdCorso", 0);
		MessageRequest request = new MessageRequest(
				"http://api.povoshardlife.eu",
				// "http://api.povoshardlife.eu/api/documenti/getDirByIDSC/"
				"/api/documenti/getDirByIDPHL/" + idPHL);
		// SmartUniDataWS.URL_WS_SMARTUNI,
		// SmartUniDataWS.GET_MATERIAL_FOR_COURSE(idCorso));

		request.setMethod(Method.GET);

		MessageResponse response;
		String body = null;
		try {
			response = mProtocolCarrier.invokeSync(request, "token",
					"2d2abbe190e0d7ad0ae71425059f00cc");// SmartUniDataWS.TOKEN);

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

		System.out.println(body);

		return Utils.convertJSONToObject(body, RisorsaPhl.class);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		new ProgressDialog(currentActivity);
		pd = ProgressDialog.show(currentActivity, "Carico i materiali",
				"Caricamento...");
	}

	@Override
	protected void onPostExecute(final RisorsaPhl result) {
		super.onPostExecute(result);
		if (result == null) {
			Toast.makeText(context, "Ops! C'è stato un errore...",
					Toast.LENGTH_SHORT).show();
			currentActivity.finish();
		} else {
			System.out.println("FUNZIONA????");
			int i = 0;
//			MaterialItem[] items = new MaterialItem[result.getCdc().size()];
//			for (CwdPHL c : result.getCdc()) {
//				if (c.getMime() == "directory") {
//					items[i++] = new MaterialItem(result.getCwd().getName(),
//							c.getName(), R.drawable.cartella, c.getHash());
//				}
//				// if(n.getCwd().getMime() == "application/pdf"){
//				else {
//					items[i++] = new MaterialItem(result.getCwd().getName(),
//							c.getName(), R.drawable.pdf, c.getHash());
//				}
//
//				MaterialAdapter adapter = new MaterialAdapter(currentSherlock,
//						items);
//				listViewCorsiPersonali.setAdapter(adapter);
//			}

			
		}

		listViewCorsiPersonali
				.setOnItemClickListener(new ListView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						new PHLengine4Material(context, currentActivity, listViewCorsiPersonali, currentSherlock, result.getCwd().getHash());
					}
				});

		pd.dismiss();
	}

	@Override
	protected RisorsaPhl doInBackground(Bundle... params) {
		// TODO Auto-generated method stub
		//bundleParam = params[0];

		return getMaterial4Dir();
	}

}
