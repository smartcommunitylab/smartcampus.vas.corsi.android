package eu.trentorise.smartcampus.studyMate.utilities;

import java.util.Collections;
import java.util.List;

import smartcampus.android.studyMate.finder.FindHomeCourseActivity;
import smartcampus.android.template.standalone.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studyMate.models.Commento;

public class FeedbackHandler extends AsyncTask<Void, Void, List<Commento>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	long idCourse;
	TextView tvCourseName;
	RatingBar ratingAverage;
	ExpandableListView listComments;
	public static List<Commento> feedbackInfoList;
	public SherlockFragmentActivity act;
	TextView descriptionCourse;
	Button swichFollow;
	public static ProgressDialog pd;
TextView txtMonitor;
	public FeedbackHandler(Context applicationContext, long idCourse,
			SherlockFragmentActivity act, RatingBar ratingAverage, TextView descriptionCourse, Button sFollow, TextView txtMonitor) {
		this.context = applicationContext;
		this.idCourse = idCourse;
		this.act = act;
		this.ratingAverage = ratingAverage;
		this.descriptionCourse = descriptionCourse;
		this.swichFollow = sFollow;
		this.txtMonitor = txtMonitor;
	}

	private List<Commento> getFullFeedbackById() {

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_FEEDBACK_OF_COURSE(idCourse));
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, SmartUniDataWS.TOKEN);

			if (response.getHttpStatus() == 200) {

				body = response.getBody();
				System.out.println(body);
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
		System.out.println(body);
		return Utils.convertJSONToObjects(body, Commento.class);
	}

	@Override
	protected void onPreExecute() {
		new ProgressDialog(act);
		pd = ProgressDialog.show(act, "Informazioni del corso di "
				+ FindHomeCourseActivity.courseName, "Caricamento...");

		super.onPreExecute();

	}

	@Override
	protected void onPostExecute(final List<Commento> commenti) {
		super.onPostExecute(commenti);

		if (commenti == null) {

			Toast.makeText(context, "Ops! C'e' stato un errore...",
					Toast.LENGTH_SHORT).show();

			pd.dismiss();
			act.finish();
		} else {

			if (commenti.get(0).getCorso().isSeguito() == false){
				swichFollow.setBackgroundResource(R.drawable.ic_monitor_off);
				txtMonitor.setText(R.string.label_txtMonitor_off);
			}
			else{
				swichFollow.setBackgroundResource(R.drawable.ic_monitor_on);
				txtMonitor.setText(R.string.label_txtMonitor_on);
			}
			
			swichFollow.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (commenti.get(0).getCorso().isSeguito() == false){
						swichFollow.setBackgroundResource(R.drawable.ic_monitor_on);
						//TODO: set true for user
						txtMonitor.setText(R.string.label_txtMonitor_on);
						commenti.get(0).getCorso().setSeguito(true);
						//new FeedbackHandler(context, idCourse, act, ratingAverage, descriptionCourse, swichFollow).execute();
						
					}
					else{
						swichFollow.setBackgroundResource(R.drawable.ic_monitor_off);
						commenti.get(0).getCorso().setSeguito(false);
						txtMonitor.setText(R.string.label_txtMonitor_off);
						new FeedbackHandler(context, idCourse, act, ratingAverage, descriptionCourse, swichFollow, txtMonitor).execute();
					}
					
				}
			});
			
			Collections.reverse(commenti);
			feedbackInfoList = commenti;
			act.getSupportActionBar().setTitle(
					feedbackInfoList.get(0).getCorso().getNome());
			ratingAverage.setRating((float) feedbackInfoList.get(0).getCorso()
					.getValutazione_media());

			RatingBar ratingCont = (RatingBar) act
					.findViewById(R.id.ratingBarRowContenuti);
			ratingCont.setRating(feedbackInfoList.get(0).getRating_contenuto());

			RatingBar ratingCaricoStudio = (RatingBar) act
					.findViewById(R.id.ratingBarRowCfu);
			ratingCaricoStudio.setRating(feedbackInfoList.get(0)
					.getRating_carico_studio());

			RatingBar ratingLezioni = (RatingBar) act
					.findViewById(R.id.ratingBarRowLezioni);
			ratingLezioni
					.setRating(feedbackInfoList.get(0).getRating_lezioni());

			RatingBar ratingMateriali = (RatingBar) act
					.findViewById(R.id.ratingBarRowMateriali);
			ratingMateriali.setRating(feedbackInfoList.get(0)
					.getRating_materiali());

			RatingBar ratingEsame = (RatingBar) act
					.findViewById(R.id.ratingBarRowEsame);
			ratingEsame.setRating(feedbackInfoList.get(0).getRating_esame());

			descriptionCourse.setText(feedbackInfoList.get(0).getCorso()
					.getDescrizione());

			pd.dismiss();

		}

	}

	@Override
	protected List<Commento> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return getFullFeedbackById();
	}
}