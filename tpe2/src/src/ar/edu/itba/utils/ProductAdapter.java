package ar.edu.itba.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ar.edu.itba.R;
import ar.edu.itba.model.Attribute;
import ar.edu.itba.model.Product;

public class ProductAdapter extends BaseAdapter {
	private List<Product> items = new ArrayList<Product>();
	private LayoutInflater inflater;

	public ProductAdapter(Context context, List<Product> items) {
		inflater = LayoutInflater.from(context);
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int i) {
		return items.get(i);
	}

	@Override
	public long getItemId(int i) {
		return Long.parseLong(items.get(i).getName());
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		View v = view;
		ImageView picture;
		TextView data;

		if (v == null) {
			v = inflater.inflate(R.layout.grid_element, viewGroup, false);
			v.setTag(R.id.itemImage, v.findViewById(R.id.itemImage));
			v.setTag(R.id.itemData, v.findViewById(R.id.itemData));
		}

		picture = (ImageView) v.getTag(R.id.itemImage);
		data = (TextView) v.getTag(R.id.itemData);

		Product item = (Product) getItem(i);

		new DownloadImageTask(picture).execute(item.getImageUrls()[0]);
		data.setText(getProductData(item));

		return v;
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
