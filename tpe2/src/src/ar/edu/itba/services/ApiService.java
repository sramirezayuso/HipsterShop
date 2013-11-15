package ar.edu.itba.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;
import ar.edu.itba.model.GetAllStates;
import ar.edu.itba.model.MethodObject;
import ar.edu.itba.utils.Utils;

public class ApiService extends IntentService{

	  public ApiService() {
		super(ApiService.class.getSimpleName());
		// TODO Auto-generated constructor stub
	}

	public static final int STATUS_RUNNING = 1;
	public static final int STATUS_FINISHED = 2;
	public static final int STATUS_ERROR = 3;

	protected void onHandleIntent(Intent intent) {
		final ResultReceiver receiver = intent.getParcelableExtra(Utils.RECEIVER);
	    Bundle b = new Bundle();

	    String command = intent.getStringExtra(Utils.COMMAND);
	    String requestUrl = intent.getStringExtra(Utils.REQUEST_URL);
	    Class methodObjectClass = null;
	    try {
	    	System.out.println(intent.getStringExtra(Utils.METHOD_CLASS));
	    	methodObjectClass = Class.forName(intent.getStringExtra(Utils.METHOD_CLASS));
	    	System.out.println(methodObjectClass);
		} catch (ClassNotFoundException e) {
       	 	b.putString(Intent.EXTRA_TEXT, e.toString());
       	 	receiver.send(STATUS_ERROR, b);
		}
	    if(methodObjectClass != null && command.equals("query")) {
	    	receiver.send(STATUS_RUNNING, Bundle.EMPTY);
	        try {
	        	Gson gson = new GsonBuilder().create();
	            BufferedReader reader = getJSONData(requestUrl);
	            MethodObject response = gson.fromJson(reader, methodObjectClass);	   
	            System.out.println("Lo que devuelve de la API desde el service " + response);
	                
		        b.putParcelable(Utils.RESPONSE, response);
	            receiver.send(STATUS_FINISHED, b);
	         } catch(Exception e) {
	        	 b.putString(Intent.EXTRA_TEXT, e.toString());
	             receiver.send(STATUS_ERROR, b);
	         }    
	    }
	    this.stopSelf();
	 }

	   public BufferedReader getJSONData(String url){
	        // create DefaultHttpClient
	        HttpClient httpClient = new DefaultHttpClient();
	        URI uri; // for URL
	        InputStream data = null; // for URL's JSON

	        try {
	            uri = new URI(url);
	            HttpGet method = new HttpGet(uri); // Get URI
	            HttpResponse response = httpClient.execute(method); // Get response from method.  
	            data = response.getEntity().getContent(); // Data = Content from the response URL. 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return new BufferedReader(new InputStreamReader(data));
	    }
}


