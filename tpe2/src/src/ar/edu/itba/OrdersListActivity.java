package ar.edu.itba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import ar.edu.itba.model.GetAllOrders;
import ar.edu.itba.model.Order;
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
        	GetAllOrders response = (GetAllOrders) resultData.get(Utils.RESPONSE); 
        	System.out.println(response.getOrders());
        	Order[] orders = response.getOrders().toArray(new Order[response.getOrders().size()]); // Just to have sth to show for now
    		    		 
    		ArrayAdapter<Order> adapter = new ArrayAdapter<Order>(this, 
    		        android.R.layout.simple_list_item_1, orders);
    		ListView listView = (ListView) findViewById(R.id.listView1);
    		listView.setAdapter(adapter);
    	    listView.setOnItemClickListener(mMessageClickedHandler); 

            break;
        case ApiService.STATUS_ERROR:
        	System.out.println("error");
        	System.out.println(resultData.get(Intent.EXTRA_TEXT));
            // handle the error;
            break;
        }
    }
    
 // Create a message handling object as an anonymous class.
    private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
        	Integer orderId = ((Order) parent.getAdapter().getItem(position)).getId();
        	
    		Intent intent = new Intent(parent.getContext(), OrderActivity.class);
    		intent.putExtra(Utils.ID, orderId);
    		startActivity(intent);
        }
    };


}
