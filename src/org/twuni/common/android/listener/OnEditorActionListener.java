package org.twuni.common.android.listener;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public abstract class OnEditorActionListener implements android.widget.TextView.OnEditorActionListener {

	private final int actionId;

	protected OnEditorActionListener( int actionId ) {
		this.actionId = actionId;
	}

	public abstract void handleAction();

	@Override
	public final boolean onEditorAction( TextView view, int actionId, KeyEvent event ) {
		if( this.actionId == actionId ) {
			InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService( Context.INPUT_METHOD_SERVICE );
			imm.hideSoftInputFromWindow( view.getWindowToken(), 0 );
			handleAction();
			return true;
		}
		return false;
	}

}
