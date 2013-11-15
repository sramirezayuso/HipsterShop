package ar.edu.itba;

import android.app.Activity;
import android.os.Bundle;
import ar.edu.itba.services.APIResultReceiver;

public class MasterActivity extends Activity implements APIResultReceiver.Receiver {
	public APIResultReceiver apiResultReceiver;
	
    public void onPause() {
    	apiResultReceiver.setReceiver(null); // Clear receiver so there are no leaks.
    }

	public void onReceiveResult(int resultCode, Bundle resultData) {
		// Might do nothing when we get a result.
	}
}
