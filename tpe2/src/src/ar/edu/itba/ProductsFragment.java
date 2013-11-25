package ar.edu.itba;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.Spinner;
import ar.edu.itba.model.GetProductsBySubcategoryId;
import ar.edu.itba.model.Product;
import ar.edu.itba.services.APIResultReceiver;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.AgeAdapter;
import ar.edu.itba.utils.GenderAdapter;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.ProductAdapter;
import ar.edu.itba.utils.Utils;

public class ProductsFragment extends Fragment implements APIResultReceiver.Receiver {
	
	private APIResultReceiver apiResultReceiver;
	private GridView gridView;
	private View view;
	private String mGender;
	private String mAge;
	private int subcategoryId;
	
	
	 @Override
	 public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	 }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstnceState){
		view = inflater.inflate(R.layout.fragment_products, container, false);
		gridView = (GridView) view.findViewById(R.id.fragment_products);
		mGender = "";
		mAge = "";
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
	   	subcategoryId = getActivity().getIntent().getIntExtra(Utils.ID, -1);
	   	mGender =  getActivity().getIntent().getExtras().getString(Utils.GENDER, "");
	   	mAge =  getActivity().getIntent().getExtras().getString(Utils.AGE, "");
		final Intent intent = HipsterShopApi.getProductsBySubcategoryIdRequest(getActivity(), apiResultReceiver, String.valueOf(subcategoryId), "", "");
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
        	GetProductsBySubcategoryId response = (GetProductsBySubcategoryId) resultData.get(Utils.RESPONSE); 
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
		
		final ActionBar actionBar = getActivity().getActionBar();
		actionBar.setCustomView(R.layout.products_bar);
		actionBar.setDisplayShowCustomEnabled(true);
		
		Spinner ageSpinner = (Spinner) getActivity().findViewById(R.id.age_spinner);
		Spinner genderSpinner = (Spinner) getActivity().findViewById(R.id.gender_spinner);
		
		ageSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			String[] strings = {"", "Adulto", "Infantil", "Bebe"};
			
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	mAge = strings[position];
		    	final Intent intent = HipsterShopApi.getProductsBySubcategoryIdRequest(getActivity(), apiResultReceiver, String.valueOf(subcategoryId), mGender, mAge);
			    view.getContext().startService(intent);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		    	mAge = strings[0];
		    	final Intent intent = HipsterShopApi.getProductsBySubcategoryIdRequest(getActivity(), apiResultReceiver, String.valueOf(subcategoryId), mGender, mAge);
			    view.getContext().startService(intent);
		    }
		});
		
		genderSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			String[] strings = {"", "Masculino", "Femenino"};
			
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	mGender = strings[position];
		    	final Intent intent = HipsterShopApi.getProductsBySubcategoryIdRequest(getActivity(), apiResultReceiver, String.valueOf(subcategoryId), mGender, mAge);
			    view.getContext().startService(intent);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		    	mGender = strings[0];
		    	final Intent intent = HipsterShopApi.getProductsBySubcategoryIdRequest(getActivity(), apiResultReceiver, String.valueOf(subcategoryId), mGender, mAge);
			    view.getContext().startService(intent);
		    }
		});
		
		ageSpinner.setAdapter(new AgeAdapter(view.getContext(), R.layout.spinner, getActivity().getResources().getStringArray(R.array.age_list)));
		genderSpinner.setAdapter(new GenderAdapter(view.getContext(), R.layout.spinner, getActivity().getResources().getStringArray(R.array.gender_list)));

	}
}

