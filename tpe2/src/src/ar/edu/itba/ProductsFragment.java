package ar.edu.itba;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import ar.edu.itba.model.Attribute;
import ar.edu.itba.model.Product;
import ar.edu.itba.utils.ProductAdapter;

public class ProductsFragment extends Fragment {
	
	
	 @Override
     public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
       
     }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstnceState){
		View view = inflater.inflate(R.layout.fragment_products, container, false);
		GridView gridView = (GridView) view.findViewById(R.id.fragment_products);
		
		ArrayList<Product> data = new ArrayList<Product>();
		String[] b = { "Levi's" };
		Attribute[] atts = new Attribute[1];
		atts[0] = new Attribute(9, "marca", b);
		data.add(new Product("Baker4", new Integer(100), atts));
		data.add(new Product("Baker5", new Integer(3400), atts));
		data.add(new Product("Baker3", new Integer(60), atts));
		
		ProductAdapter imageAdapter = new ProductAdapter(view.getContext(), data);
		gridView.setAdapter(imageAdapter);
		return view;
	}
	
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
  }
  
  @Override
  public void onDetach() {
    super.onDetach();
  }
}
