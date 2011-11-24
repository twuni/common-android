package org.twuni.common.android.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicNameValuePair;
import org.twuni.common.android.adapter.QuerystringAdapter;
import org.twuni.common.android.exception.NetworkException;

import android.util.Log;

public class WebClient {

	private final String baseUrl;

	private final HttpClient client;

	private final QuerystringAdapter querystringAdapter = new QuerystringAdapter();
	private final ResponseHandler<String> stringHandler = new ResponseHandler<String>() {

		@Override
		public String handleResponse( HttpResponse response ) throws ClientProtocolException, IOException {
			InputStream stream = response.getEntity().getContent();
			StringBuilder content = new StringBuilder();
			byte [] buffer = new byte [4096];
			for( int length = stream.read( buffer ); length > 0; length = stream.read( buffer, 0, length ) ) {
				content.append( new String( buffer, 0, length ) );
			}
			return content.toString();
		}

	};

	public WebClient( String baseUrl, HttpClient client ) {
		this.baseUrl = baseUrl;
		this.client = client;
	}

	public String get( String uri ) {
		return get( uri, stringHandler );
	}

	public String get( String uri, Map<String, String> parameters ) {
		return get( uri, parameters, stringHandler );
	}

	public <T> T get( String uri, Map<String, String> parameters, ResponseHandler<T> handler ) {
		try {
			Log.d( getClass().getSimpleName(), String.format( "-> GET %s - %s", uri, parameters ) );
			return handler.handleResponse( client.execute( new HttpGet( getUrl( uri, parameters ) ) ) );
		} catch( IOException exception ) {
			throw new NetworkException( exception );
		}
	}

	public <T> T get( String uri, ResponseHandler<T> handler ) {
		return get( uri, new HashMap<String, String>(), handler );
	}

	private String getUrl( String uri ) {
		return new StringBuilder( baseUrl ).append( uri ).toString();
	}

	private String getUrl( String uri, Map<String, String> parameters ) {
		return new StringBuilder( baseUrl ).append( uri ).append( querystringAdapter.adapt( parameters ) ).toString();
	}

	public String post( String uri ) {
		return post( uri, stringHandler );
	}

	public String post( String uri, Map<String, String> parameters ) {
		return post( uri, parameters, stringHandler );
	}

	public <T> T post( String uri, Map<String, String> parameters, ResponseHandler<T> handler ) {
		try {
			HttpPost post = new HttpPost( getUrl( uri ) );
			post.setEntity( new UrlEncodedFormEntity( toNameValuePairs( parameters ) ) );
			Log.d( getClass().getSimpleName(), String.format( "-> POST %s - %s", uri, parameters ) );
			return handler.handleResponse( client.execute( post ) );
		} catch( IOException exception ) {
			throw new NetworkException( exception );
		}
	}

	public <T> T post( String uri, ResponseHandler<T> handler ) {
		return post( uri, new HashMap<String, String>(), handler );
	}

	public String put( String uri ) {
		return put( uri, stringHandler );
	}

	public String put( String uri, Map<String, String> parameters ) {
		return put( uri, parameters, stringHandler );
	}

	public <T> T put( String uri, Map<String, String> parameters, ResponseHandler<T> handler ) {
		try {
			HttpPut put = new HttpPut( getUrl( uri ) );
			put.setEntity( new UrlEncodedFormEntity( toNameValuePairs( parameters ) ) );
			Log.d( getClass().getSimpleName(), String.format( "-> PUT %s - %s", uri, parameters ) );
			return handler.handleResponse( client.execute( put ) );
		} catch( IOException exception ) {
			throw new NetworkException( exception );
		}
	}

	public <T> T put( String uri, ResponseHandler<T> handler ) {
		return put( uri, new HashMap<String, String>(), handler );
	}

	private List<NameValuePair> toNameValuePairs( Map<String, String> parameters ) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for( String key : parameters.keySet() ) {
			String value = parameters.get( key );
			pairs.add( new BasicNameValuePair( key, value ) );
		}
		return pairs;
	}

}
