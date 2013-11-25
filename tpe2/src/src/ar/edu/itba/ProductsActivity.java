package ar.edu.itba;


import java.util.List;

import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import ar.edu.itba.model.Category;
import ar.edu.itba.model.GetAllCategories;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.Utils;

public class ProductsActivity extends MasterActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
        final Intent intent = HipsterShopApi.getAllCategoriesRequest(this, apiResultReceiver);
	   	startService(intent);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	public void setupActionBar() {
		super.setupActionBar();
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.products, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		System.out.println(resultCode);
		switch (resultCode) {
		case ApiService.STATUS_RUNNING:
			// show progress
			System.out.println("progress");

			break;
		case ApiService.STATUS_FINISHED:
			if(resultData.getString(Utils.METHOD_CLASS).equals("ar.edu.itba.model.GetAllCategories")) {
				GetAllCategories response = (GetAllCategories) resultData.get(Utils.RESPONSE);
				List<Category> categories = response.getCategories();	
	    	
				String[] values = response.getNames();
				mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, values));
			}
		    
			// hide progress
			break;
		case ApiService.STATUS_ERROR:
			System.out.println("error");

			// handle the error;
			break;
		}
	}

}
