package ar.edu.itba.utils;

import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import ar.edu.itba.model.GetAllOrders;
import ar.edu.itba.model.GetAllStates;
import ar.edu.itba.model.GetOrderById;
import ar.edu.itba.model.GetProductById;
import ar.edu.itba.model.GetProductsByCategoryId;
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
	
	public static Intent getAllOrdersRequest(Activity activity, APIResultReceiver receiver){
		Intent intent = buildIntent(activity, receiver);
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", "elgrupo2");
		parameters.put("authentication_token", "bf4c9b4dc2d9ed26118c538aefe36859");

		
		intent.putExtra(Utils.REQUEST_URL, buildUrl("Order", "GetAllOrders", parameters));
		intent.putExtra(Utils.METHOD_CLASS, GetAllOrders.class.getName());
	    return intent;
	}
	
	public static Intent getOrderByIdRequest(Activity activity, APIResultReceiver receiver, Integer id){
		Intent intent = buildIntent(activity, receiver);
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", "elgrupo2");
		parameters.put("authentication_token", "bf4c9b4dc2d9ed26118c538aefe36859");
		parameters.put("id", id.toString());

		
		intent.putExtra(Utils.REQUEST_URL, buildUrl("Order", "GetOrderById", parameters));
		intent.putExtra(Utils.METHOD_CLASS, GetOrderById.class.getName());
	    return intent;
	}
	
	public static Intent getProductByIdRequest(Activity activity, APIResultReceiver receiver, Integer id){
		Intent intent = buildIntent(activity, receiver);
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("id", id.toString());
		
		intent.putExtra(Utils.REQUEST_URL, buildUrl("Catalog", "GetProductById", parameters));
		intent.putExtra(Utils.METHOD_CLASS, GetProductById.class.getName());
	    return intent;
	}
	
	public static Intent getProductsByCategoryIdRequest(Activity activity, APIResultReceiver receiver, String id, String gender, String age){
		Intent intent = buildIntent(activity, receiver);
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("id", id);
		String filters = "[";
		if(gender != "")
			filters = filters + "{\"id\":1,\"value\":\"" + gender + "\"},";
		if(age != "")
			filters = filters + "{\"id\":2,\"value\":\"" + age + "\"}";
		filters = filters + "]";
		parameters.put("filters", filters);
		
		intent.putExtra(Utils.REQUEST_URL, buildUrl("Catalog", "GetProductsByCategoryId", parameters));
		intent.putExtra(Utils.METHOD_CLASS, GetProductsByCategoryId.class.getName());
		return intent;
	}
	
	public static Intent getProductsBySubcategoryIdRequest(Activity activity, APIResultReceiver receiver, String id, String gender, String age){
		Intent intent = buildIntent(activity, receiver);
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("id", id);
		String filters = "[";
		if(gender != "")
			filters = filters + "{\"id\":1,\"value\":\"" + gender + "\"},";
		if(age != "")
			filters = filters + "{\"id\":2,\"value\":\"" + age + "\"}";
		filters = filters + "]";
		parameters.put("filters", filters);
		
		intent.putExtra(Utils.REQUEST_URL, buildUrl("Catalog", "GetProductsBySubcategoryId", parameters));
		intent.putExtra(Utils.METHOD_CLASS, GetProductsByCategoryId.class.getName());
		return intent;
	}
	
	
	public static String buildUrl(String controller, String method, HashMap<String, String> parameters){
        Uri.Builder b = new Uri.Builder();
        
        b.encodedPath("http://eiffel.itba.edu.ar/hci/service3");
        b.appendPath(controller + ".groovy");
        b.appendQueryParameter("method", method);
        
        if (parameters != null) {
        	for(Entry<String, String> entry : parameters.entrySet()) {
        		b.appendQueryParameter(entry.getKey(), entry.getValue());
        	}
        }
        String url = b.build().toString();
        System.out.println(url);
		return url;
	}

}
