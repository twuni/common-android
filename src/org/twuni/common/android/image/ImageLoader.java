package org.twuni.common.android.image;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import org.twuni.common.android.image.task.LoadImageFromMemoryTask;
import org.twuni.common.android.image.task.LoadImageTask;

import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class ImageLoader {

	private final boolean enabled;
	private final Timer timer = new Timer( false );
	private final BitmapFactory.Options defaultBitmapOptions = new BitmapFactory.Options();
	private final File cacheDir;
	private final Map<String, LoadImageTask> tasks = new HashMap<String, LoadImageTask>();

	public ImageLoader( File cacheDir ) {
		this( cacheDir, true );
	}

	public ImageLoader( File cacheDir, boolean enabled ) {
		this.cacheDir = cacheDir;
		defaultBitmapOptions.inSampleSize = 1;
		defaultBitmapOptions.inPurgeable = true;
		this.enabled = enabled;
		if( enabled ) {
			timer.schedule( new LoadImageTaskKiller( tasks ), 1000, 2000 );
		}
	}

	public void cancel( String url ) {
		LoadImageTask task = tasks.get( url );
		if( task != null ) {
			task.cancel( true );
		}
	}

	public void clear() {
		for( LoadImageTask task : tasks.values() ) {
			task.cancel( true );
		}
	}

	public void load( ImageView view, String url ) {

		if( !enabled || url == null ) {
			return;
		}

		if( view.getTag() != null && !url.equals( view.getTag() ) ) {
			LoadImageTask task = tasks.get( view.getTag() );
			if( task != null ) {
				task.dequeue( view );
			}
		}

		LoadImageTask task = tasks.get( url );

		if( task != null && !( task.isCancelled() || task.isComplete() ) ) {
			task.queue( view );
			return;
		}

		task = new LoadImageFromMemoryTask( url, defaultBitmapOptions, cacheDir );
		synchronized( tasks ) {
			tasks.put( url, task );
		}
		task.queue( view );

		timer.schedule( new LoadImageTaskRunner( task ), 500 );

	}

}
