package org.twuni.common.android.image.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.ref.SoftReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class LoadImageFromFileTask extends LoadImageFromNetworkTask {

	protected final File cacheDir;

	public LoadImageFromFileTask( String url, BitmapFactory.Options options, File cacheDir ) {
		super( url, options );
		this.cacheDir = cacheDir;
	}

	@Override
	protected SoftReference<Bitmap> performTask( Void nothing ) {

		File cached = new File( cacheDir, getCacheFileName() );

		try {
			Bitmap bitmap = loadBitmap( cached );
			return new SoftReference<Bitmap>( bitmap );
		} catch( Exception exception ) {
			SoftReference<Bitmap> bitmap = super.performTask( nothing );
			save( bitmap, cached );
			return bitmap;
		}
	}

	@Override
	public boolean isComplete() {
		return new File( cacheDir, getCacheFileName() ).exists();
	}

	private String getCacheFileName() {
		return Integer.toHexString( url.hashCode() );
	}

	private Bitmap loadBitmap( File file ) {
		if( file.exists() ) {
			return BitmapFactory.decodeFile( file.getAbsolutePath(), options );
		}
		throw new RuntimeException( new FileNotFoundException( file.getAbsolutePath() ) );
	}

	private void save( SoftReference<Bitmap> reference, File file ) {
		if( reference != null ) {
			Bitmap bitmap = reference.get();
			if( bitmap != null ) {
				try {
					bitmap.compress( Bitmap.CompressFormat.PNG, 90, new FileOutputStream( file ) );
				} catch( Exception exception ) {
					handleException( exception );
				}
			}
		}
	}

}
