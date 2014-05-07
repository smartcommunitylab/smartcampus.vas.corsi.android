package eu.trentorise.smartcampus.android.studyMate.utilities;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.net.NetworkInfo.DetailedState;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.CorsiFragment;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.DettailOfEventFragment;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.OverviewFilterFragment;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.OverviewFragment;

public class CustomPagerAdapterMyAgenda extends FragmentStatePagerAdapter {

	private final SherlockFragmentActivity mActivity;
	private Fragment mFragmentAtPos0;// tab1
	private Fragment mFragmentAtPos1;// tab2
	private FragmentManager mFragmentManager;
	FirstPageListener listener1 = new FirstPageListener();
	SecondPageListener listener2 = new SecondPageListener();
	// private static final String TABOVERVIEWTAG = "overviewTag";
	// private static final String TABCORSITAG = "corsiTag";
	public Evento eventoSelezionato;

	public CustomPagerAdapterMyAgenda(FragmentManager fm,
			SherlockFragmentActivity activity) {
		super(fm);
		this.mActivity = activity;
		this.mFragmentManager = fm;
	}

	private final class FirstPageListener implements FirstPageFragmentListener {
		public void onSwitchToNextFragment() {
			mFragmentManager.beginTransaction().remove(mFragmentAtPos0)
					.commit();
			if (mFragmentAtPos0 instanceof OverviewFragment) {
				mFragmentAtPos0 = new DettailOfEventFragment(listener1,
						OverviewFragment.eventoSelezionato);
			} else { // Instance of NextFragment
				mFragmentAtPos0 = new OverviewFragment(listener1);
			}
			notifyDataSetChanged();
		}
	}

	private final class SecondPageListener implements FirstPageFragmentListener {
		public void onSwitchToNextFragment() {
			mFragmentManager.beginTransaction().remove(mFragmentAtPos1)
					.commit();
			if (mFragmentAtPos1 instanceof CorsiFragment) {
				mFragmentAtPos1 = new OverviewFilterFragment(listener2,
						CorsiFragment.corsoSelezionato);
			} else if (mFragmentAtPos1 instanceof OverviewFilterFragment) { // Instance
																			// of
																			// NextFragment
				mFragmentAtPos1 = new DettailOfEventFragment(listener2,
						OverviewFilterFragment.eventoSelezionato);
			} else {
				mFragmentAtPos1 = new CorsiFragment(listener2);
			}
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public Fragment getItem(int position) {

		switch (position) {
		case 0:

			if (mFragmentAtPos0 == null)
				mFragmentAtPos0 = new OverviewFragment(listener1);

			return mFragmentAtPos0;
		case 1:
			if (mFragmentAtPos1 == null)
				mFragmentAtPos1 = new CorsiFragment(listener2);

			return mFragmentAtPos1;
		default:
			break;
		}

		return null;

	}
	
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
				
	}

	@Override
	public int getItemPosition(Object object) {
		
		//tab1
		if((object instanceof OverviewFragment) || (object instanceof DettailOfEventFragment)){
			if(mFragmentAtPos0 instanceof OverviewFragment || mFragmentAtPos0 instanceof DettailOfEventFragment)
				return POSITION_NONE;
		}
		
		//tab2
		if((object instanceof CorsiFragment) || (object instanceof OverviewFilterFragment)){
			if(mFragmentAtPos1 instanceof CorsiFragment || mFragmentAtPos1 instanceof OverviewFilterFragment)
				return POSITION_NONE;
		}
		
		
//		if (object instanceof OverviewFragment
//				&& mFragmentAtPos0 instanceof DettailOfEventFragment) {
//			return POSITION_NONE;
//		}
//
//		if (object instanceof DettailOfEventFragment
//				&& mFragmentAtPos0 instanceof OverviewFragment) {
//			return POSITION_NONE;
//		}
//
//		if (object instanceof CorsiFragment
//				&& mFragmentAtPos1 instanceof OverviewFilterFragment) {
//			return POSITION_NONE;
//		}
//
//		if (object instanceof OverviewFilterFragment
//				&& mFragmentAtPos1 instanceof CorsiFragment) {
//			return POSITION_NONE;
//		}
//
//		if (object instanceof DettailOfEventFragment
//				&& mFragmentAtPos1 instanceof OverviewFilterFragment) {
//			return POSITION_NONE;
//		}
//
//		if (object instanceof OverviewFilterFragment
//				&& mFragmentAtPos1 instanceof DettailOfEventFragment) {
//			return POSITION_NONE;
//		}

		return POSITION_UNCHANGED;

	}

}