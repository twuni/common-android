package org.twuni.common.android.image.task;

import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Set;

import org.twuni.common.android.task.AsyncTask;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

public abstract class LoadImageTask extends AsyncTask<Void, SoftReference<Bitmap>> {

	protected final Set<SoftReference<ImageView>> targets = new HashSet<SoftReference<ImageView>>();
	protected final String url;
	protected boolean complete = false;

	public LoadImageTask( String url ) {
		if( url == null ) {
			throw new IllegalArgumentException();
		}
		this.url = url;
	}

	@Override
	protected void onCancelled() {
		targets.clear();
	}

	public boolean isComplete() {
		return complete;
	}

	public void queue( ImageView target ) {
		target.setTag( url );
		if( getReference( target ) == null ) {
			targets.add( new SoftReference<ImageView>( target ) );
		}
	}

	public void dequeue( ImageView target ) {
		remove( target );
		if( targets.isEmpty() ) {
			cancel( true );
		}
	}

	private void remove( ImageView target ) {
		if( target != null ) {
			targets.remove( getReference( target ) );
		}
	}

	private SoftReference<ImageView> getReference( ImageView target ) {
		for( SoftReference<ImageView> reference : targets ) {
			if( reference == null ) {
				continue;
			}
			ImageView view = reference.get();
			if( view == target ) {
				return reference;
			}
		}
		return null;
	}

	public boolean isQueued( ImageView target ) {
		return targets.contains( target );
	}

	@Override
	protected void onTaskPerformed( SoftReference<Bitmap> reference ) {
		synchronized( targets ) {
			Bitmap bitmap = reference.get();
			if( bitmap != null ) {
				for( ImageView view : getTargets() ) {
					if( view == null ) {
						continue;
					}
					if( url.equals( view.getTag() ) ) {
						view.setImageBitmap( bitmap );
					}
				}
			}
			targets.clear();
		}
		complete = true;
	}

	private Set<ImageView> getTargets() {
		Set<ImageView> filtered = new HashSet<ImageView>();
		for( SoftReference<ImageView> target : targets ) {
			if( target == null ) {
				continue;
			}
			ImageView view = target.get();
			if( view == null ) {
				continue;
			}
			filtered.add( view );
		}
		return filtered;
	}

	@Override
	protected void onException( Exception exception ) {
		Log.e( getClass().getSimpleName(), String.format( String.format( "[%s] %s", exception.getClass().getSimpleName(), exception.getMessage() ) ) );
	}

}
