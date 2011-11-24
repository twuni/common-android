package org.twuni.common.android.image;

import java.util.TimerTask;

import org.twuni.common.android.image.task.LoadImageTask;

public class LoadImageTaskRunner extends TimerTask {

	private final LoadImageTask task;

	public LoadImageTaskRunner( LoadImageTask task ) {
		this.task = task;
	}

	@Override
	public void run() {
		if( !task.isCancelled() ) {
			try {
				task.execute();
			} catch( Exception exception ) {
			}
		}
	}

}
