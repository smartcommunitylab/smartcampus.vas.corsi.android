package eu.trentorise.smartcampus.android.studyMate.phl;

import java.util.Collections;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.Corso;
import eu.trentorise.smartcampus.android.studyMate.models.CwdPHL;
import eu.trentorise.smartcampus.android.studyMate.models.RisorsaPhl;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.DownloadTask;
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
	private RisorsaPhl r;

	public PHLengine4Material(Context applicationContext,
			Activity currentActivity, ListView listViewCorsi,
			SherlockFragmentActivity currentSherlock, String pHLid) {
		this.context = applicationContext;
		this.currentActivity = currentActivity;
		this.listViewCorsiPersonali = listViewCorsi;
		this.currentSherlock = currentSherlock;
		this.idPHL = pHLid;
	}

	public RisorsaPhl getMaterial4Dir() throws AACException {

		Context context = null;
		ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				"http://api.povoshardlife.eu", "/api/documenti/getDirByIDPHL/"
						+ idPHL + "?sc_token=" + MyUniActivity.getAuthToken());
		request.setCustomHeaders(Collections.singletonMap("Authorization",
				"Token token=2d2abbe190e0d7ad0ae71425059f00cc"));
		request.setMethod(Method.GET);

		MessageResponse response;
		String body = null;
		try {
			response = mProtocolCarrier.invokeSync(request, "token", null);

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
		}

		return Utils.convertJSONToObject(body, RisorsaPhl.class);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		new ProgressDialog(currentActivity);
		pd = ProgressDialog.show(currentActivity, "Carico i materiali",
				"Caricamento...");
	}

	@Override
	protected void onPostExecute(final RisorsaPhl result) {
		super.onPostExecute(result);

		r = result;
		int i = 0;
		if (result == null) {
			Toast.makeText(context, "Ops! C'è stato un errore...",
					Toast.LENGTH_SHORT).show();
			currentActivity.finish();
		} else {
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
		if (i == 0) {
			Toast.makeText(context, "La cartella è vuota...",
					Toast.LENGTH_SHORT).show();
		}

		listViewCorsiPersonali
				.setOnItemClickListener(new ListView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (r.getCdc().get(arg2).getMime().equals("directory")) {

							currentSherlock.getSupportFragmentManager()
									.popBackStack();
							FragmentTransaction ft = currentSherlock
									.getSupportFragmentManager()
									.beginTransaction();
							Fragment fragment = new Materiali4LevelPhlFragment();
							Bundle b = new Bundle();
							b.putString("res", result.getCdc().get(arg2)
									.getHash());
							fragment.setArguments(b);
							ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
							ft.replace(R.id.tabMateriali, fragment);
							ft.addToBackStack(null);
							ft.commit();

						} else {
							DownDialog(r.getCdc().get(arg2));

						}
					}
				});
		pd.dismiss();
	}

	@Override
	protected RisorsaPhl doInBackground(Bundle... params) {
		// bundleParam = params[0];
		try {
			return getMaterial4Dir();
		} catch (AACException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void DownDialog(CwdPHL r) {

		// instantiate it within the onCreate method
		ProgressDialog mProgressDialog;
		mProgressDialog = new ProgressDialog(currentActivity);
		mProgressDialog.setMessage("Download...");
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(true);

		// execute this when the downloader must be fired
		final DownloadTask downloadTask = new DownloadTask(currentActivity,
				mProgressDialog, r);
		downloadTask.execute("http://api.povoshardlife.eu/" + r.getURL());// +
																			// "?sc_token="
																			// +
																			// MyUniActivity.userAuthToken);

		mProgressDialog
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						downloadTask.cancel(true);
					}
				});

	}

}