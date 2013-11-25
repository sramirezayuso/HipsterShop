package ar.edu.itba;

import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ar.edu.itba.model.GetAllSubcategories;
import ar.edu.itba.model.Subcategory;
import ar.edu.itba.services.APIResultReceiver;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.Utils;

public class SubcategoriesFragment extends ListFragment implements APIResultReceiver.Receiver{
	
	private APIResultReceiver apiResultReceiver;
	List<Subcategory> products;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), ProductsActivity.class);
		intent.putExtra(Utils.ID, Long.valueOf(products.get(position).getId()).intValue());
		startActivity(intent);
	}
	  
	@Override
	public void onStart(){
		super.onStart();
	   	apiResultReceiver = new APIResultReceiver(new Handler());
	   	apiResultReceiver.setReceiver(this);
	   	final Intent intent = HipsterShopApi.getAllSubcategoriesRequest(getActivity(), apiResultReceiver, "1");
	   	getActivity().startService(intent);
	}
	  
	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		System.out.println(resultCode);
		System.out.println("-------------TEST------------");
	    switch (resultCode) {
	    case ApiService.STATUS_RUNNING:
	        //show progress
	    	System.out.println("progress");
	
	        break;
	    case ApiService.STATUS_FINISHED:
	    	GetAllSubcategories response = (GetAllSubcategories) resultData.get(Utils.RESPONSE); 
	    	System.out.println(response.getSubcategories());
	    	products = response.getSubcategories();
			
	    	
		    String[] values = response.getNames();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
			        android.R.layout.simple_list_item_1, values);
			setListAdapter(adapter);
	//	        	ProductAdapter imageAdapter = new ProductAdapter(view.getContext(), products);
	//	        	gridView.setAdapter(imageAdapter);
	//	    		gridView.setOnItemClickListener(mMessageClickedHandler); 
	
	        break;
	    case ApiService.STATUS_ERROR:
	    	System.out.println("error");
	    	System.out.println(resultData.get(Intent.EXTRA_TEXT));
	        // handle the error;
	        break;
	    }
	}

}
