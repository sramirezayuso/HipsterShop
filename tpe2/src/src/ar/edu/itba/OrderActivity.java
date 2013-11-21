package ar.edu.itba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import ar.edu.itba.model.GetAllOrders;
import ar.edu.itba.model.GetOrderById;
import ar.edu.itba.model.GetProductById;
import ar.edu.itba.model.Order;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.Utils;

public class OrderActivity extends MasterActivity {
	private Order order;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		Intent receivedIntent = getIntent();
		Integer orderId = receivedIntent.getIntExtra(Utils.ID, -1);
		final Intent intent = HipsterShopApi.getOrderByIdRequest(this,
				apiResultReceiver, orderId);
		startService(intent);
	}
	
	public void showData(){
		// Implement displaying data
		System.out.println(order.getName());
		System.out.println(order.getName());

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
        	GetOrderById response = (GetOrderById) resultData
				.get(Utils.RESPONSE);
	
        	this.order = response.getOrder();
        	showData();
        	
            break;
        case ApiService.STATUS_ERROR:
        	System.out.println("error");
        	System.out.println(resultData.get(Intent.EXTRA_TEXT));
            // handle the error;
            break;
        }
    }

}
