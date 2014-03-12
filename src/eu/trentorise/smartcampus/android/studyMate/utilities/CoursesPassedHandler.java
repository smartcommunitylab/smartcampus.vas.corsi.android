package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.rate.AddRatingFromCoursesPassed;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;


public class CoursesPassedHandler extends AsyncTask<Void, Void, List<CorsoCarriera>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	private String body;
	private ListView listViewCorsiPassati;
	public Activity currentActivity;
	public static ProgressDialog pd;


	public CoursesPassedHandler(Context applicationContext,
			ListView listViewCorsi, Activity currentActivity) {
		this.context = applicationContext;
		this.listViewCorsiPassati = listViewCorsi;
		this.currentActivity = currentActivity;
	}

	// return list of all courses of all departments
	private List<CorsoCarriera> getAllCoursesPassed() {
		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_MY_COURSES_PASSED);
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
				items[i++] = new TitledItem("Corsi che posso votare",
						s.getName());
			}

			TitledAdapter adapter = new TitledAdapter(context, items);

			listViewCorsiPassati.setAdapter(adapter);

			listViewCorsiPassati
					.setOnItemClickListener(new ListView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							
							// Pass Data to other Fragment
							CorsoCarriera corsoSelezionato = new CorsoCarriera();
							corsoSelezionato = result.get(arg2);
							Intent intent = new Intent();
							intent.setClass(currentActivity,
									AddRatingFromCoursesPassed.class);
							intent.putExtra("NomeCorso",
									corsoSelezionato.getName());
							intent.putExtra("IdCorso", corsoSelezionato.getId());
							currentActivity.startActivity(intent);

							// Intent intent = new Intent(context,
							// AddRatingFromCoursesPassed.class);

							// currentActivity.getIntent().putExtra("CoursePassedSelected",
							// corsoSelezionato);

						}
					});

			pd.dismiss();

		}
	}

	@Override
	protected List<CorsoCarriera> doInBackground(Void... params) {

		return getAllCoursesPassed();
	}

}
