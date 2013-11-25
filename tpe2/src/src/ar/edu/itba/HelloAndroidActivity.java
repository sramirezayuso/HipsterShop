package ar.edu.itba;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ar.edu.itba.model.GetAllCategories;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.Utils;

public class HelloAndroidActivity extends MasterActivity {

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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				System.out.println(categories.get(position).getName());
				Intent intent = new Intent(HelloAndroidActivity.this, SubcategoriesActivity.class);
				intent.putExtra(Utils.ID, categories.get(position).getId());
				startActivity(intent);
			}
	     });
		
        final Intent intent = HipsterShopApi.getAllCategoriesRequest(this, apiResultReceiver);
	   	startService(intent);
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

	/*
	 * UTILEjemplo de uso de shared preferences SharedPreferences sp =
	 * getPreferences(MODE_PRIVATE); SharedPreferences.Editor editor =
	 * sp.edit();
	 * 
	 * editor.putString("PASSWORD_VALUE", "123456");
	 * editor.commit();//IMPORTANTE!
	 * 
	 * 
	 * Asi se levantan del otro lado String s=
	 * sp.getString("PASSWORD_VALUE","-1");
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(ar.edu.itba.R.menu.main, menu);
		return true;
	}

	public void onProductClick(View view) {
		Intent intent = new Intent(this, ProductsActivity.class);
		//intent.putExtra(ProductActivity.EXTRA_MESSAGE, "prueba");
		startActivity(intent);

	}

	public void onOrdersClick(View view) {
		Intent intent = new Intent(this, OrdersListActivity.class);
		// intent.putExtra(ProductActivity.EXTRA_MESSAGE, "prueba");
		startActivity(intent);
	}
	
	public void onSubcategoriesClick(View view) {
		Intent intent = new Intent(this, SubcategoriesActivity.class);
		// intent.putExtra(ProductActivity.EXTRA_MESSAGE, "prueba");
		startActivity(intent);
	}

}
