package ar.edu.itba;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import ar.edu.itba.model.GetProductsByCategoryId;
import ar.edu.itba.model.Product;
import ar.edu.itba.services.APIResultReceiver;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.ProductAdapter;
import ar.edu.itba.utils.Utils;

public class ProductsFragment extends Fragment implements APIResultReceiver.Receiver {
	
	public APIResultReceiver apiResultReceiver;
	public GridView gridView;
	public View view;
	
	 @Override
	 public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	 }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstnceState){
		view = inflater.inflate(R.layout.fragment_products, container, false);
		gridView = (GridView) view.findViewById(R.id.fragment_products);
	
		return view;
	}
	
	
    
 // Create a message handling object as an anonymous class.
    private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
    		Intent intent = new Intent(parent.getContext(), ProductActivity.class);
    		intent.putExtra(Utils.ID, Long.valueOf(id).intValue());
    		startActivity(intent);
        }
    };
	
	
	@Override
	public void onStart(){
		super.onStart();
		setUpSpinners();
        apiResultReceiver = new APIResultReceiver(new Handler());
        apiResultReceiver.setReceiver(this);
		final Intent intent = HipsterShopApi.getProductsBySubcategoryIdRequest(getActivity(), apiResultReceiver, "1", "", "");
	    view.getContext().startService(intent);
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
        	GetProductsByCategoryId response = (GetProductsByCategoryId) resultData.get(Utils.RESPONSE); 
        	System.out.println(response.getProducts());
        	List<Product> products = response.getProducts();
    		    		 
        	ProductAdapter imageAdapter = new ProductAdapter(view.getContext(), products);
        	gridView.setAdapter(imageAdapter);
    		gridView.setOnItemClickListener(mMessageClickedHandler); 

            break;
        case ApiService.STATUS_ERROR:
        	System.out.println("error");
        	System.out.println(resultData.get(Intent.EXTRA_TEXT));
            // handle the error;
            break;
        }
    }
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
  
	@Override
	public void onDetach() {
		super.onDetach();
	}
	
	public void setUpSpinners(){
		SpinnerAdapter genderSpinner = ArrayAdapter.createFromResource(view.getContext(), R.array.gender_list,
		          android.R.layout.simple_spinner_dropdown_item);
		OnNavigationListener onGenderChange = new OnNavigationListener() {
			String[] strings = {"", "Masculino", "Femenino"};
			
			@Override
			public boolean onNavigationItemSelected(int position, long itemId) {
				final Intent intent = HipsterShopApi.getProductsBySubcategoryIdRequest(getActivity(), apiResultReceiver, "1", strings[position], "");
			    view.getContext().startService(intent);
				return true;
			}
		};
		
		SpinnerAdapter ageSpinner = ArrayAdapter.createFromResource(view.getContext(), R.array.age_list,
		          android.R.layout.simple_spinner_dropdown_item);
		OnNavigationListener onAgeChange = new OnNavigationListener() {
			String[] strings = {"", "Adulto", "Infantil", "Bebe"};
			
			@Override
			public boolean onNavigationItemSelected(int position, long itemId) {
				final Intent intent = HipsterShopApi.getProductsBySubcategoryIdRequest(getActivity(), apiResultReceiver, "1", "", strings[position]);
			    view.getContext().startService(intent);
				return true;
			}
		};
		
		getActivity().getActionBar().setListNavigationCallbacks(genderSpinner, onGenderChange);
		getActivity().getActionBar().setListNavigationCallbacks(ageSpinner, onAgeChange);
	}
}
