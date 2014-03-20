package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.Comparator;
import java.util.List;

import eu.trentorise.smartcampus.studymate.R;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.DettailOfEventFragment;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.OverviewFragment;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

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

	// private List<Evento> getAllEventsOfCourse(String id_course) {
	//
	// mProtocolCarrier = new ProtocolCarrier(context,
	// SmartUniDataWS.TOKEN_NAME);
	//
	// MessageRequest request = new MessageRequest(
	// SmartUniDataWS.URL_WS_SMARTUNI,
	// SmartUniDataWS.GET_WS_EVENTS_OF_COURSE(id_course));
	// request.setMethod(Method.GET);
	//
	// MessageResponse response;
	// try {
	// response = mProtocolCarrier.invokeSync(request,
	// SmartUniDataWS.TOKEN_NAME, MyUniActivity.getAuthToken());
	//
	// if (response.getHttpStatus() == 200) {
	//
	// body = response.getBody();
	//
	// } else {
	// return null;
	// }
	// } catch (ConnectionException e) {
	// e.printStackTrace();
	// } catch (ProtocolException e) {
	// e.printStackTrace();
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (AACException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return Utils.convertJSONToObjects(body, Evento.class);
	// }

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

			Toast.makeText(context, "Ops! C'è stato un errore...",
					Toast.LENGTH_SHORT).show();
			fragment.finish();
		} else {
			EventItem[] listEvItem = new EventItem[result.size()];

			int i = 0;

			for (Evento ev : result) {
				AdptDetailedEvent e = new AdptDetailedEvent(ev.getEventoId()
						.getDate(), ev.getTitle(), ev.getType(), ev
						.getEventoId().getStart().toString(), ev.getRoom());
				listEvItem[i++] = new EventItem(e);

			}

			EventAdapter adapter = new EventAdapter(fragment, listEvItem);
			ListView listView = (ListView) fragment
					.findViewById(R.id.listViewEventi);
			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new ListView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					fragment.supportInvalidateOptionsMenu();

					Evento evento = result.get(arg2);

					// Pass Data to other Fragment
					Bundle arguments = new Bundle();
					arguments.putSerializable("eventSelected", evento);
					FragmentTransaction ft = fragment
							.getSupportFragmentManager().beginTransaction();
					Fragment fragment = new DettailOfEventFragment();
					fragment.setArguments(arguments);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
					ft.replace(R.id.tabOverview, fragment);
					ft.addToBackStack(null);
					ft.commit();
				}
			});

			OverviewFragment.pd.dismiss();
		}
	}

	public class CustomComparator implements Comparator<Evento> {
		public int compare(Evento object1, Evento object2) {
			return object1.getEventoId().getStart()
					.compareTo(object2.getEventoId().getStart());
		}
	}

}
