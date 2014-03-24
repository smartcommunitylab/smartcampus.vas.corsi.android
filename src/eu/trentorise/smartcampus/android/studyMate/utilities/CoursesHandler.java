package eu.trentorise.smartcampus.android.studyMate.utilities;

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

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.OverviewFilterFragment;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

public class CoursesHandler extends
		AsyncTask<Bundle, Void, List<CorsoCarriera>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	public List<AttivitaDidattica> coursesFiltered;
	ListView listViewCorsiPersonali;
	public String body;
	public static ProgressDialog pd;
	public Activity currentActivity;
	public SherlockFragmentActivity currentSherlock;
	public Bundle bundleParam;

	public CoursesHandler(Context applicationContext, ListView listViewCorsi,
			Activity currentActivity, SherlockFragmentActivity currentSherlock) {
		this.context = applicationContext;
		this.listViewCorsiPersonali = listViewCorsi;
		this.currentActivity = currentActivity;
		this.currentSherlock = currentSherlock;
	}

	// return list of all courses of all departments
	private List<CorsoCarriera> getAllPersonalCourses() {
		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_MY_COURSES_NOT_PASSED);
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

		return Utils.convertJSONToObjects(body, CorsoCarriera.class);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		new ProgressDialog(currentActivity);
		pd = ProgressDialog.show(currentActivity,
				"Lista dei corsi da libretto", "Caricamento...");
	}

	@Override
	protected void onPostExecute(final List<CorsoCarriera> result) {
		super.onPostExecute(result);
		if (result == null) {

			Toast.makeText(context, "Ops! C'è stato un errore...",
					Toast.LENGTH_SHORT).show();
			currentActivity.finish();
		} else {
			TitledItem[] items = new TitledItem[result.size()];

			int i = 0;
			for (CorsoCarriera s : result) {
				items[i++] = new TitledItem("Corsi da libretto", s.getName());
			}

			TitledAdapter adapter = new TitledAdapter(context, items);

			listViewCorsiPersonali.setAdapter(adapter);

			listViewCorsiPersonali
					.setOnItemClickListener(new ListView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {

							currentSherlock.supportInvalidateOptionsMenu();

							// Pass Data to other Fragment
							CorsoCarriera corsoSelezionato = new CorsoCarriera();
							corsoSelezionato = result.get(arg2);
							Bundle data = new Bundle();
							data.putSerializable(Constants.CORSO_CARRIERA_SELECTED,
									corsoSelezionato);
							data.putString(Constants.COURSE_NAME,
									corsoSelezionato.getName());
							FragmentTransaction ft = currentSherlock
									.getSupportFragmentManager()
									.beginTransaction();
							Fragment fragment = new OverviewFilterFragment();
							fragment.setArguments(data);
							ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
							ft.replace(R.id.tabCorsi, fragment);
							ft.addToBackStack(null);
							ft.commit();
						}
					});

			pd.dismiss();

		}
	}

	@Override
	protected List<CorsoCarriera> doInBackground(Bundle... params) {
		bundleParam = params[0];

		return getAllPersonalCourses();
	}

}
