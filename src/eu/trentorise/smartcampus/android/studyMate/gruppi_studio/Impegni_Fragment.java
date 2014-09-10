package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.utilities.AdptDetailedEvent;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventAdapter;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventItem;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class Impegni_Fragment extends SherlockFragment {
	public List<Evento> lista_impegni;
	public List<Evento> contextualListaImpegni = new ArrayList<Evento>();
	GruppoDiStudio gds;
	private ProtocolCarrier mProtocolCarrier;
	public String body;
	private View view;
	private ListView impegni_listview;
	@SuppressWarnings("unused")
	private ProgressDialog pd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.listaimpegni_fragment, container,
				false);
		hiddenKeyboard(view);
		return view;
	}
	private void hiddenKeyboard(View v) {
        InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
	@Override
	public void onStart() {

		// TODO Auto-generated method stub
		impegni_listview = (ListView) view.findViewById(R.id.lista_impegni);
		setHasOptionsMenu(true);
		gds = Overview_GDS.contextualGDS;
		new AsyncTimpegniLoader(getActivity()).execute();
		super.onStart();

	}

	private class AsyncTimpegniLoader extends AsyncTask<Void, Void, Void> {

		public AsyncTimpegniLoader(Context taskcontext) {
		}

		private List<Evento> retrievedImpegni() {
			mProtocolCarrier = new ProtocolCarrier(getActivity(),
					SmartUniDataWS.TOKEN_NAME);
			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_CONTEXTUAL_ATTIVITASTUDIO(gds.getId()));
			request.setMethod(Method.GET);

			MessageResponse response;
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

			return Utils.convertJSONToObjects(body, Evento.class);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(getActivity());
			// ripulisco la lista impegni prima di caricare i nuovi impegni
			// contextualListaImpegni.clear();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// faccio andare il metodo web per recuperare la lista impegni dal
			// web
			contextualListaImpegni = (ArrayList<Evento>) retrievedImpegni();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			lista_impegni = contextualListaImpegni;
			// gestione listaimpegni

			EventItem[] listEvItem = new EventItem[lista_impegni.size()];
			int i = 0;
			for (Evento ev : lista_impegni) {
				AdptDetailedEvent e = new AdptDetailedEvent(ev.getEventoId()
						.getDate(), ev.getTitle(), ev.getType(), ev
						.getEventoId().getStart().toString(), ev.getRoom());
				listEvItem[i++] = new EventItem(e, getActivity());

			}
			EventAdapter adapter = new EventAdapter(getSherlockActivity(),
					listEvItem);
			impegni_listview.setAdapter(adapter);

			impegni_listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					final Evento selected_impegno = lista_impegni.get(position);
					Intent intent = new Intent(getActivity(),
							ShowImpegnoGDS.class);
					intent.putExtra(Constants.CONTEXTUAL_ATT, selected_impegno);
					intent.putExtra(Constants.CONTESTUAL_GDS, gds);
					startActivity(intent);
				}
			});
		}

	}

}
