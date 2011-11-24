package org.twuni.common.android.image.task;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class LoadImageFromNetworkTask extends LoadImageTask {

	protected final BitmapFactory.Options options;

	public LoadImageFromNetworkTask( String url, BitmapFactory.Options options ) {
		super( url );
		this.options = options;
	}

	@Override
	protected SoftReference<Bitmap> performTask( Void nothing ) {
		try {
			URL url = new URL( this.url );
			InputStream in = url.openStream();
			Bitmap bitmap = BitmapFactory.decodeStream( in, null, options );
			return new SoftReference<Bitmap>( bitmap );
		} catch( Exception exception ) {
			onException( exception );
		}
		return null;
	}

}
