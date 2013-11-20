package ar.edu.itba;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import ar.edu.itba.model.Attribute;
import ar.edu.itba.model.Product;
import ar.edu.itba.utils.DownloadImageTask;

public class ProductImageAdapter extends BaseAdapter{
	
	private Context mContext;
	private List<Product> items = new ArrayList<Product>();
	private LayoutInflater inflater;

	// Constructor
	public ProductImageAdapter(Context c, List<Product> items){
		this.mContext = c;
		this.items = items;
		this.inflater = LayoutInflater.from(mContext);	
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int idx) {
		return items.get(idx);
	}

	@Override
	public long getItemId(int idx) {
		return Long.parseLong(items.get(idx).getName());
	}

	@Override
	public View getView(int idx, View convertView, ViewGroup parent) {
		View view = convertView;
		ImageView picture;
		TextView data;

		if (view == null) {
			view = inflater.inflate(R.layout.grid_element, parent, false);
			view.setTag(R.id.itemImage, view.findViewById(R.id.itemImage));
			view.setTag(R.id.itemData, view.findViewById(R.id.itemData));
		}

		picture = (ImageView) view.getTag(R.id.itemImage);
		data = (TextView) view.getTag(R.id.itemData);

		Product item = (Product) getItem(idx);

		new DownloadImageTask(picture).execute(item.getImageUrl()[0]);
		data.setText(getProductData(item));

		return view;
	}
	
	private String getProductData(Product prod) {
		String brand = "";
		for (Attribute att : prod.getAttributes()) {
			if (att.getId() == 9 && att.getName().equals("marca")) {
				brand = att.getValues()[0];
			}
		}
		return prod.getName() + '\n' + "$" + prod.getPrice() + " - " + brand;
	}

}
