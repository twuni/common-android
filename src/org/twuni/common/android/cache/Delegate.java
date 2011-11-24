package org.twuni.common.android.cache;

public interface Delegate<T> {

	public T invoke( Object... args );

}
