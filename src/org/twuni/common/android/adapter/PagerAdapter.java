package org.twuni.common.android.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class PagerAdapter<T, V extends View> extends android.support.v4.view.PagerAdapter {

	private final int resourceId;
	private final Map<Integer, V> views = new HashMap<Integer, V>();
	protected final List<T> items;

	protected abstract void adapt( T item, V targetView );

	public PagerAdapter( int resourceId, List<T> items ) {
		this.resourceId = resourceId;
		this.items = items;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public void destroyItem( View collection, int position, Object view ) {
		position = position % items.size();
		( (ViewGroup) collection ).removeView( (V) view );
		views.remove( Integer.valueOf( position ) );
	}

	@Override
	public void finishUpdate( View view ) {
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object instantiateItem( View collection, int position ) {

		position = position % items.size();

		@SuppressWarnings( "unchecked" )
		V view = (V) LayoutInflater.from( collection.getContext() ).inflate( resourceId, null );

		adapt( items.get( position ), view );

		( (ViewGroup) collection ).addView( view, 0 );
		views.put( Integer.valueOf( position ), view );

		return view;

	}

	@Override
	public boolean isViewFromObject( View view, Object object ) {
		return view == null ? view == object : view.equals( object );
	}

	@Override
	public void restoreState( Parcelable state, ClassLoader classLoader ) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate( View view ) {
	}

	public T getItem( int position ) {
		position = position % items.size();
		return items.get( position );
	}

	public V getView( int position ) {
		position = position % items.size();
		return views.get( Integer.valueOf( position ) );
	}

}
