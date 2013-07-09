package smartcampus.android.template.standalone;

import java.util.List;

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
import eu.trentorise.smartcampus.smartuni.models.Corso;
import eu.trentorise.smartcampus.smartuni.models.RisorsaPhl;
import eu.trentorise.smartcampus.smartuni.utilities.SmartUniDataWS;

public class PHLengine4Course extends AsyncTask<Bundle, Void, List<RisorsaPhl>> {

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

	public List<RisorsaPhl> getMaterial4Course() {

		Context context = null;
		ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);
		final long idCorso = currentActivity.getIntent().getLongExtra(
				"IdCorso", 0);
		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_MATERIAL_FOR_COURSE(idCorso));

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

		return Utils.convertJSONToObjects(body, RisorsaPhl.class);
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
	protected void onPostExecute(final List<RisorsaPhl> result) {
		super.onPostExecute(result);
		final int lv = 1;
		if (result == null) {

			Toast.makeText(context, "Ops! C'è stato un errore...",
					Toast.LENGTH_SHORT).show();
			currentActivity.finish();
		} else {
			int size = 0;
			// MaterialItem[] items = new MaterialItem[result.size()];
			int i = 0;
			for (RisorsaPhl n : result) {
				if (n.getLevel() == lv) {
					size++;
				}
			}
			final MaterialItem[] items = new MaterialItem[size];
			for (RisorsaPhl s : result) {
				if (s.getLevel() == lv) {
					if (s.getMime() == null) {
						items[i++] = new MaterialItem(s.getModified(),
								s.getName(), R.drawable.cartella, s.getLevel());

					} else {

						items[i++] = new MaterialItem(s.getModified(),
								s.getName(), R.drawable.pdf, s.getLevel());

					}
				}
				// else{
				// items[i++] = new MaterialItem(s.getModified(), s.getName(),
				// R.drawable.smartuni_logo);
				//
				// }
			}

			MaterialAdapter adapter = new MaterialAdapter(currentSherlock,
					items);
			listViewCorsiPersonali.setAdapter(adapter);

			listViewCorsiPersonali
					.setOnItemClickListener(new ListView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,								
								int arg2, long arg3) {
							Bundle b = new Bundle();
							//b.putSerializable("Materiale", risorsa);
							b.putSerializable("cartella", items[arg2]);
							FragmentTransaction ft = currentSherlock
									.getSupportFragmentManager()
									.beginTransaction();
							Fragment fragment = new MaterialiPhlFragment();
							ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
							ft.replace(R.id.tabMateriali, fragment);
							ft.addToBackStack(null);
							ft.commit();

						}
					});

			pd.dismiss();
		}
	}

	@Override
	protected List<RisorsaPhl> doInBackground(Bundle... params) {
		// TODO Auto-generated method stub
		bundleParam = params[0];

		return getMaterial4Course();
	}

}
