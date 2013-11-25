package ar.edu.itba.utils;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ar.edu.itba.R;
import ar.edu.itba.model.Order;

public class SpecificOrderAdapter extends BaseAdapter {
	protected Context activity;
	protected List<Order> items;

	public SpecificOrderAdapter(Context context, List<Order> items) {
		this.activity = context;
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

		Order item = items.get(position);

		TextView number = (TextView) vi.findViewById(R.id.orderItemNumber);
		number.setText(activity.getResources().getString(R.string.order_request) + item.getId());

		TextView address = (TextView) vi.findViewById(R.id.orderItemAddress);
		if (item.getAddress() != null && item.getAddress().getName() != null)
			address.setText(item.getAddress().getName());

		TextView creationDate = (TextView) vi
				.findViewById(R.id.orderItemCreationDate);
		creationDate.setText(item.getReceivedDate());

		TextView deliveryDate = (TextView) vi
				.findViewById(R.id.orderItemDeliveryDate);
		deliveryDate.setText(item.getDeliveredDate());

		return vi;
	}
}