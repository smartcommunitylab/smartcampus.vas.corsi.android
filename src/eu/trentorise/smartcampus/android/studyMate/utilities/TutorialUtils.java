package eu.trentorise.smartcampus.android.studyMate.utilities;

import com.github.espiandev.showcaseview.TutorialHelper;
import com.github.espiandev.showcaseview.TutorialItem;
import com.github.espiandev.showcaseview.TutorialHelper.TutorialProvider;

import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.studymate.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class TutorialUtils {

	private static final String NAME = "studymatetut";

	private static final String FIRSTLAUNCH = "studymatefirst";

	private static SharedPreferences getPrefs(Context ctx) {
		return ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	}

	public static void disableTutorial(Context ctx) {
		SharedPreferences sp = getPrefs(ctx);
		sp.edit().putBoolean(FIRSTLAUNCH, false).commit();
	}

	public static void enableTutorial(Context ctx) {
		SharedPreferences sp = getPrefs(ctx);
		sp.edit().putBoolean(FIRSTLAUNCH, true).commit();
	}

	public static boolean isTutorialEnabled(Context ctx) {
		return getPrefs(ctx).getBoolean(FIRSTLAUNCH, true);
	}

	public static TutorialHelper getTutorial(final Activity act) {
		TutorialProvider tutProvider = new TutorialProvider() {

			@Override
			public int size() {
				return 5;
			}

			@Override
			public void onTutorialFinished() {
				disableTutorial(act);
			}

			@Override
			public void onTutorialCancelled() {
				disableTutorial(act);
			}

			@Override
			public TutorialItem getItemAt(int pos) {
				View v = null;
				int[] location = new int[2];
				switch (pos) {
				case 0:
					v = act.findViewById(R.id.my_agenda_btn);
					if (v != null && v.isShown()) {
						v.getLocationOnScreen(location);
						location[0] += convertPixelsToDp(
								(v.getWidth() / 2) - 18, act);
						location[1] += convertDpToPixel(5, act);
						return new TutorialItem("search", location,
								(int) (v.getWidth() / 2.5f),
								act.getString(R.string.my_agenda_btn_label),
								act.getString(R.string.tut_agenda));
					}
					break;
				case 1:
					v = act.findViewById(R.id.find_courses_btn);
					if (v != null && v.isShown()) {
						v.getLocationOnScreen(location);
						location[0] += convertPixelsToDp(
								v.getWidth() / 2, act);
						location[1] += convertDpToPixel(8, act);
						return new TutorialItem("search", location,
								(int) (v.getWidth() / 2.5f),
								act.getString(R.string.find_courses_btn_label),
								act.getString(R.string.tut_searchcourse));
					}
					break;
				case 2:
					v = act.findViewById(R.id.rate_btn);
					v.getLocationOnScreen(location);
					location[0] += convertPixelsToDp(
							(v.getWidth() / 2) - 8, act);
					location[1] += convertDpToPixel(8, act);
					if (v != null && v.isShown()) {
						return new TutorialItem("search", location,
								(int) (v.getWidth() / 2.5f),
								act.getString(R.string.rate),
								act.getString(R.string.tut_rate));
					}
					break;
				case 3:
					v = act.findViewById(R.id.notices_btn);
					v.getLocationOnScreen(location);
					location[0] += convertPixelsToDp(
							v.getWidth() / 2, act);
					location[1] += convertDpToPixel(12, act);
					if (v != null && v.isShown()) {
						return new TutorialItem("search", location,
								(int) (v.getWidth() / 2.5f),
								act.getString(R.string.notices_btn_label),
								act.getString(R.string.tut_notifications));
					}
					break;
				case 4:
					v = act.findViewById(R.id.phl_btn);
					v.getLocationOnScreen(location);
					location[0] += convertPixelsToDp((v.getWidth() / 2) - 18,
							act);
					location[1] += convertDpToPixel(8, act);
					if (v != null && v.isShown()) {
						return new TutorialItem("search", location,
								(int) (v.getWidth() / 2.5f),
								act.getString(R.string.phl_btn_label),
								act.getString(R.string.tut_materials));
					}
					break;
				}
				return null;
			}
		};
		return new TutorialHelper(act, tutProvider);
	}

	/**
	 * This method converts dp unit to equivalent pixels, depending on device
	 * density.
	 * 
	 * @param dp
	 *            A value in dp (density independent pixels) unit. Which we need
	 *            to convert into pixels
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent px equivalent to dp depending on
	 *         device density
	 */
	public static int convertDpToPixel(float dp, Context context) {
		// Resources resources = context.getResources();
		// DisplayMetrics metrics = resources.getDisplayMetrics();
		// float px = dp * (metrics.densityDpi / 160f);
		// return px;
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * This method converts device specific pixels to density independent
	 * pixels.
	 * 
	 * @param px
	 *            A value in px (pixels) unit. Which we need to convert into db
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent dp equivalent to px value
	 */
	public static float convertPixelsToDp(float px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;
	}
}