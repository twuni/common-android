package org.twuni.common.android.exception;

public class AuthenticationException extends HttpException {

	public AuthenticationException( int status, String message ) {
		super( status, message );
	}

}
