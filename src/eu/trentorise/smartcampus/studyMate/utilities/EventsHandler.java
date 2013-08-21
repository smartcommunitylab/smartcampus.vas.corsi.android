package eu.trentorise.smartcampus.studyMate.utilities;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import smartcampus.android.studyMate.myAgenda.DettailOfEventFragment;
import smartcampus.android.studyMate.myAgenda.MyAgendaActivity;
import smartcampus.android.studyMate.myAgenda.OverviewFragment;
import smartcampus.android.studyMate.myAgenda.MyAgendaActivity.MenuKind;
import smartcampus.android.template.standalone.R;
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
import eu.trentorise.smartcampus.ac.authenticator.AMSCAccessProvider;
import eu.trentorise.smartcampus.ac.model.UserData;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studyMate.models.Evento;

public class EventsHandler extends AsyncTask<Void, Void, List<Evento>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	String id_course = null;
	private AMSCAccessProvider mAccessProvider;
	FragmentActivity fragment;
	public static List<Evento> listaEventi;

	public EventsHandler(Context applicationContext, FragmentActivity fragment) {
		this.context = applicationContext;
		this.fragment = fragment;
	}

	public EventsHandler(Context applicationContext) {
		this.context = applicationContext;
	}

	@SuppressWarnings("unused")
	private List<Evento> getAllEventsOfCourse(String id_course) {

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_EVENTS_OF_COURSE(id_course));
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

		return Utils.convertJSONToObjects(body, Evento.class);
	}

	private List<Evento> getAllPersonalEvents() {

		mAccessProvider = new AMSCAccessProvider();
		UserData data = mAccessProvider.readUserData(context, null);
		data.getUserId();

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI, SmartUniDataWS.GET_WS_MYEVENTS);
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

		return Utils.convertJSONToObjects(body, Evento.class);
	}

	@Override
	protected List<Evento> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return getAllPersonalEvents();
		// return getAllEventsOfCourse("1");
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

	}

	@Override
	protected void onPostExecute(final List<Evento> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		listaEventi = result;
		if (result == null) {

			Toast.makeText(context, "Ops! C'è stato un errore...",
					Toast.LENGTH_SHORT).show();
			fragment.finish();
		} else {
			// ordino per data
			Collections.sort(result, new CustomComparator());

			EventItem[] listEvItem = new EventItem[result.size()];

			int i = 0;

			for (Evento ev : result) {
				AdptDetailedEvent e = new AdptDetailedEvent(ev.getData(),
						ev.getTitolo(), ev.getDescrizione(), ev.getStart()
								.toString(), ev.getRoom());
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
					MyAgendaActivity parent = (MyAgendaActivity) fragment;
					parent.setAgendaState(MenuKind.DETAIL_OF_EVENT);
					fragment.invalidateOptionsMenu();

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
			return object1.getData().compareTo(object2.getData());
		}
	}
}
