package ar.edu.itba.notifications;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import ar.edu.itba.model.GetAllOrders;
import ar.edu.itba.model.Order;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OrderStatusDetector extends IntentService {
	public OrderStatusDetector() {
		super(ApiService.class.getSimpleName());
	}

	Context context;
	private String username = null;
	private String token = null;

	private SharedPreferences settings = null;
	
	private SharedPreferences getSettings(){
		if (settings != null){
			return settings;
		}
		return settings = context.getSharedPreferences(Utils.PREFERENCES, 0);
	}
	
	private SharedPreferences getStoredOrders(){
		if (settings != null){
			return settings;
		}
		return settings = context.getSharedPreferences(Utils.ORDERS, 0);
	}
	
	private String getUsername(){
		if (username != null){
			return username;
		}
		return username = getSettings().getString("Username", null);

	}
	
	private String getToken(){
		if (token != null){
			return token;
		}
		token = getSettings().getString("Password", null);
		
		if (token != null && token.equals("estonoesuntoken")) {
			return token = null;
		}
		
		return token;
	}
	
	private void storeOrder(Order order){
		SharedPreferences.Editor editor = getStoredOrders().edit();
		editor.putBoolean(order.getId().toString(), true);
		String orderStatus = order.getId().toString() + ":status";
		editor.putString(orderStatus, order.getStatusString());

		if (order.getStatusString().equals("moving")){
			String orderLatitude = order.getId().toString() + ":lat";
			editor.putInt(orderLatitude, order.getLatitude());

			String orderLongitude = order.getId().toString() + ":lon";
			editor.putInt(orderLongitude, order.getLatitude());
		}
		editor.commit();
	}
	
	private boolean hasChanged(Order order){
		return false;		
	}
	
	private void processOrder(Order order){
		if (getStoredOrders().getBoolean(order.getId().toString(), false)){
			// check if there is a change
			if (hasChanged(order)){
				System.out.println("cambio");
			}
		} else {
			storeOrder(order);
		}
	}
	

	@Override
	protected void onHandleIntent(Intent intent) {
		//if (getToken() != null){

			System.out.println("Checking...");
			List<Order> orders = getOrders();
			for(Order order : orders) {
				processOrder(order);
        //		b.appendQueryParameter(entry.getKey(), entry.getValue());
        	}
			//}

	}
    
	public List<Order> getOrders(){
		HashMap<String, String> parameters = new HashMap<String, String>();
		//parameters.put("username", getUsername());
		//parameters.put("authentication_token", getToken());
		
		parameters.put("username", "elgrupo2");
		parameters.put("authentication_token", "bf4c9b4dc2d9ed26118c538aefe36859");
		String url = HipsterShopApi.buildUrl("Order", "GetAllOrders", parameters);
		
		Gson gson = new GsonBuilder().create();
		BufferedReader reader = ApiService.getJSONData(url);
		GetAllOrders response = gson.fromJson(reader, GetAllOrders.class);	   
    	System.out.println("Lo que devuelve de la API desde el service " + response.getOrders());
    	return response.getOrders();
 }
}
