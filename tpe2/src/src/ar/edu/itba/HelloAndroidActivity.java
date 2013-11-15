package ar.edu.itba;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import ar.edu.itba.model.State;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.HipsterShopApi;


public class HelloAndroidActivity extends MasterActivity  {


    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = HipsterShopApi.getAllStates(this, apiResultReceiver);
        startService(intent);
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
            List<State> results = resultData.getParcelableArrayList("results");
        	System.out.println("Lo que vuelve de response a la Activity: " + results);
            // hide progress
            break;
        case ApiService.STATUS_ERROR:
        	System.out.println("error");

            // handle the error;
            break;
    }
}
        
        /*UTIL
         *Ejemplo de uso de shared preferences  
      	SharedPreferences sp = getPreferences(MODE_PRIVATE);
      	SharedPreferences.Editor editor = sp.edit();
     
      	editor.putString("PASSWORD_VALUE", "123456");
      	editor.commit();//IMPORTANTE! 
      
      
       	Asi se levantan del otro lado 
     	String s= sp.getString("PASSWORD_VALUE","-1");*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(ar.edu.itba.R.menu.main, menu);
        return true;
    }
    public void onProductClick(View view){
    	Intent intent = new Intent(this,ProductActivity.class);
    	intent.putExtra(ProductActivity.EXTRA_MESSAGE, "prueba");
    	startActivity(intent);
    	
    } 

}
