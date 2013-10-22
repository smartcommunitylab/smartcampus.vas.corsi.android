package eu.trentorise.smartcampus.android.studyMate.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.studyMate.R;

// in EventsListingFragment
public class MaterialAdapter extends ArrayAdapter<MaterialItem> {

	private Context context;
	private int layoutResourceId;

	public MaterialAdapter(Context context, MaterialItem[] arr) {
		super(context, R.layout.material_row, arr);
		this.context = context;
		this.layoutResourceId = R.layout.material_row;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		MaterialItem item = getItem(position);

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(layoutResourceId, parent, false);
		}

		TextView title = (TextView) row.findViewById(R.id.title_text);
		TextView content = (TextView) row.findViewById(R.id.item_text);
		ImageView icon = (ImageView) row.findViewById(R.id.item_view);

		title.setText(item.getTitle());
		content.setText(item.getContent());
		icon.setImageResource(item.getIcon());
		icon.setPadding(
				(int) context.getResources().getDimension(
						R.dimen.activity_horizontal_margin),
				(int) context.getResources().getDimension(
						R.dimen.adpt_activity_vertical_margin),
				(int) context.getResources().getDimension(
						R.dimen.activity_horizontal_margin),
				(int) context.getResources().getDimension(
						R.dimen.adpt_activity_vertical_margin));
		content.setPadding(
				(int) context.getResources().getDimension(
						R.dimen.activity_horizontal_margin),
				(int) context.getResources().getDimension(
						R.dimen.adpt_activity_vertical_margin),
				(int) context.getResources().getDimension(
						R.dimen.activity_horizontal_margin),
				(int) context.getResources().getDimension(
						R.dimen.adpt_activity_vertical_margin));
		MaterialItem prev = null;
		if (position > 0)
			prev = getItem(position - 1);

		if (prev == null || !(prev.getTitle().equals(item.getTitle()))) {
			title.setVisibility(View.VISIBLE);
		} else {
			title.setVisibility(View.GONE);
		}
		return row;
	}

}
