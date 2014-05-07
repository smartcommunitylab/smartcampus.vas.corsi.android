package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.NetworkInfo.DetailedState;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.finder.ResultSearchedActivity;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.CoursesHandler;
import eu.trentorise.smartcampus.android.studyMate.utilities.CustomPagerAdapterMyAgenda;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventsHandler;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.android.studyMate.utilities.TabListener;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

public class MyAgendaActivity extends SherlockFragmentActivity {

	public enum ChildActivity {
		ADD_EVENT_FOR_COURSES, ADD_RATING, NONE
	}

	public static String corsoNameMA;
	public static List<Evento> listEvents;
	public static List<CorsoCarriera> listCoursesOnAgenda;
	String idCorsoMA;
	private ViewPager myPager;
	private CustomPagerAdapterMyAgenda adapter;
	private ActionBar ab;
	public ActionBar.TabListener tabListener;
	Tab overviewTab;
	Tab corsiTab;
	private static final String TABOVERVIEWTAG = "overviewTag"; 
	private static final String TABCORSITAG = "corsiTag"; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.myagenda_activity);

		Intent intent = getIntent();
		corsoNameMA = intent.getStringExtra(Constants.COURSE_NAME);

		ab = getSupportActionBar();

		setTitle(getResources().getString(R.string.title_activity_my_agenda));
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		
		new SyncAgenda().execute();

	

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		setTitle("Agenda");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.add_event:
			return false;
			// case R.id.menu_modify_event:
			// return false;
		case R.id.menu_delete_event:
			return false;
		case R.id.menu_add_event_4_course:
			return false;
		default:
			break;

		}
		return false;
	}

	
	public static List<Evento> getListEvents(){
		return listEvents;
	}
	
	public static List<CorsoCarriera> getListCoursesOnAgenda(){
		return listCoursesOnAgenda;
	}
	
	public class SyncAgenda extends AsyncTask<Void, Void, List<Evento>> {
		
		private ProgressDialog pd;
		private ProtocolCarrier mProtocolCarrier;
		private String body;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = ProgressDialog.show(MyAgendaActivity.this, getResources()
					.getString(R.string.title_activity_my_agenda),
					getResources().getString(R.string.loading));
		}

		@Override
		protected List<Evento> doInBackground(Void... params) {
			
				listEvents = getAllPersonalEvents();
				
				listCoursesOnAgenda = getAllPersonalCourses();

			return listEvents;
			
		}
		
		@Override
		protected void onPostExecute(List<Evento> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			pd.dismiss();
			
			
			// Create and set adapter
			adapter = new CustomPagerAdapterMyAgenda(getSupportFragmentManager(), MyAgendaActivity.this);
			myPager = (ViewPager) findViewById(R.id.customviewpager);
			myPager.setAdapter(adapter);
			myPager.setCurrentItem(0);
			
			
			
			tabListener = new ActionBar.TabListener() {
				
				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onTabSelected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					myPager.setCurrentItem(tab.getPosition());
										
				}
				
				@Override
				public void onTabReselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					
				}
			};
			
			ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

			/** TabHost will have Tabs */
			String tab1_txt = getResources().getString(R.string.tab_home);
			String tab2_txt = getResources().getString(R.string.tab_courses);
			
			
			overviewTab = ab.newTab().setText(tab1_txt);
			corsiTab = ab.newTab().setText(tab2_txt);
			
			
			overviewTab.setTabListener(tabListener);
			corsiTab.setTabListener(tabListener);
			ab.addTab(overviewTab);
			ab.addTab(corsiTab);
			
			
			myPager.setOnPageChangeListener(
		            new ViewPager.SimpleOnPageChangeListener() {
		                @Override
		                public void onPageSelected(int position) {
		                    // When swiping between pages, select the
		                    // corresponding tab.
		                	super.onPageSelected(position);
		                	ab.setSelectedNavigationItem(position);
		                }
		            });
			
		}
		
		
		// return list of all courses of all departments
		private List<CorsoCarriera> getAllPersonalCourses() {
			mProtocolCarrier = new ProtocolCarrier(MyAgendaActivity.this,
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
		
	
		
		
		private List<Evento> getAllPersonalEvents() {

			mProtocolCarrier = new ProtocolCarrier(MyAgendaActivity.this,
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
	}
	
	
	public void onBackPressed() {
        if(myPager.getCurrentItem() == 0) {
            if (adapter.getItem(0) instanceof DettailOfEventFragment) {
                ((DettailOfEventFragment) adapter.getItem(0)).backPressed();
            }
            else if (adapter.getItem(0) instanceof OverviewFragment) {
                finish();
            }
        }
        
        
        if(myPager.getCurrentItem() == 1) {
            if (adapter.getItem(1) instanceof OverviewFilterFragment) {
                ((OverviewFilterFragment) adapter.getItem(1)).backPressed();
            }
            if (adapter.getItem(1) instanceof DettailOfEventFragment) {
                ((DettailOfEventFragment) adapter.getItem(1)).backPressed();
            }
            else if (adapter.getItem(1) instanceof CorsiFragment) {
                finish();
            }
        }
    }

}
