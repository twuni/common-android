package org.twuni.common.android.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class ListAdapter<T, V extends View> extends BaseAdapter {

	private List<T> items;
	private final int viewResourceId;

	public ListAdapter( int viewResourceId ) {
		this( viewResourceId, new ArrayList<T>() );
	}

	public ListAdapter( int viewResourceId, List<T> items ) {
		this.viewResourceId = viewResourceId;
		this.items = items;
	}

	protected abstract void adapt( T item, V targetView );

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem( int position ) {
		return items.get( position );
	}

	@Override
	public long getItemId( int position ) {
		return getItem( position ).hashCode();
	}

	public List<T> getItems() {
		return items;
	}

	@SuppressWarnings( "unchecked" )
	private V getTargetView( View convertView, View parent ) {
		return convertView != null ? (V) convertView : (V) LayoutInflater.from( parent.getContext() ).inflate( viewResourceId, null );
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {

		V targetView = getTargetView( convertView, parent );
		T item = items.get( position );

		adapt( item, targetView );

		return targetView;

	}

	public void setItems( List<T> items ) {
		this.items = items;
		notifyDataSetChanged();
	}

}
