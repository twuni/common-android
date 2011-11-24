package org.twuni.common.android.task;

import android.os.Handler;
import android.os.Looper;

public abstract class AsyncTask<Input, Output> extends android.os.AsyncTask<Input, String, Output> {

	@Override
	protected final Output doInBackground( Input... inputs ) {
		try {
			return performTask( inputs != null && inputs.length > 0 ? inputs[0] : null );
		} catch( Exception exception ) {
			handleException( exception );
		}
		return null;
	}

	protected final void handleException( final Exception exception ) {

		new Handler( Looper.getMainLooper() ).post( new Runnable() {

			@Override
			public void run() {
				onException( exception );
			}

		} );

	}

	protected void onException( Exception exception ) {
	}

	@Override
	protected final void onPostExecute( Output output ) {
		if( output == null ) {
			return;
		}
		onTaskPerformed( output );
	}

	protected abstract Output performTask( Input input );

	protected void onTaskPerformed( Output output ) {
	}

}
