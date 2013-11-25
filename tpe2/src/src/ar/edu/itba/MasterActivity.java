package ar.edu.itba;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ar.edu.itba.model.Category;
import ar.edu.itba.model.GetAllCategories;
import ar.edu.itba.services.APIResultReceiver;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.Utils;

public class MasterActivity extends Activity implements APIResultReceiver.Receiver {
	public APIResultReceiver apiResultReceiver;
    public ListView mDrawerList;
    private List<Category> categories;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        apiResultReceiver = new APIResultReceiver(new Handler());
        apiResultReceiver.setReceiver(this);
        setContentView(R.layout.activity_main);
		//mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        
        // Set the adapter for the list view
		//String [] mierda = new String[] {"TestA", "Test2", "Test3"};
        //mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, mierda));

        
        //final Intent intent = HipsterShopApi.getAllCategoriesRequest(this, drawerResultReceiver);
	   	//startService(intent);
       

        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        setupActionBar();
    }

    public void onPause() {
    	apiResultReceiver.setReceiver(null); // Clear receiver so there are no leaks.
    	super.onPause();
    }
    

    @Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
//		System.out.println(resultCode);
//	    switch (resultCode) {
//	    case ApiService.STATUS_RUNNING:
//	        //show progress
//	    	System.out.println("progress");
//	
//	        break;
//	    case ApiService.STATUS_FINISHED:
//	    	GetAllCategories response = (GetAllCategories) resultData.get(Utils.RESPONSE); 
//	    	System.out.println(response.getCategories());
//	    	categories = response.getCategories();
//			
//	    	
//		    String[] values = response.getNames();
//		    System.out.println(values);
//		    //mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//	        //        R.layout.drawer_list_item, values));
//	
//	        break;
//	    case ApiService.STATUS_ERROR:
//	    	System.out.println("error");
//	    	System.out.println(resultData.get(Intent.EXTRA_TEXT));
//	        // handle the error;
//	        break;
//	    }
	}
	
	public void setupActionBar(){
		getActionBar();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
		Intent intent = null;
	    switch (item.getItemId()) {
	        case R.id.action_search:
	        	 //get focus
	            item.getActionView().requestFocus();
	            //get input method
	            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	            return true;
	        case R.id.action_orders:
	    		intent = new Intent(this, OrdersListActivity.class);
	    		startActivity(intent);
	    		return true;
	        case R.id.action_settings:
	    		intent = new Intent(this, SettingsActivity.class);
	    		startActivity(intent);
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
