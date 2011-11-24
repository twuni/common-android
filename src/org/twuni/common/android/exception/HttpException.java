package org.twuni.common.android.exception;

public class HttpException extends NetworkException {

	private final int status;

	public HttpException( int status, String message ) {
		super( message );
		this.status = status;
	}

	public int getStatusCode() {
		return status;
	}

}
