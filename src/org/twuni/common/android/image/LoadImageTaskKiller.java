package org.twuni.common.android.image;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import org.twuni.common.android.image.task.LoadImageTask;

public class LoadImageTaskKiller extends TimerTask {

	private final Map<String, LoadImageTask> tasks;

	public LoadImageTaskKiller( Map<String, LoadImageTask> tasks ) {
		this.tasks = tasks;
	}

	@Override
	public void run() {
		Set<String> finished = new HashSet<String>();
		synchronized( tasks ) {
			for( String url : tasks.keySet() ) {
				LoadImageTask task = tasks.get( url );
				if( task.isCancelled() || task.isComplete() ) {
					finished.add( url );
				}
			}
			for( String url : finished ) {
				tasks.remove( url );
			}
		}
	}

}
