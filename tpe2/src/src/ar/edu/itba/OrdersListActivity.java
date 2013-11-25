package ar.edu.itba;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ar.edu.itba.model.GetAllCategories;
import ar.edu.itba.model.GetAllOrders;
import ar.edu.itba.model.Order;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.OrdersAdapter;
import ar.edu.itba.utils.Utils;

import com.viewpagerindicator.TitlePageIndicator;

public class OrdersListActivity extends MasterActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orders_list);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				System.out.println(categories.get(position).getName());
				Intent intent = new Intent(OrdersListActivity.this, SubcategoriesActivity.class);
		    	SharedPreferences prefs = OrdersListActivity.this.getSharedPreferences("hipster_preferences", Context.MODE_PRIVATE);
		    	SharedPreferences.Editor editor = prefs.edit();
		        editor.putInt("selectedCategory", categories.get(position).getId());
		        editor.commit();
				startActivity(intent);
			}
	    });
		
        final Intent catIntent = HipsterShopApi.getAllCategoriesRequest(this, apiResultReceiver);
	   	startService(catIntent);

        final Intent intent = HipsterShopApi.getAllOrdersRequest(this, apiResultReceiver);
        startService(intent);
        
		SharedPreferences settings = getSharedPreferences(Utils.PREFERENCES, 0);
        if(settings.getString("Token", "estonoesuntoken").equals("estonoesuntoken")) {
    		new AlertDialog.Builder(this)
			.setMessage(getResources().getString(R.string.orders_alert))
			.setPositiveButton(getResources().getString(R.string.accept_message),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,	int id) {
						dialog.cancel();
						finish();
					}
			}).show();
        }
	}
	

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
    	System.out.println(resultCode);
        switch (resultCode) {
        case ApiService.STATUS_RUNNING:
        	Utils.showProgress(true, this, findViewById(R.id.orders_progress_status), findViewById(R.id.orders_list_container));
            break;
        case ApiService.STATUS_FINISHED:
        	if(resultData.getString(Utils.METHOD_CLASS).equals("ar.edu.itba.model.GetAllCategories")) {
				GetAllCategories response = (GetAllCategories) resultData.get(Utils.RESPONSE);
				categories = response.getCategories();	
	    	
				String[] values = response.getNames();
				mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, values));
			} else {
	        	GetAllOrders response = (GetAllOrders) resultData.get(Utils.RESPONSE); 
	        	System.out.println(response.getOrders());
	     
	        	ArrayAdapter<List<Order>> adapter = organizeOrders(response.getOrders());
	    	    PagerAdapter pageAdapter = new OrdersAdapter(this, adapter);
	    	    ViewPager pager = (ViewPager) findViewById(R.id.ordersPager);  
	            pager.setAdapter(pageAdapter);
	            
	            TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.ordersTitles);
	            titleIndicator.setViewPager(pager);
	            
	            Utils.showProgress(false, this, findViewById(R.id.orders_progress_status), findViewById(R.id.orders_list_container));
			}
            break;
        case ApiService.STATUS_ERROR:
        	System.out.println("error");
        	System.out.println(resultData.get(Intent.EXTRA_TEXT));
            // handle the error;
            break;
        }
    }
    
    private ArrayAdapter<List<Order>> organizeOrders(List<Order> orders) {
    	ArrayAdapter<List<Order>> adapter = new ArrayAdapter<List<Order>>(this, android.R.layout.simple_list_item_1);
    	HashMap<String, List<Order>> map = new HashMap<String, List<Order>>();
    	
    	for(Order order : orders) {
    		if(map.containsKey(order.getStatus())) {
    			map.get(order.getStatus()).add(order);
    		} else {
    			List<Order> aux = new ArrayList<Order>();
    			aux.add(order);
    			map.put(order.getStatus(), aux);
    		}
    	}
    	
    	for(int i = 1; i <= 4; i++) {
    		if(map.containsKey(String.valueOf(i))) {
    			adapter.add(map.get(String.valueOf(i)));
    		} else {
    			adapter.add(new ArrayList<Order>());
    		}
    	}
    	
    	return adapter;
    }


}
