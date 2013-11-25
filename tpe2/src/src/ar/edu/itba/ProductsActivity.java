package ar.edu.itba;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				System.out.println(categories.get(position).getName());
				Intent intent = new Intent(ProductsActivity.this, SubcategoriesActivity.class);
		    	SharedPreferences prefs = ProductsActivity.this.getSharedPreferences("hipster_preferences", Context.MODE_PRIVATE);
		    	SharedPreferences.Editor editor = prefs.edit();
		        editor.putInt("selectedCategory", categories.get(position).getId());
		        editor.commit();
				startActivity(intent);
			}
	    });
		
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
				categories = response.getCategories();	
	    	
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
