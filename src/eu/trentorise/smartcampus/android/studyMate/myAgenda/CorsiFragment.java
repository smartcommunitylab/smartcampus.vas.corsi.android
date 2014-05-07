package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.finder.FindHomeCourseActivity;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.CoursesHandler;
import eu.trentorise.smartcampus.android.studyMate.utilities.FirstPageFragmentListener;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.android.studyMate.utilities.TitledAdapter;
import eu.trentorise.smartcampus.android.studyMate.utilities.TitledItem;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

public class CorsiFragment extends SherlockFragment {
	public static boolean followstate;
	private List<CorsoCarriera> listCorsi;
	public static CorsoCarriera corsoSelezionato;
	static FirstPageFragmentListener secondPageListener;
	
	public CorsiFragment(FirstPageFragmentListener secondPListener) {
		secondPageListener = secondPListener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_corsi,
				container, false);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		setHasOptionsMenu(true);
		ListView listViewCorsi = (ListView) getSherlockActivity().findViewById(
				R.id.listViewCorsi);
		getSherlockActivity().supportInvalidateOptionsMenu();

//		Bundle arguments = new Bundle();
//		CoursesHandler handlerPersonalCourses = new CoursesHandler(
//				getActivity().getApplicationContext(), listViewCorsi, this,
//				getSherlockActivity());
//		handlerPersonalCourses.execute(arguments);
		CorsiFragment.followstate = true;

		listCorsi = MyAgendaActivity.getListCoursesOnAgenda();

		if (listCorsi == null) {

			Toast.makeText(
					getActivity(),
					getActivity().getResources().getString(
							R.string.invalid_career), Toast.LENGTH_SHORT)
					.show();
			getSherlockActivity().finish();
		} else {
			TitledItem[] items = new TitledItem[listCorsi.size()];

			int i = 0;
			for (CorsoCarriera s : listCorsi) {
				if (s.getResult().equals("-1"))
					items[i++] = new TitledItem(getActivity().getResources()
							.getString(R.string.header_course_interest),
							s.getName());
				else
					items[i++] = new TitledItem(getActivity().getResources()
							.getString(R.string.header_course_career),
							s.getName());

			}

			TitledAdapter adapter = new TitledAdapter(getActivity(), items);

			listViewCorsi.setAdapter(adapter);

			listViewCorsi
					.setOnItemClickListener(new ListView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {

							getSherlockActivity()
									.supportInvalidateOptionsMenu();

							corsoSelezionato = listCorsi.get(arg2);
							secondPageListener.onSwitchToNextFragment();
//							Bundle data = new Bundle();
//							data.putSerializable(
//									Constants.CORSO_CARRIERA_SELECTED,
//									corsoSelezionato);
//							data.putString(Constants.COURSE_NAME,
//									corsoSelezionato.getName());
//							FragmentTransaction ft = getSherlockActivity()
//									.getSupportFragmentManager()
//									.beginTransaction();
//							Fragment fragment = new OverviewFilterFragment();
//							fragment.setArguments(data);
//							ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//							ft.replace(CorsiFragment.this.getId(), fragment,
//									CorsiFragment.this.getTag());
//							ft.addToBackStack(CorsiFragment.this.getTag());
//							ft.commit();
						}
					});

