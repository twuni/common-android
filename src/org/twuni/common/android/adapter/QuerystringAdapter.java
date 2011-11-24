package org.twuni.common.android.adapter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.twuni.common.Adapter;

public class QuerystringAdapter implements Adapter<Map<String, String>, String> {

	private static final String AND = "&";
	private static final String EQUALS = "=";
	private static final String QUERY = "?";
	private static final String UTF8 = "UTF-8";

	@Override
	public String adapt( Map<String, String> parameters ) {

		StringBuilder query = new StringBuilder( QUERY );

		for( String key : parameters.keySet() ) {

			String value = parameters.get( key );

			try {
				query.append( URLEncoder.encode( key, UTF8 ) );
				query.append( EQUALS );
				query.append( URLEncoder.encode( value, UTF8 ) );
				query.append( AND );
			} catch( UnsupportedEncodingException exception ) {
				throw new RuntimeException( exception );
			}

		}

		query.setLength( query.length() - 1 );

		return query.toString();

	}

}
