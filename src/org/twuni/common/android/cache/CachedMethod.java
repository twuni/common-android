package org.twuni.common.android.cache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CachedMethod<T> {

	private class Entry {

		long added;
		T value;

	}

	private final Map<List<Object>, Entry> cache = new HashMap<List<Object>, Entry>();
	private final Delegate<T> delegate;
	private final long ttl;
	private final Set<List<Object>> waiting = new HashSet<List<Object>>();

	/**
	 * @param ttl
	 *            the time (in milliseconds) until the method's return value expires.
	 * @param delegate
	 *            the method to execute if nothing is in the cache.
	 */
	public CachedMethod( String name, long ttl, Delegate<T> delegate ) {
		this.ttl = ttl;
		this.delegate = delegate;
	}

	public T invoke( Object... args ) {

		List<Object> key = Arrays.asList( args );

		waitForOthersToFinish( key );

		Entry entry = cache.get( key );

		if( entry != null && System.currentTimeMillis() - entry.added < ttl ) {
			log( "CACHED [%s]: %s", key, entry.value );
			return entry.value;
		}

		log( "UNCACHED [%s]: (delegating)", key, delegate );

		entry = new Entry();

		entry.added = System.currentTimeMillis();
		waiting.add( key );
		try {
			entry.value = delegate.invoke( args );
			cache.put( key, entry );
		} finally {
			waiting.remove( key );
		}

		return entry.value;

	}

	private void log( String message, Object... args ) {
	}

	private void waitForOthersToFinish( List<Object> key ) {
		while( waiting.contains( key ) ) {
			try {
				Thread.currentThread().join( 500 );
			} catch( InterruptedException exception ) {
			}
		}
	}

}