			listViewCorsi
					.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {

							AlertDialog.Builder builderSingle = new AlertDialog.Builder(
									getSherlockActivity());
							builderSingle.setTitle(listCorsi.get(arg2)
									.getName());

							corsoSelezionato = listCorsi.get(arg2);

							// ListView mLocationList = (ListView)
							// currentSherlock.findViewById(R.id.lv);

							if (listCorsi.get(arg2).getResult().equals("-1")) {

								final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
										getSherlockActivity(),
										android.R.layout.simple_list_item_1);

								String[] cI = getSherlockActivity()
										.getResources().getStringArray(
												R.array.dialogAgendaInterest);
								adapter.add(cI[0]);
								// adapter.add(cI[1]);//////////////////////////////////////////////////////////////////////PROVVISORIO
								builderSingle.setAdapter(adapter, new ItemMenuCourseListener());
							} else {
								final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
										getSherlockActivity(),
										android.R.layout.simple_list_item_1);
								String[] cL = getSherlockActivity()
										.getResources().getStringArray(
												R.array.dialogAgendaCareer);
								adapter.add(cL[0]);
								builderSingle.setAdapter(adapter,
										new ItemMenuCourseListener());
							}

							builderSingle.show();
							return true;
						}
					});
		}

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.agenda, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.add_event:
			FragmentTransaction ft = getSherlockActivity()
					.getSupportFragmentManager().beginTransaction();
			Fragment fragment = new AddEventFragment();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			ft.replace(getId(), fragment, getTag());
			ft.addToBackStack(getTag());
			ft.commit();
			return true;
		default:
			break;
		}
		return false;
	}

	public class AsyncCourseAd extends AsyncTask<Void, Void, AttivitaDidattica> {

		private ProtocolCarrier mProtocolCarrier;
		private ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(getSherlockActivity());
			pd = ProgressDialog.show(getSherlockActivity(), getActivity().getResources()
					.getString(R.string.dialog_waiting_goto_home), getActivity()
					.getResources().getString(R.string.dialog_loading));

		}

		@Override
		protected AttivitaDidattica doInBackground(Void... params) {
			mProtocolCarrier = new ProtocolCarrier(getActivity(),
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_COURSE_BY_COD(corsoSelezionato
							.getCod()));
			request.setMethod(Method.GET);

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

			return Utils.convertJSONToObject(body, AttivitaDidattica.class);
		}

		@Override
		protected void onPostExecute(AttivitaDidattica result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result == null) {

				pd.dismiss();

				Toast.makeText(
						getSherlockActivity(),
						getSherlockActivity().getResources().getString(
								R.string.dialog_error_redirect),
						Toast.LENGTH_SHORT).show();
			} else {

				Intent i = new Intent(getSherlockActivity(),
						FindHomeCourseActivity.class);

				i.putExtra(Constants.COURSE_NAME, result.getDescription());
				i.putExtra(Constants.COURSE_ID, result.getAdId());
				i.putExtra(Constants.AD_COD, result.getAdCod());

				pd.dismiss();

				getSherlockActivity().startActivity(i);
			}

		}
	}

	public class ItemMenuCourseListener implements OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			if (which == 0) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					new AsyncCourseAd().executeOnExecutor(
							AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
				} else {
					new AsyncCourseAd().execute((Void[]) null);
				}

			} else {
				if (which == 1) {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
						new AsyncDeleteCourseInterest().executeOnExecutor(
								AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
					} else {
						new AsyncDeleteCourseInterest().execute((Void[]) null);
					}
				}
			}

		}

	}

	public class AsyncDeleteCourseInterest extends
			AsyncTask<Void, Void, Boolean> {

		private ProtocolCarrier mProtocolCarrier;
		private ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(getSherlockActivity());
			pd = ProgressDialog.show(getSherlockActivity(), getActivity().getResources()
					.getString(R.string.dialog_waiting_goto_home), getActivity()
					.getResources().getString(R.string.dialog_loading));

		}

		@Override
		protected Boolean doInBackground(Void... params) {
			mProtocolCarrier = new ProtocolCarrier(getActivity(),
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.POST_WS_COURSE_UNFOLLOW(corsoSelezionato
							.getCod()));
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
						getSherlockActivity(),
						getActivity().getResources().getString(
								R.string.dialog_error_delete),
						Toast.LENGTH_SHORT).show();
			} else if (result) {

				Toast.makeText(
						getSherlockActivity(),
						getActivity().getResources().getString(
								R.string.dialog_success_delete),
						Toast.LENGTH_SHORT).show();

			}

		}
	}

}
