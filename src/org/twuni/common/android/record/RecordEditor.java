package org.twuni.common.android.record;

import android.content.SharedPreferences.Editor;

public class RecordEditor {

	private final Editor editor;

	public RecordEditor( Editor editor ) {
		this.editor = editor;
	}

	public void put( Object key, boolean value ) {
		editor.putBoolean( key.toString(), value );
	}

	public void put( Object key, int value ) {
		editor.putInt( key.toString(), value );
	}

	public void put( Object key, long value ) {
		editor.putLong( key.toString(), value );
	}

	public void put( Object key, Object value ) {
		editor.putString( key.toString(), value != null ? value.toString() : null );
	}

}
