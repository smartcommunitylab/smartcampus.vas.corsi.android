package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import java.text.SimpleDateFormat;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

public class DettailOfEventFragment extends SherlockFragment {

	public Evento eventSelected = null;
	public View view = null;
	private ProtocolCarrier mProtocolCarrier;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_detail_of_event, container,
				false);

		eventSelected = (Evento) getArguments().getSerializable(
				Constants.SELECTED_EVENT);

		return view;
	}

	@Override
	public void onStart() {
		setHasOptionsMenu(true);
		TextView tvTitleEvent = (TextView) view
				.findViewById(R.id.textTitleEvent);
		tvTitleEvent.setText(eventSelected.getTitle());

		TextView tvDateEvent = (TextView) view.findViewById(R.id.textDataEvent);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		tvDateEvent.setText(dateFormat.format(eventSelected.getEventoId()
				.getDate()));

		TextView tvOraEvent = (TextView) view.findViewById(R.id.textOraEvent);

		// se è un evento personale non mostro la data di fine
		if (eventSelected.getEventoId().getIdStudente() == -1)
			tvOraEvent.setText(eventSelected
					.getEventoId()
					.getStart()
					.toString()
					.subSequence(
							0,
							eventSelected.getEventoId().getStart().toString()
									.length() - 3)
					+ " - "
					+ eventSelected
							.getEventoId()
							.getStop()
							.toString()
							.subSequence(
									0,
									eventSelected.getEventoId().getStart()
											.toString().length() - 3));
		else
			tvOraEvent.setText(eventSelected
					.getEventoId()
					.getStart()
					.toString()
					.subSequence(
							0,
							eventSelected.getEventoId().getStart().toString()
									.length() - 3));

		TextView tvTypeEvent = (TextView) view.findViewById(R.id.textTypeEvent);
		tvTypeEvent.setText(eventSelected.getType());

		TextView tvAulaEvent = (TextView) view
				.findViewById(R.id.textTeacherEvent);
		tvAulaEvent.setText(eventSelected.getTeacher());

		TextView tvLocationEvent = (TextView) view
				.findViewById(R.id.textLocationEvent);
		tvLocationEvent.setText(eventSelected.getRoom());
		TextView tvDescEvent = (TextView) view.findViewById(R.id.textDescEvent);
		tvDescEvent.setText(eventSelected.getPersonalDescription());
		super.onStart();

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		if (eventSelected.getEventoId().getIdStudente() == -1) {
			menu.clear();
		} else {
			inflater.inflate(R.menu.det_event, menu);
		}
		super.onCreateOptionsMenu(menu, inflater);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case R.id.menu_change_event:
			Bundle data = new Bundle();
			data.putSerializable(Constants.EDIT_EVENT, eventSelected);
			FragmentTransaction ft = getSherlockActivity()
					.getSupportFragmentManager().beginTransaction();
			Fragment fragment = new EditEventFragment();
			fragment.setArguments(data);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			ft.replace(getId(), fragment, getTag());
			ft.addToBackStack(getTag());
			ft.commit();
			return true;
		case R.id.menu_delete_event:
			new DeleteEvent(eventSelected).execute();
			return true;
		default:
			break;
		}
		return false;
	}

	private class DeleteEvent extends AsyncTask<Evento, Void, Void> {

		public ProgressDialog pd;
		Evento ev;

		public DeleteEvent(Evento ev) {

			this.ev = ev;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(getSherlockActivity());
			pd = ProgressDialog.show(getSherlockActivity(), "Sto eliminando..",
					"");
		}

		private boolean deleteEv(Evento ev) {
			mProtocolCarrier = new ProtocolCarrier(getSherlockActivity(),
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.DELETE_EVENTO);
			request.setMethod(Method.POST);

			MessageResponse response;
			try {
				String evJSON = Utils.convertToJSON(ev);
				request.setBody(evJSON);
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {
					return true;

				} else {
					return false;
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

			return true;
		}

		@Override
		protected Void doInBackground(Evento... params) {
			deleteEv(ev);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			pd.dismiss();
			getSherlockActivity().onBackPressed();
		}

	}

}
