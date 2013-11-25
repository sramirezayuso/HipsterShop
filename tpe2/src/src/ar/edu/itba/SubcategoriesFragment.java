package ar.edu.itba;

import java.util.List;

import android.app.ActionBar;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import ar.edu.itba.model.GetAllSubcategories;
import ar.edu.itba.model.Subcategory;
import ar.edu.itba.services.APIResultReceiver;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.AgeAdapter;
import ar.edu.itba.utils.GenderAdapter;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.Utils;

public class SubcategoriesFragment extends ListFragment implements APIResultReceiver.Receiver{
	
	private APIResultReceiver apiResultReceiver;
	private List<Subcategory> products;
	private String mGender;
	private String mAge;
	private int categoryId;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), ProductsActivity.class);
		intent.putExtra(Utils.ID, Long.valueOf(products.get(position).getId()).intValue());
		intent.putExtra(Utils.GENDER, mGender);
		intent.putExtra(Utils.AGE, mAge);
		startActivity(intent);
	}
	  
	@Override
	public void onStart(){
		super.onStart();
		mGender = "";
		mAge = "";
		setUpSpinners();
	   	apiResultReceiver = new APIResultReceiver(new Handler());
	   	apiResultReceiver.setReceiver(this);
	   	categoryId = getActivity().getIntent().getIntExtra(Utils.ID, -1);
	   	final Intent intent = HipsterShopApi.getAllSubcategoriesRequest(getActivity(), apiResultReceiver, String.valueOf(categoryId), "", "");
	   	getActivity().startService(intent);
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
	    	GetAllSubcategories response = (GetAllSubcategories) resultData.get(Utils.RESPONSE); 
	    	System.out.println(response.getSubcategories());
	    	products = response.getSubcategories();
			
	    	
		    String[] values = response.getNames();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
			        android.R.layout.simple_list_item_1, values);
			setListAdapter(adapter);
	
	        break;
	    case ApiService.STATUS_ERROR:
	    	System.out.println("error");
	    	System.out.println(resultData.get(Intent.EXTRA_TEXT));
	        // handle the error;
	        break;
	    }
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
		    	final Intent intent = HipsterShopApi.getAllSubcategoriesRequest(getActivity(), apiResultReceiver, String.valueOf(categoryId), mGender, mAge);
		    	getActivity().startService(intent);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		    }
		});
		
		genderSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			String[] strings = {"", "Masculino", "Femenino"};
			
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	mGender = strings[position];
		    	final Intent intent = HipsterShopApi.getAllSubcategoriesRequest(getActivity(), apiResultReceiver, String.valueOf(categoryId), mGender, mAge);
		    	getActivity().startService(intent);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		    }
		});
		
		ageSpinner.setAdapter(new AgeAdapter(getActivity(), R.layout.spinner, getActivity().getResources().getStringArray(R.array.age_list)));
		genderSpinner.setAdapter(new GenderAdapter(getActivity(), R.layout.spinner, getActivity().getResources().getStringArray(R.array.gender_list)));

	}

}
