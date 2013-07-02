package eu.trentorise.smartcampus.smartuni.utilities;

import java.util.List;

import smartcampus.android.template.standalone.MyAgendaActivity;
import smartcampus.android.template.standalone.MyAgendaActivity.MenuKind;
import smartcampus.android.template.standalone.OverviewFilterFragment;
import smartcampus.android.template.standalone.R;
import smartcampus.android.template.standalone.TitledAdapter;
import smartcampus.android.template.standalone.TitledItem;
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

public class CoursesHandler extends AsyncTask<Bundle, Void, List<Corso>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	public List<Corso> coursesFiltered;
	ListView listViewCorsiPersonali;
	public String body;
	public static ProgressDialog pd;
	public Activity currentActivity;
	public SherlockFragmentActivity currentSherlock;
	public Bundle bundleParam;
	public static Corso corsoSelezionato;

	public CoursesHandler(Context applicationContext, ListView listViewCorsi,
			Activity currentActivity, SherlockFragmentActivity currentSherlock) {
		// TODO Auto-generated constructor stub
		this.context = applicationContext;
		this.listViewCorsiPersonali = listViewCorsi;
		this.currentActivity = currentActivity;
		this.currentSherlock = currentSherlock;
	}

	// return list of all courses of all departments
	private List<Corso> getAllPersonalCourses() {
		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_FREQUENTEDCOURSES);
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

		TitledItem[] items = new TitledItem[result.size()];

		int i = 0;
		for (Corso s : result) {
			items[i++] = new TitledItem("Corsi da libretto", s.getNome());
		}

		TitledAdapter adapter = new TitledAdapter(currentSherlock, items);

		listViewCorsiPersonali.setAdapter(adapter);

		listViewCorsiPersonali
				.setOnItemClickListener(new ListView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						MyAgendaActivity parent = (MyAgendaActivity) currentActivity;
						parent.setAgendaState(MenuKind.OVERVIEW_FILTERED_BY_COURSE);
						currentActivity.invalidateOptionsMenu();

						// Pass Data to other Fragment
						corsoSelezionato = new Corso();
						corsoSelezionato = result.get(arg2);
						FragmentTransaction ft = currentSherlock
								.getSupportFragmentManager().beginTransaction();
						Fragment fragment = new OverviewFilterFragment();
						ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						ft.replace(R.id.tabCorsi, fragment);
						ft.addToBackStack(null);
						ft.commit();
					}
				});

		pd.dismiss();

	}

	@Override
	protected List<Corso> doInBackground(Bundle... params) {
		// TODO Auto-generated method stub
		bundleParam = params[0];

		return getAllPersonalCourses();
	}

}
