package org.twuni.common.android.record;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public abstract class Record<T> {

	private final SharedPreferences preferences;

	public Record( SharedPreferences preferences ) {
		this.preferences = preferences;
	}

	public abstract void clear();

	protected <K> void clear( K... keys ) {
		Editor editor = preferences.edit();
		for( Object key : keys ) {
			editor.remove( key.toString() );
		}
		editor.commit();
	}

	protected boolean getBoolean( Object key, boolean defaultValue ) {
		return preferences.getBoolean( key.toString(), defaultValue );
	}

	protected int getInt( Object key ) {
		return preferences.getInt( key.toString(), 0 );
	}

	protected long getLong( Object key ) {
		return preferences.getLong( key.toString(), 0L );
	}

	protected String getString( Object key ) {
		return preferences.getString( key.toString(), null );
	}

	public abstract T load();

	public void save( T object ) {

		if( object == null ) {
			return;
		}

		Editor editor = preferences.edit();
		save( object, new RecordEditor( editor ) );
		editor.commit();

	}

	protected abstract void save( T object, RecordEditor editor );

}
