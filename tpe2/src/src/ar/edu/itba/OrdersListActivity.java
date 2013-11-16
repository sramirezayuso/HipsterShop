package ar.edu.itba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ar.edu.itba.model.GetAllOrders;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.Utils;

public class OrdersListActivity extends MasterActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orders_list);
		

        final Intent intent = HipsterShopApi.getAllOrdersRequest(this, apiResultReceiver);
        startService(intent);
		
		String[] myStringArray = new String[]{"hola", "chau"};
		ArrayAdapter adapter = new ArrayAdapter<String>(this, 
		        android.R.layout.simple_list_item_1, myStringArray);
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
	    // TODO Auto-generated method stub

	}
	

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
    	System.out.println(resultCode);
        switch (resultCode) {
        case ApiService.STATUS_RUNNING:
            //show progress
        	System.out.println("progress");

            break;
        case ApiService.STATUS_FINISHED:
        	System.out.println("Lo que vuelve de response a la Activity: "+ resultData.get("response"));
        	GetAllOrders orders = (GetAllOrders) resultData.get(Utils.RESPONSE);
        	System.out.println("los states son: "+ orders.getOrders());
        	
        	System.out.println("El primero es: "+ orders.getOrders().get(0).getId()+ " " + orders.getOrders().get(0).getName());

        	
        	//System.out.println("Lo que vuelve de response a la Activity: " + results);
            // hide progress
            break;
        case ApiService.STATUS_ERROR:
        	System.out.println("error");
        	System.out.println(resultData.get(Intent.EXTRA_TEXT));
            // handle the error;
            break;
        }
    }

}
