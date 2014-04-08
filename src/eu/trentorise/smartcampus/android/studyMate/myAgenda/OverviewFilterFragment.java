package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.AdptDetailedEvent;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventAdapter;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventItem;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventsHandler;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.android.studyMate.utilities.CoursesHandler.AsyncDeleteCourseInterest;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

public class OverviewFilterFragment extends SherlockFragment {

	public static ProgressDialog pd;
	public List<Evento> listaEventiFiltrati = null;
	public static String nomeCorsoOW;
	private CorsoCarriera cc;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_overview,
				container, false);
		Bundle b = getArguments();
		nomeCorsoOW = b.getString(Constants.COURSE_NAME);
		cc = (CorsoCarriera) b
				.getSerializable(Constants.CORSO_CARRIERA_SELECTED);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		setHasOptionsMenu(true);

		listaEventiFiltrati = new ArrayList<Evento>();

		getActivity().setTitle(nomeCorsoOW);

		OverviewFilterFragment.pd = ProgressDialog.show(
				getActivity(),
				getActivity().getResources().getString(
						R.string.dialog_list_events), getActivity()
						.getResources().getString(R.string.dialog_loading));

		AsyncTask<Void, Void, Void> taskCourseEvents = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				listaEventiFiltrati = filterEventsbyCourse();

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);

				OverviewFilterFragment.pd.dismiss();

				EventItem[] listEvItem = new EventItem[listaEventiFiltrati
						.size()];
				if (listaEventiFiltrati.size() == 0) {
					Toast.makeText(
							getSherlockActivity(),
							"Non sono disponibli eventi a breve per questo corso",
							Toast.LENGTH_SHORT).show();
				} else {
					int i = 0;
					for (Evento ev : listaEventiFiltrati) {
						AdptDetailedEvent e = new AdptDetailedEvent(ev
								.getEventoId().getDate(), ev.getTitle(),
								ev.getType(), ev.getEventoId().getStart()
										.toString(), ev.getRoom());
						listEvItem[i++] = new EventItem(e, getActivity()
								.getResources());
					}

					EventAdapter adapter = new EventAdapter(
							getSherlockActivity(), listEvItem);
					ListView listView = (ListView) getSherlockActivity()
							.findViewById(R.id.listViewEventi);
					listView.setAdapter(adapter);
					listView.setOnItemClickListener(new ListView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							getSherlockActivity()
									.supportInvalidateOptionsMenu();
							// Pass Data to other Fragment
							Evento evento = listaEventiFiltrati.get(arg2);
							Bundle arguments = new Bundle();
							arguments.putSerializable(Constants.SELECTED_EVENT,
									evento);
							FragmentTransaction ft = getSherlockActivity()
									.getSupportFragmentManager()
									.beginTransaction();
							Fragment fragment = new DettailOfEventFragment();
							fragment.setArguments(arguments);
							ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
							ft.replace(getId(), fragment, getTag());
							ft.addToBackStack(getTag());
							ft.commit();
						}

					});
				}
			}
		};

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			taskCourseEvents.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					(Void[]) null);
		else
			taskCourseEvents.execute((Void[]) null);

	}

	// filtro gli eventi in base al corso che ho selezionato
	private List<Evento> filterEventsbyCourse() {
		;
		List<Evento> eventiFiltrati = new ArrayList<Evento>();

		try {
			for (Evento evento : new EventsHandler(getSherlockActivity()
					.getApplicationContext(), getActivity()).execute().get()) {
				if (String.valueOf(evento.getTitle()).compareTo(nomeCorsoOW) == 0) {
					eventiFiltrati.add(evento);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return eventiFiltrati;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.add_event, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_add_event_4_course:
			Bundle data = new Bundle();
			data.putSerializable(Constants.CC_SELECTED, cc);
			FragmentTransaction ft = getSherlockActivity()
					.getSupportFragmentManager().beginTransaction();
			Fragment fragment = new AddEvent4coursesFragment();
			fragment.setArguments(data);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			ft.replace(getId(), fragment, getTag());
			ft.addToBackStack(getTag());
			ft.commit();
			return true;
		case R.id.menu_unfollow:
			if (cc.getResult().compareTo("-1") == 0) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					new AsyncDeleteCourseInterest().executeOnExecutor(
							AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
					getActivity().onBackPressed();
				} else {
					new AsyncDeleteCourseInterest().execute((Void[]) null);
					getActivity().onBackPressed();
				}
			} else {
				Toast.makeText(getActivity(),
						R.string.feedback_course_is_career, Toast.LENGTH_SHORT)
						.show();
			}
		default:
			break;
		}
		return false;
	}

	public class AsyncDeleteCourseInterest extends
			AsyncTask<Void, Void, Boolean> {

		private ProtocolCarrier mProtocolCarrier;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(getActivity());
			pd = ProgressDialog.show(getActivity(),
					getActivity().getApplicationContext().getResources()
							.getString(R.string.dialog_waiting_goto_home),
					getActivity().getApplicationContext().getResources()
							.getString(R.string.dialog_loading));

		}

		@Override
		protected Boolean doInBackground(Void... params) {
			mProtocolCarrier = new ProtocolCarrier(getActivity()
					.getApplicationContext(), SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.POST_WS_COURSE_UNFOLLOW(cc.getCod()));
			request.setMethod(Method.POST);

			MessageResponse response;
			String body = null;

			try {
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

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
				e.printStackTrace();
			}

			return Utils.convertJSONToObject(body, Boolean.class);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result == null) {

				pd.dismiss();

				Toast.makeText(
						getActivity(),
						getActivity().getApplicationContext().getResources()
								.getString(R.string.dialog_error_delete),
						Toast.LENGTH_SHORT).show();
			} else if (result) {

				pd.dismiss();
				Toast.makeText(
						getActivity(),
						getActivity().getApplicationContext().getResources()
								.getString(R.string.dialog_success_delete),
						Toast.LENGTH_SHORT).show();

			}

		}
	}

}
