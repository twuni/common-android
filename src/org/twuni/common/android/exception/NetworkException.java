package org.twuni.common.android.exception;

public class NetworkException extends RuntimeException {

	public NetworkException( String message ) {
		super( message );
	}

	public NetworkException( Throwable throwable ) {
		super( throwable );
	}

}
