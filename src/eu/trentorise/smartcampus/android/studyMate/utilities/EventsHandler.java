package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.OverviewFragment;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

public class EventsHandler extends AsyncTask<Void, Void, List<Evento>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	String id_course = null;
	FragmentActivity fragment;
	public static List<Evento> listaEventi;

	public EventsHandler(Context applicationContext, FragmentActivity fragment) {
		this.context = applicationContext;
		this.fragment = fragment;
	}

	public EventsHandler(Context applicationContext) {
		this.context = applicationContext;
	}

	private List<Evento> getAllPersonalEvents() {

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI, SmartUniDataWS.GET_WS_MYEVENTS);
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

		return Utils.convertJSONToObjects(body, Evento.class);
	}

	@Override
	protected List<Evento> doInBackground(Void... params) {
		return getAllPersonalEvents();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected void onPostExecute(final List<Evento> result) {
		super.onPostExecute(result);

		listaEventi = result;
		if (result == null) {

			Toast.makeText(context, context.getResources().getString(R.string.dialog_error),
					Toast.LENGTH_SHORT).show();
			fragment.finish();
		} 
	}

	public class CustomComparator implements Comparator<Evento> {
		public int compare(Evento object1, Evento object2) {
			return object1.getEventoId().getStart()
					.compareTo(object2.getEventoId().getStart());
		}
	}

}
