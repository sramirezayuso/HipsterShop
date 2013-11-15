package ar.edu.itba.utils;

import android.app.Activity;
import android.content.Intent;
import ar.edu.itba.services.APIResultReceiver;
import ar.edu.itba.services.ApiService;

public class HipsterShopApi {
	public static Intent getAllStates(Activity activity, APIResultReceiver receiver){
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, activity, ApiService.class);
	    intent.putExtra(Utils.RECEIVER, receiver);
	    intent.putExtra(Utils.COMMAND, "query");
	    return intent;
	}
}
