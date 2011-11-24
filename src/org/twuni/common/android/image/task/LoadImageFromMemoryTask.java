package org.twuni.common.android.image.task;

import java.io.File;
import java.lang.ref.SoftReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class LoadImageFromMemoryTask extends LoadImageFromFileTask {

	private SoftReference<Bitmap> cache;

	public LoadImageFromMemoryTask( String url, BitmapFactory.Options options, File cacheDir ) {
		super( url, options, cacheDir );
	}

	@Override
	public boolean isComplete() {
		return cache != null && cache.get() != null;
	}

	@Override
	public void queue( ImageView target ) {
		if( isComplete() ) {
			target.setImageBitmap( cache.get() );
		} else {
			super.queue( target );
		}
	}

	@Override
	protected SoftReference<Bitmap> performTask( Void nothing ) {
		if( !isComplete() ) {
			cache = super.performTask( nothing );
		} else {
		}
		return cache;
	}

}
