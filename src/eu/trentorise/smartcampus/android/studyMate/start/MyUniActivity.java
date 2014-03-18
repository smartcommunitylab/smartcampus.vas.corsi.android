package eu.trentorise.smartcampus.android.studyMate.start;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockActivity;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.finder.FindHomeActivity;
import eu.trentorise.smartcampus.android.studyMate.gruppi_studio.Lista_GDS_activity;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.MyAgendaActivity;
import eu.trentorise.smartcampus.android.studyMate.notices.NoticesActivity;
import eu.trentorise.smartcampus.android.studyMate.rate.AddRatingFromCoursesPassed;
import eu.trentorise.smartcampus.android.studyMate.rate.CoursesPassedActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.communicator.CommunicatorConnectorException;
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
import eu.trentorise.smartcampus.pushservice.PushServiceConnector;
import eu.trentorise.smartcampus.studymate.R;

public class MyUniActivity extends SherlockActivity {

	/** Logging tag */
	private static final String TAG = "Main";

	public static final String APP_ID = "studymate";
	//
	public static final String SERVER_URL = "https://vas-dev.smartcampuslab.it/core.communicator";
	private static Context mContext;
	private static SCAccessProvider accessProvider = null;
	public static ProgressDialog pd;
	/**
	 * Provides access to the authentication mechanism. Used to retrieve the
	 * token
	 */
	public ProtocolCarrier mProtocolCarrier;
	public String body;
	// public static String userAuthToken;
	public static BasicProfile bp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();
		new LoadUserDataFromACServiceTask().execute();
	}

	@Override
	protected void onStart() {
		super.onStart();
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

		// findViewById(R.id.phl_btn).setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(MyUniActivity.this,
		// PHLActivity.class);
		// MyUniActivity.this.startActivity(intent);
		// }
		// });

		findViewById(R.id.notices_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MyUniActivity.this,
								NoticesActivity.class);
						MyUniActivity.this.startActivity(intent);
					}
				});

		findViewById(R.id.rate_btn).setOnClickListener(new OnClickListener() {
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
						Intent intent = new Intent(MyUniActivity.this,
								Lista_GDS_activity.class);
						MyUniActivity.this.startActivity(intent);
					}
				});

	}

	public static SCAccessProvider getAccessProvider() {
		if (accessProvider == null)
			accessProvider = SCAccessProvider.getInstance(mContext);
		return accessProvider;
	}

	public static String getAuthToken() throws AACException {
		String mToken;
		mToken = getAccessProvider().readToken(mContext);
		System.out.println(mToken);
		return mToken;
	}

	public class LoadUserDataFromACServiceTask extends
			AsyncTask<Void, Void, BasicProfile> {

		ArrayList<CorsoCarriera> corsicarrierastudente;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			pd = ProgressDialog.show(MyUniActivity.this,
					"Sincronizzazione dei tuoi dati in corso.", "Caricamento...");
		}

		@Override
		protected BasicProfile doInBackground(Void... params) {
			try {
				RemoteConnector.setClientType(CLIENT_TYPE.CLIENT_ACCEPTALL);
				BasicProfileService service = new BasicProfileService(
						"https://vas-dev.smartcampuslab.it/aac");
				bp = service.getBasicProfile(getAuthToken());
				System.out.println(bp.getName());
				System.out.println("USERID: " + bp.getUserId());
				// init connector
				PushServiceConnector connector = new PushServiceConnector();
				try {
					connector.init(getApplicationContext(), getAuthToken(),
							APP_ID, SERVER_URL);
				} catch (CommunicatorConnectorException e) {
					e.printStackTrace();
				}
				if (bp != null) {
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
						// TODO Auto-generated catch block
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
					request.setMethod(Method.GET);
					try {
						response1 = mProtocolCarrier.invokeSync(request1,
								SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());
						if (response1.getHttpStatus() == 200) {

							body = response1.getBody();
							String jsoncorsidellostudente = body;
							save("corsiStudente", jsoncorsidellostudente);
							corsicarrierastudente = (ArrayList<CorsoCarriera>) Utils
									.convertJSONToObjects(
											jsoncorsidellostudente,
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// proviamo a recueprare i dati relativi al corso di laurea
					// di
					// uno studente tramite il codice prensete all'interno di
					// uno
					// dei corsicarriera che ci siamo appena presi
					long cod = corsicarrierastudente.get(0).getId();
					mProtocolCarrier = new ProtocolCarrier(MyUniActivity.this,
							SmartUniDataWS.TOKEN_NAME);
					MessageResponse response2;
					MessageRequest request2 = new MessageRequest(
							SmartUniDataWS.URL_WS_SMARTUNI,
							SmartUniDataWS.GET_WS_COURSES_DETAILS(cod));
					request.setMethod(Method.GET);
					try {
						response2 = mProtocolCarrier.invokeSync(request2,
								SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());
						if (response2.getHttpStatus() == 200) {

							body = response2.getBody();
							String jsonattivitadidattica = body;
							save("attivitaDidatticaStudente",
									jsonattivitadidattica);
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
				}

				return bp;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return null;
			}

		}

		@Override
		protected void onPostExecute(BasicProfile result) {
			super.onPostExecute(result);
		}

		private void save(String key, String jsonTosaveinSharedP) {
			pd.dismiss();
			SharedPreferences sharedPreferences = MyUniActivity.this
					.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString(key, jsonTosaveinSharedP);
			editor.commit();
		}
	}

}