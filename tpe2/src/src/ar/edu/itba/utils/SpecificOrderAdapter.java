package ar.edu.itba.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ar.edu.itba.R;
import ar.edu.itba.model.SpecificOrder;

public class SpecificOrderAdapter extends BaseAdapter {
	protected Activity activity;
	protected ArrayList<SpecificOrder> items;

	public SpecificOrderAdapter(Activity activity,
			ArrayList<SpecificOrder> items) {
		this.activity = activity;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getId();
	}

	@Override
	public View getView(int position, View contentView, ViewGroup parent) {
		View vi = contentView;

		if (contentView == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vi = inflater.inflate(R.layout.specific_order_element, null);
		}

		SpecificOrder item = items.get(position);

		ImageView image = (ImageView) vi.findViewById(R.id.specificOrderImage);
		new DownloadImageTask(image).execute(item.getProduct().getImageUrl());

		TextView product = (TextView) vi
				.findViewById(R.id.specificOrderProduct);
		product.setText(item.getProduct().getName());

		TextView price = (TextView) vi.findViewById(R.id.specificOrderPrice);
		price.setText(String.valueOf(item.getPrice() * item.getQuantity()));

		return vi;
	}
}