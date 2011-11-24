package org.twuni.common.android.listener;

import java.util.List;

import android.support.v4.view.ViewPager.OnPageChangeListener;

public abstract class OnPageSelectedListener<T> implements OnPageChangeListener {

	protected final List<T> items;

	public OnPageSelectedListener( List<T> items ) {
		this.items = items;
	}

	@Override
	public void onPageScrollStateChanged( int state ) {
	}

	@Override
	public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {
	}

	@Override
	public void onPageSelected( int position ) {
		onItemSelected( items.get( position % items.size() ) );
	}

	protected abstract void onItemSelected( T item );

}
