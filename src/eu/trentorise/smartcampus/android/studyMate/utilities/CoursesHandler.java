package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.finder.FindHomeCourseActivity;
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
import it.smartcampuslab.studymate.R;

public class CoursesHandler extends
		AsyncTask<Bundle, Void, List<CorsoCarriera>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	public List<AttivitaDidattica> coursesFiltered;
	ListView listViewCorsiPersonali;
	public String body;
	public static ProgressDialog pd;
	public Fragment currentFragment;
	public SherlockFragmentActivity currentSherlock;
	public Bundle bundleParam;
	public CorsoCarriera corsoSelezionato;

	public CoursesHandler(Context applicationContext, ListView listViewCorsi,
			Fragment currentFragment, SherlockFragmentActivity currentSherlock) {
		this.context = applicationContext;
		this.listViewCorsiPersonali = listViewCorsi;
		this.currentFragment = currentFragment;
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
			e.printStackTrace();
		}

		return Utils.convertJSONToObjects(body, CorsoCarriera.class);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		new ProgressDialog(currentSherlock);
		pd = ProgressDialog.show(currentSherlock, context.getResources()
				.getString(R.string.dialog_courses_events), context
				.getResources().getString(R.string.dialog_loading));
	}

	@Override
	protected void onPostExecute(final List<CorsoCarriera> result) {
		super.onPostExecute(result);
		if (result == null) {

			Toast.makeText(context,
					context.getResources().getString(R.string.invalid_career),
					Toast.LENGTH_SHORT).show();
			currentSherlock.finish();
		} else {
			TitledItem[] items = new TitledItem[result.size()];

			int i = 0;
			for (CorsoCarriera s : result) {
				if (s.getResult().equals("-1"))
					items[i++] = new TitledItem(context.getResources()
							.getString(R.string.header_course_interest),
							s.getName());
				else
					items[i++] = new TitledItem(context.getResources()
							.getString(R.string.header_course_career),
							s.getName());

			}

			TitledAdapter adapter = new TitledAdapter(context, items);

			listViewCorsiPersonali.setAdapter(adapter);

			listViewCorsiPersonali
					.setOnItemClickListener(new ListView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {

							currentSherlock.supportInvalidateOptionsMenu();

							corsoSelezionato = result.get(arg2);
							Bundle data = new Bundle();
							data.putSerializable(
									Constants.CORSO_CARRIERA_SELECTED,
									corsoSelezionato);
							data.putString(Constants.COURSE_NAME,
									corsoSelezionato.getName());
							FragmentTransaction ft = currentSherlock
									.getSupportFragmentManager()
									.beginTransaction();
							Fragment fragment = new OverviewFilterFragment();
							fragment.setArguments(data);
							ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
							ft.replace(currentFragment.getId(), fragment,
									currentFragment.getTag());
							ft.addToBackStack(currentFragment.getTag());
							ft.commit();
						}
					});

			listViewCorsiPersonali
					.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {

							AlertDialog.Builder builderSingle = new AlertDialog.Builder(
									currentSherlock);
							builderSingle.setTitle(result.get(arg2).getName());

							corsoSelezionato = result.get(arg2);

							// ListView mLocationList = (ListView)
							// currentSherlock.findViewById(R.id.lv);

							if (result.get(arg2).getResult().equals("-1")) {

								final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
										currentSherlock,
										android.R.layout.simple_list_item_1);

								String[] cI = currentSherlock.getResources()
										.getStringArray(
												R.array.dialogAgendaInterest);
								adapter.add(cI[0]);
								// adapter.add(cI[1]);//////////////////////////////////////////////////////////////////////PROVVISORIO
								builderSingle.setAdapter(adapter,
										new ItemMenuCourseListener());
							} else {
								final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
										currentSherlock,
										android.R.layout.simple_list_item_1);
								String[] cL = currentSherlock.getResources()
										.getStringArray(
												R.array.dialogAgendaCareer);
								adapter.add(cL[0]);
								builderSingle.setAdapter(adapter,
										new ItemMenuCourseListener());
							}

							builderSingle.show();
							return true;
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

	public class AsyncCourseAd extends AsyncTask<Void, Void, AttivitaDidattica> {

		private ProtocolCarrier mProtocolCarrier;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(currentSherlock);
			pd = ProgressDialog.show(currentSherlock, context.getResources()
					.getString(R.string.dialog_waiting_goto_home), context
					.getResources().getString(R.string.dialog_loading));

		}

		@Override
		protected AttivitaDidattica doInBackground(Void... params) {
			mProtocolCarrier = new ProtocolCarrier(context,
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
						currentSherlock,
						currentSherlock.getResources().getString(
								R.string.dialog_error_redirect),
						Toast.LENGTH_SHORT).show();
			} else {

				Intent i = new Intent(currentSherlock,
						FindHomeCourseActivity.class);

				i.putExtra(Constants.COURSE_NAME, result.getDescription());
				i.putExtra(Constants.COURSE_ID, result.getAdId());
				i.putExtra(Constants.AD_COD, result.getAdCod());

				pd.dismiss();

				currentSherlock.startActivity(i);
			}

		}
	}

	public class AsyncDeleteCourseInterest extends
			AsyncTask<Void, Void, Boolean> {

		private ProtocolCarrier mProtocolCarrier;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(currentSherlock);
			pd = ProgressDialog.show(currentSherlock, context.getResources()
					.getString(R.string.dialog_waiting_goto_home), context
					.getResources().getString(R.string.dialog_loading));

		}

		@Override
		protected Boolean doInBackground(Void... params) {
			mProtocolCarrier = new ProtocolCarrier(context,
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
						currentSherlock,
						context.getResources().getString(
								R.string.dialog_error_delete),
						Toast.LENGTH_SHORT).show();
			} else if (result) {

				Toast.makeText(
						currentSherlock,
						context.getResources().getString(
								R.string.dialog_success_delete),
						Toast.LENGTH_SHORT).show();

			}

		}
	}

}
