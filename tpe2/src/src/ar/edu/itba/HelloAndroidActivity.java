package ar.edu.itba;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import ar.edu.itba.model.GetAllStates;
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

		final Intent intent = HipsterShopApi.getAllStatesRequest(this,
				apiResultReceiver);
		startService(intent);
		
		TextView txt = (TextView) findViewById(R.id.aux);
		txt.setText("rusemaster");
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
			System.out.println("Lo que vuelve de response a la Activity: "
					+ resultData.get("response"));
			GetAllStates states = (GetAllStates) resultData.get(Utils.RESPONSE);
			System.out.println("los states son: " + states.getStates());

			System.out.println("El primero es: "
					+ states.getStates().get(0).getStateId() + " "
					+ states.getStates().get(0).getName());

			// System.out.println("Lo que vuelve de response a la Activity: " +
			// results);
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

}
