package ar.edu.itba;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import ar.edu.itba.model.GetAllCategories;
import ar.edu.itba.model.GetAllProducts;
import ar.edu.itba.model.Product;
import ar.edu.itba.model.SpecificOrder;
import ar.edu.itba.notifications.AlarmService;
import ar.edu.itba.services.APIResultReceiver;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.ProductAdapter;
import ar.edu.itba.utils.Utils;

public class HelloAndroidActivity extends MasterActivity {
	
	private APIResultReceiver apiResultReceiver2;
	private List<Product> offers;

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setNotificationReceiver();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				System.out.println(categories.get(position).getName());
				Intent intent = new Intent(HelloAndroidActivity.this, SubcategoriesActivity.class);
		    	SharedPreferences prefs = HelloAndroidActivity.this.getSharedPreferences("hipster_preferences", Context.MODE_PRIVATE);
		    	SharedPreferences.Editor editor = prefs.edit();
		        editor.putInt("selectedCategory", categories.get(position).getId());
		        editor.commit();
				startActivity(intent);
			}
	    });
    	SharedPreferences prefs = this.getSharedPreferences("hipster_preferences", Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
        editor.putString("filterAge", "");
        editor.putString("filterGender", "");
        editor.commit();
		
        final Intent intent = HipsterShopApi.getAllCategoriesRequest(this, apiResultReceiver);
	   	startService(intent);
	   	
	   	apiResultReceiver2 = new APIResultReceiver(new Handler());
        apiResultReceiver2.setReceiver(this);
        final Intent intent2 = HipsterShopApi.getOffersRequest(this, apiResultReceiver2);
	   	startService(intent2);
	}
	
	public void setNotificationReceiver(){
		Intent intent = new Intent(this, AlarmService.class);
		
		AlarmService alarm = new AlarmService();
		alarm.onStart(this, intent, 0);
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		System.out.println(resultCode);
		switch (resultCode) {
		case ApiService.STATUS_RUNNING:
			Utils.showProgress(true, this, findViewById(R.id.order_progress_status), findViewById(R.id.order_form));
			break;
		case ApiService.STATUS_FINISHED:
			if(resultData.getString(Utils.METHOD_CLASS).equals("ar.edu.itba.model.GetAllCategories")) {
				GetAllCategories response = (GetAllCategories) resultData.get(Utils.RESPONSE);
				categories = response.getCategories();	
	    	
				String[] values = response.getNames();
				mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, values));
			}
			
			if(resultData.getString(Utils.METHOD_CLASS).equals("ar.edu.itba.model.GetAllProducts")) {
				GetAllProducts response = (GetAllProducts) resultData.get(Utils.RESPONSE);
				GridView gridView = (GridView) findViewById(R.id.main_offers);
				offers = response.getProducts();
            	ProductAdapter imageAdapter = new ProductAdapter(this, offers);
            	gridView.setAdapter(imageAdapter);
        		gridView.setOnItemClickListener(mMessageClickedHandler); 
        		Utils.showProgress(false, this, findViewById(R.id.order_progress_status), findViewById(R.id.order_form));
			}
		    
			// hide progress
			break;
		case ApiService.STATUS_ERROR:
			System.out.println("error");

			// handle the error;
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(ar.edu.itba.R.menu.main, menu);
		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
		final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() { 
		    @Override 
		    public boolean onQueryTextChange(String newText) { 
		        // Do something 
		        return true; 
		    } 

		    @Override 
		    public boolean onQueryTextSubmit(String query) {
				Intent intent = new Intent(HelloAndroidActivity.this, ProductsActivity.class);
				intent.putExtra("searchTerm", query);
				startActivity(intent);
				return true; 
		    } 
		}; 

		searchView.setOnQueryTextListener(queryTextListener);
		return true;
	}

	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			Integer productId = ((Product) parent.getAdapter().getItem(
					position)).getId();

			Intent intent = new Intent(parent.getContext(),
					ProductActivity.class);
			intent.putExtra(Utils.ID, productId);
			startActivity(intent);
        }
    };
}
