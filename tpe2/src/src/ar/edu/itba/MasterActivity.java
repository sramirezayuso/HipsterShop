package ar.edu.itba;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import ar.edu.itba.model.Category;
import ar.edu.itba.services.APIResultReceiver;

public class MasterActivity extends Activity implements APIResultReceiver.Receiver {
	public APIResultReceiver apiResultReceiver;
    public ListView mDrawerList;
    public List<Category> categories;
    public ActionBarDrawerToggle mDrawerToggle;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        apiResultReceiver = new APIResultReceiver(new Handler());
        apiResultReceiver.setReceiver(this);
        setContentView(R.layout.activity_main);
        
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                ) {

        	String title;
        	
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(title);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            	title = getActionBar().getTitle().toString();
                getActionBar().setTitle(R.string.drawer_title);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        setupActionBar();
    }

    public void onPause() {
    	apiResultReceiver.setReceiver(null); // Clear receiver so there are no leaks.
    	super.onPause();
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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
		// Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {System.out.println("pasa");
          return true;
        }
        System.out.println("no pasa");
        // Handle presses on the action bar items
		Intent intent = null;
	    switch (item.getItemId()) {
	        case R.id.menu_search:
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
