package ar.edu.itba;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import ar.edu.itba.model.GetOrderById;
import ar.edu.itba.model.Order;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.SpecificOrderAdapter;
import ar.edu.itba.utils.Utils;

public class OrderActivity extends MasterActivity {
	private Order order;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		Intent receivedIntent = getIntent();
		Integer orderId = receivedIntent.getIntExtra(Utils.ID, -1);
		final Intent intent = HipsterShopApi.getOrderByIdRequest(this,
				apiResultReceiver, orderId);
		startService(intent);
	}

	public void showData() {
		// Implement displaying data
		TextView address = (TextView) findViewById(R.id.orderAddress);
		address.setText(order.getAddress().getName());

		TextView creationDate = (TextView) findViewById(R.id.orderCreationDate);
		creationDate.setText(order.getReceivedDate());

		TextView deliveryDate = (TextView) findViewById(R.id.orderDeliveryDate);
		if (order.getDeliveredDate() != null)
			deliveryDate.setText(order.getDeliveredDate());

		ListView lv = (ListView) findViewById(R.id.orderList);
		lv.setAdapter(new SpecificOrderAdapter(this, order.getListItems()));

		TextView total = (TextView) findViewById(R.id.orderTotal);
		total.append(String.valueOf(order.getTotalPrice()));
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		System.out.println(resultCode);
		switch (resultCode) {
		case ApiService.STATUS_RUNNING:
			// show progress
			System.out.println("progress");

			break;
		case ApiService.STATUS_FINISHED:
			GetOrderById response = (GetOrderById) resultData
					.get(Utils.RESPONSE);

			this.order = response.getOrder();
			showData();

			break;
		case ApiService.STATUS_ERROR:
			System.out.println("error");
			System.out.println(resultData.get(Intent.EXTRA_TEXT));
			// handle the error;
			break;
		}
	}

}
