package org.twuni.common.android.activity;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;

public class HtmlAssetViewerActivity extends Activity {

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		requestWindowFeature( Window.FEATURE_NO_TITLE );
		super.onCreate( savedInstanceState );

		try {

			String source = getString( getIntent().getStringExtra( Intent.EXTRA_TEXT ) );

			TextView html = new TextView( this );
			html.setText( Html.fromHtml( source ) );

			ScrollView scroller = new ScrollView( this );
			scroller.addView( html, 0 );
			setContentView( scroller );

		} catch( IOException exception ) {
			setResult( RESULT_CANCELED );
			finish();
		}

	}

	private String getString( String assetName ) throws IOException {

		InputStream in = getAssets().open( assetName, AssetManager.ACCESS_BUFFER );
		byte [] buffer = new byte [4096];
		StringBuilder string = new StringBuilder();

		for( int size = in.read( buffer, 0, buffer.length ); size > 0; size = in.read( buffer, 0, size ) ) {
			string.append( new String( buffer, 0, size ) );
		}

		return string.toString();

	}

}
