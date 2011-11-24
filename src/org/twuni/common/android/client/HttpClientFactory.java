package org.twuni.common.android.client;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class HttpClientFactory {

	private final ClientConnectionManager connectionManager;

	private final CookieStore COOKIE_STORE = new EmptyCookieStore();
	private final HttpParams params;

	public HttpClientFactory() {
		this.params = new BasicHttpParams();
		this.connectionManager = new ThreadSafeClientConnManager( params, createSchemeRegistry() );
	}

	public HttpClient createInstance() {
		DefaultHttpClient client = new DefaultHttpClient( connectionManager, params );
		client.setCookieStore( COOKIE_STORE );
		return client;
	}

	private SchemeRegistry createSchemeRegistry() {

		SchemeRegistry registry = new SchemeRegistry();

		registry.register( new Scheme( "http", PlainSocketFactory.getSocketFactory(), 80 ) );
		registry.register( new Scheme( "https", SSLSocketFactory.getSocketFactory(), 443 ) );

		return registry;

	}

}
