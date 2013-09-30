package eu.trentorise.smartcampus.android.studyMate.phl;

import java.util.Collections;

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
import eu.trentorise.smartcampus.android.studyMate.models.Corso;
import eu.trentorise.smartcampus.android.studyMate.models.CwdPHL;
import eu.trentorise.smartcampus.android.studyMate.models.RisorsaPhl;
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

public class PHLengine4Course extends AsyncTask<Bundle, Void, RisorsaPhl> {

	private Context context;
	private Activity currentActivity;
	private SherlockFragmentActivity currentSherlock;
	private ListView listViewCorsiPersonali;
	public Bundle bundleParam;
	public static Corso corsoSelezionato;
	public static ProgressDialog pd;
	public static RisorsaPhl risorsa;

	public PHLengine4Course(Context applicationContext,
			Activity currentActivity, ListView listViewCorsi,
			SherlockFragmentActivity currentSherlock) {
		this.context = applicationContext;
		this.currentActivity = currentActivity;
		this.listViewCorsiPersonali = listViewCorsi;
		this.currentSherlock = currentSherlock;
	}

	public RisorsaPhl getMaterial4Course() {

		Context context = null;
		ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);
		final long idCorso = currentActivity.getIntent().getLongExtra(
				"IdCorso", 0);
		MessageRequest request = new MessageRequest(
				"http://api.povoshardlife.eu",
				// "http://api.povoshardlife.eu/api/documenti/getDirByIDSC/"
				"/api/documenti/getDirByIDSC/" + idCorso);
		request.setCustomHeaders(Collections.singletonMap("Authorization",
				"Token token=2d2abbe190e0d7ad0ae71425059f00cc"));
		// SmartUniDataWS.URL_WS_SMARTUNI,
		// SmartUniDataWS.GET_MATERIAL_FOR_COURSE(idCorso));

		request.setMethod(Method.GET);

		MessageResponse response;
		String body = null;
		try {
			response = mProtocolCarrier.invokeSync(request, "token", null);// SmartUniDataWS.TOKEN);

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
		}

		else {
			if (result.getError()== null){
			int i = 0;
			MaterialItem[] items = new MaterialItem[result.getCdc().size()];
			for (CwdPHL c : result.getCdc()) {
				if (c.getMime().equals("directory")) {
					items[i++] = new MaterialItem(result.getCwd().getName(),
							c.getName(), R.drawable.ic_folder, c.getHash());
				} else if (c.getMime().equals("application/pdf")) {
					items[i++] = new MaterialItem(result.getCwd().getName(),
							c.getName(), R.drawable.ic_pdffile, c.getHash());
				} else if (c.getMime().equals("image/jpeg")) {
					items[i++] = new MaterialItem(result.getCwd().getName(),
							c.getName(), R.drawable.ic_imgfile, c.getHash());
				} else if ((c.getMime().equals("application/x-tar"))
						|| (c.getMime().equals("application/x-rar-compressed"))
						|| (c.getMime().equals("application/zip"))) {
					items[i++] = new MaterialItem(result.getCwd().getName(),
							c.getName(), R.drawable.ic_archivefile, c.getHash());
				} else if ((c.getMime().equals("application/msword"))
						|| (c.getMime()
								.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
						|| (c.getMime().equals("text/plain"))) {
					items[i++] = new MaterialItem(result.getCwd().getName(),
							c.getName(), R.drawable.ic_textfile, c.getHash());
				} else {
					items[i++] = new MaterialItem(result.getCwd().getName(),
							c.getName(), R.drawable.ic_genericfile, c.getHash());

				}

				MaterialAdapter adapter = new MaterialAdapter(currentSherlock,
						items);
				listViewCorsiPersonali.setAdapter(adapter);
			}
			}
			else{
				Toast.makeText(context, "La directory è vuota",
						Toast.LENGTH_SHORT).show();
				currentActivity.finish();
			}
		}

		listViewCorsiPersonali
				.setOnItemClickListener(new ListView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (result.getCdc().get(arg2).getMime()
								.equals("directory")) {
							FragmentTransaction ft = currentSherlock
									.getSupportFragmentManager()
									.beginTransaction();
							Fragment fragment = new Materiali4LevelPhlFragment();
							ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
							Bundle b = new Bundle();
							b.putString("res", result.getCdc().get(arg2)
									.getHash());
							fragment.setArguments(b);
							// ft.replace(R.id.tabMateriali, fragment);
							ft.add(R.id.tabMateriali, fragment);
							// ft.addToBackStack(null);
							ft.commit();
						}
						// else {
						// Toast.makeText(context, "Coming Soon!",
						// Toast.LENGTH_SHORT).show();
						// }
						// new PHLengine4Material(context, currentActivity,
						// listViewCorsiPersonali, currentSherlock, result
						// .getCdc().get(arg2).getHash())
						// .execute();
					}
				});

		pd.dismiss();
	}

	// }

	@Override
	protected RisorsaPhl doInBackground(Bundle... params) {
		// TODO Auto-generated method stub
		// bundleParam = params[0];

		return getMaterial4Course();
	}

}
