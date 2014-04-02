package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.DialogCourseFragment;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.OverviewFilterFragment;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

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

							// Pass Data to other Fragment
							CorsoCarriera corsoSelezionato = new CorsoCarriera();
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
			
			listViewCorsiPersonali.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					CorsoCarriera corsoSelezionato = new CorsoCarriera();
					corsoSelezionato = result.get(arg2);
					
					DialogCourseFragment dialog = new DialogCourseFragment(corsoSelezionato);
					dialog.show(currentFragment.getFragmentManager(), currentFragment.getTag());
//					MenuDialogFragment menuCourse = new MenuDialogFragment(corsoSelezionato);
//					FragmentManager fm = menuCourse.getFragmentManager();
//					menuCourse.setRetainInstance(true);
//					menuCourse.show(fm, corsoSelezionato.getName());
//					AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//					LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
//			        View convertView = (View) inflater.inflate(R.layout.dialog_menu_coursesinterest, null);
//			        alertDialog.setView(convertView);
//			        alertDialog.setTitle(corsoSelezionato.getName());
//			        ListView lv = (ListView) convertView.findViewById(R.id.listViewOptions);
//			        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,context.getResources().getStringArray(R.array.dialogAgenda));
//			        lv.setAdapter(adapter);
//			        alertDialog.show();
					
					//MenuDialogFragment dialogCourseInterest = new MenuDialogFragment(corsoSelezionato);
						
					return true;
				}
			});

			pd.dismiss();

		}
	}

	
//	public class MenuDialogFragment extends DialogFragment {
//		CorsoCarriera corsoSelezionato;
//		
//		public MenuDialogFragment(CorsoCarriera corsoSelezionato) {
//			this.corsoSelezionato = corsoSelezionato;
//		}
//	    @Override
//	    public Dialog onCreateDialog(Bundle savedInstanceState) {
//	        // Use the Builder class for convenient dialog construction
//	        AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
//	        
//	        
//			Bundle data = new Bundle();
//			data.putSerializable(
//					Constants.CORSO_CARRIERA_SELECTED,
//					corsoSelezionato);
//			data.putString(Constants.COURSE_NAME,
//					corsoSelezionato.getName());
//			
//			builder.setTitle(corsoSelezionato.getName());
//			builder.setItems(R.array.dialogAgenda, new OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
//					
//	               
//	        // Create the AlertDialog object and return it
//	        return builder.create();
//	    }
	    
	    
//	    @Override
//	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//	    		Bundle savedInstanceState) {
//	    	// TODO Auto-generated method stub
//	    	View view = inflater.inflate(R.layout.dialog_courses_layout, container);
//	    	
//	    	return view;
//	    }
//	}
	
	
	@Override
	protected List<CorsoCarriera> doInBackground(Bundle... params) {
		bundleParam = params[0];

		return getAllPersonalCourses();
	}
	
	

}
