package eu.trentorise.smartcampus.android.studyMate.utilities;

//
import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class TabListener<T extends Fragment> implements ActionBar.TabListener {
	private Fragment mFragment;
	private final SherlockFragmentActivity mActivity;
	private final String mTag;
	private final Class<T> mClass;

	// private int mViewGroup = android.R.id.content;

	/**
	 * Constructor used each time a new tab is created.
	 * 
	 * @param activity
	 *            The host Activity, used to instantiate the fragment
	 * @param tag
	 *            The identifier tag for the fragment
	 * @param clz
	 *            The fragment's Class, used to instantiate the fragment
	 */
	public TabListener(SherlockFragmentActivity activity, String tag,
			Class<T> clz) {
		mActivity = activity;
		mTag = tag;
		mClass = clz;
	}

	/* The following are each of the ActionBar.TabListener callbacks */
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// SherlockFragment preInitializedFragment = (SherlockFragment)
		// mActivity
		Fragment preInitializedFragment = (Fragment) mActivity
				.getSupportFragmentManager().findFragmentByTag(mTag);
		if (preInitializedFragment != null
				&& !preInitializedFragment.equals(mFragment)) {
			ft.remove(preInitializedFragment);
		}

		if (mFragment == null) {
			// If not, instantiate and add it to the activity
			mFragment = Fragment.instantiate(mActivity, mClass.getName());
			ft.add(android.R.id.content, mFragment, mTag);
		} else {
			// If it exists, simply attach it in order to show it
			ft.attach(mFragment);
		}

		// SherlockFragment preInitializedFragment = (SherlockFragment)
		// mActivity
		// .getSupportFragmentManager().findFragmentByTag(mTag);
		//
		// if (mFragment == null && preInitializedFragment == null) {
		// mFragment = (SherlockFragment) SherlockFragment.instantiate(
		// mActivity, mClass.getName());
		// ft.add(R.id.mainlayout, mFragment, mTag);
		// } else if (mFragment != null && mFragment == preInitializedFragment)
		// {
		// // If it exists, simply attach it in order to show it
		// ft.attach(mFragment);
		// } else if (preInitializedFragment != null) {
		// mFragment = preInitializedFragment;
		// ft.attach(mFragment);
		// }

		// if (preInitializedFragment != null && mFragment !=
		// preInitializedFragment) {
		// ft.attach(preInitializedFragment);
		// }

		// // Check if the fragment is already initialized
		// if (mFragment == null) {
		// // If not, instantiate and add it to the activity
		// mFragment = Fragment.instantiate(mActivity, mClass.getName());
		// ft.add(mViewGroup, mFragment, mTag);
		// } else {
		// // If it exists, simply attach it in order to show it
		// ft.attach(mFragment);
		// }

	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// SherlockFragment preInitializedFragment = (SherlockFragment)
		// mActivity
		// .getSupportFragmentManager().findFragmentByTag(mTag);
		// if (preInitializedFragment != null
		// && mFragment != preInitializedFragment) {
		// ft.detach(preInitializedFragment);
		// }

		mActivity.getSupportFragmentManager().popBackStack(mTag,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
		if (mFragment != null) {
			// Detach the fragment, because another one is being attached
			ft.detach(mFragment);
		}
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// User selected the already selected tab. Usually do nothing.
	}
}