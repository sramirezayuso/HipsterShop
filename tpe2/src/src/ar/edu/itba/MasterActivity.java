package ar.edu.itba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import ar.edu.itba.services.APIResultReceiver;
import ar.edu.itba.utils.HipsterShopApi;

public class MasterActivity extends Activity implements APIResultReceiver.Receiver {
	public APIResultReceiver apiResultReceiver;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiResultReceiver = new APIResultReceiver(new Handler());
        apiResultReceiver.setReceiver(this);
    }

    public void onPause() {
    	apiResultReceiver.setReceiver(null); // Clear receiver so there are no leaks.
    }

	public void onReceiveResult(int resultCode, Bundle resultData) {
		// Might do nothing when we get a result.
	}
}
