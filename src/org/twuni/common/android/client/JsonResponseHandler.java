package org.twuni.common.android.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.twuni.common.android.exception.AuthenticationException;
import org.twuni.common.android.exception.HttpException;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

public final class JsonResponseHandler<T, E> implements ResponseHandler<T> {

	private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	private final Type typeOfT;
	private final Class<? extends E> typeOfE;

	public JsonResponseHandler( Type typeOfT, Class<? extends E> typeOfE ) {
		this.typeOfT = typeOfT;
		this.typeOfE = typeOfE;
	}

	@Override
	public T handleResponse( HttpResponse response ) throws ClientProtocolException, IOException {

		String output = readAsString( response.getEntity().getContent() );
		StatusLine status = response.getStatusLine();

		Log.d( getClass().getSimpleName(), String.format( "<- Status: %s", status ) );

		checkForExceptions( status, output );

		return gson.fromJson( output, typeOfT );

	}

	private void checkForExceptions( StatusLine status, String output ) {

		int statusCode = status.getStatusCode();
		String errorMessage = status.getReasonPhrase();

		if( 400 <= status.getStatusCode() ) {

			try {
				errorMessage = gson.fromJson( output, typeOfE ).toString();
			} catch( JsonParseException outputWasNotJson ) {
			}

			switch( status.getStatusCode() ) {
				case 401:
				case 403:
					throw new AuthenticationException( status.getStatusCode(), errorMessage );
			}

			throw new HttpException( statusCode, errorMessage );

		}

	}

	private String readAsString( InputStream input ) throws IOException {

		InputStreamReader reader = new InputStreamReader( input );
		StringBuilder output = new StringBuilder();
		char [] buffer = new char [8192];

		for( int size = reader.read( buffer, 0, buffer.length ); size > 0; size = reader.read( buffer, 0, size ) ) {
			output.append( buffer, 0, size );
		}

		return output.toString();

	}

}
