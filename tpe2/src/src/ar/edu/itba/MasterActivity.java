package ar.edu.itba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import ar.edu.itba.services.APIResultReceiver;

public class MasterActivity extends Activity implements APIResultReceiver.Receiver {
	public APIResultReceiver apiResultReceiver;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiResultReceiver = new APIResultReceiver(new Handler());
        apiResultReceiver.setReceiver(this);
        setupActionBar();
    }

    public void onPause() {
    	apiResultReceiver.setReceiver(null); // Clear receiver so there are no leaks.
    	super.onPause();
    }
    

	public void onReceiveResult(int resultCode, Bundle resultData) {
		// Might do nothing when we get a result.
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
