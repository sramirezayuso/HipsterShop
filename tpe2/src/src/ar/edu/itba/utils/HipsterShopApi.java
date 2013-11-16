package ar.edu.itba.utils;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import ar.edu.itba.model.GetAllStates;
import ar.edu.itba.services.APIResultReceiver;
import ar.edu.itba.services.ApiService;

public class HipsterShopApi {
	private static Intent buildIntent(Activity activity, APIResultReceiver receiver){
		Intent intent = new Intent(Intent.ACTION_SYNC, null, activity, ApiService.class);
	    intent.putExtra(Utils.RECEIVER, receiver);
	    intent.putExtra(Utils.COMMAND, "query");
	    return intent;
	}
	
	public static Intent getAllStatesRequest(Activity activity, APIResultReceiver receiver){
		Intent intent = buildIntent(activity, receiver);
		intent.putExtra(Utils.REQUEST_URL, buildUrl("Common", "GetAllStates", null));
		intent.putExtra(Utils.METHOD_CLASS, GetAllStates.class.getName());
	    return intent;
	}
	

	
	
	public static String buildUrl(String controller, String method, HashMap<String, String> parameters){
        Uri.Builder b = new Uri.Builder();
        
        b.encodedPath("http://eiffel.itba.edu.ar/hci/service3");
        b.appendPath(controller + ".groovy");
        b.appendQueryParameter("method", method);
 
		return b.build().toString();
	}

}
