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
	    String command = intent.getStringExtra("command");
	    Bundle b = new Bundle();
	    if(command.equals("query")) {
	    	receiver.send(STATUS_RUNNING, Bundle.EMPTY);
	        try {
	        	Gson gson = new GsonBuilder().create();
	            BufferedReader reader = getJSONData( "http://eiffel.itba.edu.ar/hci/service3/Common.groovy?method=GetAllStates" );
	            GetAllStates states = gson.fromJson(reader, GetAllStates.class);	   
	            System.out.println("Lo que devuelve de la API desde el service " + states.getStates());
	                
		        b.putParcelableArrayList("results", (ArrayList<? extends Parcelable>) states.getStates());
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


