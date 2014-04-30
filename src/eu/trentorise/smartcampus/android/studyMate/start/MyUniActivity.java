package eu.trentorise.smartcampus.android.studyMate.start;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.android.common.LauncherHelper;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.finder.FindHomeActivity;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.MyAgendaActivity;
import eu.trentorise.smartcampus.android.studyMate.notices.NoticesActivity;
import eu.trentorise.smartcampus.android.studyMate.rate.CoursesPassedActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.android.studyMate.utilities.TutorialUtils;
import eu.trentorise.smartcampus.network.RemoteConnector;
import eu.trentorise.smartcampus.network.RemoteConnector.CLIENT_TYPE;
import eu.trentorise.smartcampus.profileservice.BasicProfileService;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

public class MyUniActivity extends SherlockActivity {

	public static final String APP_ID = "studymate";
	//
	public static final String SERVER_URL = "https://vas.smartcampuslab.it/core.communicator";
	public static final String AUTH_URL = "https://ac.smartcampuslab.it/aac";
	private static Context mContext;
	private static SCAccessProvider accessProvider = null;
	public static ProgressDialog pd;
	/**
	 * Provides access to the authentication mechanism. Used to retrieve the
	 * token
	 */
	public ProtocolCarrier mProtocolCarrier;
	public String body;
	public static String userAuthToken;
	public static BasicProfile bp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();
		if (LauncherHelper.isLauncherInstalled(this, true)) {
			if (!isUserConnectedToInternet(mContext)) {
				Toast.makeText(mContext, R.string.internet_connection,
						Toast.LENGTH_SHORT).show();
				MyUniActivity.this.finish();
			} else {
				new LoadUserDataFromACServiceTask().execute();
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!isUserConnectedToInternet(mContext)) {
			Toast.makeText(mContext, R.string.internet_connection,
					Toast.LENGTH_SHORT).show();
			MyUniActivity.this.finish();
		} else {
			setContentView(R.layout.activity_my_uni);

			findViewById(R.id.my_agenda_btn).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(MyUniActivity.this,
									MyAgendaActivity.class);
							MyUniActivity.this.startActivity(intent);
						}
					});

			findViewById(R.id.find_courses_btn).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(MyUniActivity.this,
									FindHomeActivity.class);
							MyUniActivity.this.startActivity(intent);
						}
					});

			findViewById(R.id.phl_btn).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							Toast.makeText(
									getApplicationContext(),
									getResources().getString(
											R.string.dialog_coming_soon),
									Toast.LENGTH_SHORT).show();
							// Intent intent = new Intent(MyUniActivity.this,
							// PHLActivity.class);
							// MyUniActivity.this.startActivity(intent);
						}
					});

			findViewById(R.id.notices_btn).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(MyUniActivity.this,
									NoticesActivity.class);
							MyUniActivity.this.startActivity(intent);
						}
					});

			findViewById(R.id.rate_btn).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(MyUniActivity.this,
									CoursesPassedActivity.class);
							MyUniActivity.this.startActivity(intent);
						}
					});

			findViewById(R.id.gruppi_studio_btn).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(
									getApplicationContext(),
									getResources().getString(
											R.string.dialog_coming_soon),
									Toast.LENGTH_SHORT).show();
							// Intent intent = new Intent(MyUniActivity.this,
							// Lista_GDS_activity.class);
							// MyUniActivity.this.startActivity(intent);
						}
					});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.show_tutorial) {
			TutorialUtils.enableTutorial(this);
			TutorialUtils.getTutorial(this).showTutorials();
		}
		return super.onOptionsItemSelected(item);
	}

	public static SCAccessProvider getAccessProvider() {
		if (accessProvider == null)
			accessProvider = SCAccessProvider.getInstance(mContext);
		return accessProvider;
	}

	public static String getAuthToken() throws AACException {
		String mToken;
		mToken = getAccessProvider().readToken(mContext);
		return mToken;
	}

	public class LoadUserDataFromACServiceTask extends
			AsyncTask<Void, Void, BasicProfile> {

		ArrayList<CorsoCarriera> corsicarrierastudente;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pd = ProgressDialog.show(MyUniActivity.this, getResources()
					.getString(R.string.dialog_sync_data), getResources()
					.getString(R.string.dialog_loading));
		}

		@Override
		protected BasicProfile doInBackground(Void... params) {
			try {
				RemoteConnector.setClientType(CLIENT_TYPE.CLIENT_ACCEPTALL);
				BasicProfileService service = new BasicProfileService(AUTH_URL);
				bp = service.getBasicProfile(getAuthToken());
				System.out.println(bp.getName());
				System.out.println("USERID: " + bp.getUserId());
				System.out.println("user token: " + getAuthToken());
				// init connector
				// PushServiceConnector connector = new PushServiceConnector();
				// try {
				// connector.init(getApplicationContext(), getAuthToken(),
				// APP_ID, SERVER_URL);
				// } catch (CommunicatorConnectorException e) {
				// e.printStackTrace();
				// }
				// proviamo a recuperare i dati studente
				mProtocolCarrier = new ProtocolCarrier(MyUniActivity.this,
						SmartUniDataWS.TOKEN_NAME);
				MessageResponse response;
				MessageRequest request = new MessageRequest(
						SmartUniDataWS.URL_WS_SMARTUNI,
						SmartUniDataWS.GET_WS_STUDENT_DATA);
				request.setMethod(Method.GET);
				try {
					response = mProtocolCarrier.invokeSync(request,
							SmartUniDataWS.TOKEN_NAME,
							MyUniActivity.getAuthToken());
					if (response.getHttpStatus() == 200) {

						// il body corrisponde al jsonstudente!! allora lo
						// facciamo vedere XD
						body = response.getBody();
						String jsonstudente = body;
						save("studenteSessioneJSON", jsonstudente);
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

				// proviamo a recueprare i dati relativi ai corsi dello
				// studente
				mProtocolCarrier = new ProtocolCarrier(MyUniActivity.this,
						SmartUniDataWS.TOKEN_NAME);
				MessageResponse response1;
				MessageRequest request1 = new MessageRequest(
						SmartUniDataWS.URL_WS_SMARTUNI,
						SmartUniDataWS.GET_WS_FREQUENTEDCOURSES_SYNC);
				request1.setMethod(Method.GET);
				try {
					response1 = mProtocolCarrier.invokeSync(request1,
							SmartUniDataWS.TOKEN_NAME,
							MyUniActivity.getAuthToken());
					if (response1.getHttpStatus() == 200) {

						body = response1.getBody();
						String jsoncorsidellostudente = body;
						save("corsiStudente", jsoncorsidellostudente);
						corsicarrierastudente = (ArrayList<CorsoCarriera>) Utils
								.convertJSONToObjects(jsoncorsidellostudente,
										CorsoCarriera.class);
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

				return bp;
			} catch (Exception e) {
				e.printStackTrace();
				// System.out.println(e.getMessage());
				return null;
			}

		}

		@Override
		protected void onPostExecute(BasicProfile result) {
			super.onPostExecute(result);
			pd.dismiss();
			if (TutorialUtils.isFirstLaunch(mContext)) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MyUniActivity.this);
				builder.setTitle(R.string.welcome_title)
						.setMessage(R.string.welcome_msg)
						.setOnCancelListener(
								new DialogInterface.OnCancelListener() {

									@Override
									public void onCancel(DialogInterface arg0) {
										arg0.dismiss();
										TutorialUtils.getTutorial(
												MyUniActivity.this)
												.showTutorials();
									}
								})
						.setPositiveButton(getString(R.string.ok),
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										TutorialUtils.getTutorial(
												MyUniActivity.this)
												.showTutorials();

									}
								});
				builder.create().show();
				TutorialUtils.disableFirstLanch(MyUniActivity.this);
			} else if (TutorialUtils.isTutorialEnabled(MyUniActivity.this)) {
				TutorialUtils.getTutorial(MyUniActivity.this).showTutorials();
			}
		}

		private void save(String key, String jsonTosaveinSharedP) {
			SharedPreferences sharedPreferences = MyUniActivity.this
					.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString(key, jsonTosaveinSharedP);
			editor.commit();
		}

	}

	public static boolean isUserConnectedToInternet(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
		if (netInfo != null) {
			return netInfo.isConnected();
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		TutorialUtils.getTutorial(this).onTutorialActivityResult(requestCode,
				resultCode, data);
	}

}