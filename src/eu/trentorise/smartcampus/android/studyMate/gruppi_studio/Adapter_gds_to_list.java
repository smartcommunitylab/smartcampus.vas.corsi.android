package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import eu.trentorise.smartcampus.android.studyMate.R;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.PushNotificationGds;
import eu.trentorise.smartcampus.android.studyMate.utilities.NotificationCenterGds;

public class Adapter_gds_to_list extends ArrayAdapter<GruppoDiStudio> {

	ArrayList<GruppoDiStudio> entries;
	Context context;
	List<PushNotificationGds> listNotificationsGds;

	public Adapter_gds_to_list(Context context, int textViewResourceId,
			ArrayList<GruppoDiStudio> objects) {
		super(context, textViewResourceId, objects);
		this.entries = objects;
		this.context = context;
	}

	public Adapter_gds_to_list(Context context, int textViewResourceId,
			ArrayList<GruppoDiStudio> objects,
			List<PushNotificationGds> listNotifications) {
		super(context, textViewResourceId, objects);
		this.entries = objects;
		this.context = context;
		this.listNotificationsGds = listNotifications;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		GruppoDiStudio currentGDS = getItem(position);
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.gds_row, null);
		}

		TextView nome_gds = (TextView) row.findViewById(R.id.gds_name);
		// ImageView logo_gds = (ImageView) row.findViewById(R.id.gds_logo);
		TextView nome_corso = (TextView) row
				.findViewById(R.id.gds_name_of_course);
		nome_corso.setTextColor(Color.BLACK);
		TextView type_event = (TextView) row.findViewById(R.id.gds_name);
		type_event.setTextColor(Color.BLACK);
		nome_gds.setText(currentGDS.getNome());
		nome_gds.setTextColor(Color.BLACK);
		nome_corso.setText(currentGDS.getMateria());
		nome_corso.setTextColor(Color.BLACK);

		int numberOfNotifications = getNumberOfUnreadNotifications(currentGDS
				.getId());
		ImageView imageNotification = (ImageView) row
				.findViewById(R.id.ImageViewNotification);

		if (numberOfNotifications >= 1) {

			int imageResource = getFlagResource(context, "notification_"
					+ numberOfNotifications);

			if(imageResource == 0)
				return row;
			// int imageResource = context.getResources().getIdentifier(uri,
			// null, context.getPackageName());
			Drawable resImage = context.getResources().getDrawable(
					imageResource);
			
			
			imageNotification.setImageDrawable(resImage);
			imageNotification.setVisibility(View.VISIBLE);
		} else {
			imageNotification.setVisibility(View.GONE);
		}

		return row;
	}

	public ArrayList<GruppoDiStudio> getEntries() {
		return entries;
	}

	public int getNumberOfUnreadNotifications(Long gdsId) {
		int count = 0;

		for (PushNotificationGds notif : listNotificationsGds) {
			if (notif.getGdsId().equals(gdsId)) {
				count++;
			}
		}

		return count;
	}

	public int getFlagResource(Context context, String name) {
		int resId = context.getResources().getIdentifier(name, "drawable",
				"eu.trentorise.smartcampus.android.studyMate");
		return resId;
	}

}
