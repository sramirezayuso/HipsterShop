package ar.edu.itba;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import ar.edu.itba.model.GetAllCategories;
import ar.edu.itba.model.GetOrderById;
import ar.edu.itba.model.Order;
import ar.edu.itba.model.SpecificOrder;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.OrderProductAdapter;
import ar.edu.itba.utils.Utils;

public class OrderActivity extends MasterActivity {
	private Order order;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				System.out.println(categories.get(position).getName());
				Intent intent = new Intent(OrderActivity.this, SubcategoriesActivity.class);
		    	SharedPreferences prefs = OrderActivity.this.getSharedPreferences(Utils.PREFERENCES, 0);
		    	SharedPreferences.Editor editor = prefs.edit();
		        editor.putInt("selectedCategory", categories.get(position).getId());
		        editor.putString("selectedCategoryName", categories.get(position).getName());
		        editor.commit();
				startActivity(intent);
			}
	    });
        final Intent catIntent = HipsterShopApi.getAllCategoriesRequest(this, apiResultReceiver);
	   	startService(catIntent);
		Intent receivedIntent = getIntent();
		Integer orderId = receivedIntent.getIntExtra(Utils.ID, -1);
		final Intent intent = HipsterShopApi.getOrderByIdRequest(this,
				apiResultReceiver, orderId);
		startService(intent);
	}

	public void showData() {
		TextView address = (TextView) findViewById(R.id.orderAddress);
		address.setText(order.getAddress().getName());

		TextView creationDate = (TextView) findViewById(R.id.orderCreationDate);
		creationDate.setText(order.getReceivedDate());

		TextView deliveryDate = (TextView) findViewById(R.id.orderDeliveryDate);
		if (order.getDeliveredDate() != null)
			deliveryDate.setText(order.getDeliveredDate());

		ListView lv = (ListView) findViewById(R.id.orderList);
		lv.setAdapter(new OrderProductAdapter(this, order.getListItems()));
		lv.setOnItemClickListener(mMessageClickedHandler);

		TextView total = (TextView) findViewById(R.id.orderTotal);
		total.setText(getResources().getString(R.string.order_total)
				+ order.getTotalPrice());
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		System.out.println(resultCode);
		switch (resultCode) {
		case ApiService.STATUS_RUNNING:
			Utils.showProgress(true, this, findViewById(R.id.order_progress_status), findViewById(R.id.order_form));
			break;
		case ApiService.STATUS_FINISHED:
			if(resultData.getString(Utils.METHOD_CLASS).equals("ar.edu.itba.model.GetAllCategories")) {
				GetAllCategories response = (GetAllCategories) resultData.get(Utils.RESPONSE);
				categories = response.getCategories();	
	    	
				String[] values = response.getNames();
				mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, values));
			} else {
				GetOrderById response = (GetOrderById) resultData
						.get(Utils.RESPONSE);
				this.order = response.getOrder();
	
				if (order.getItems().length != 0) {
					showData();
					Utils.showProgress(false, this, findViewById(R.id.order_progress_status), findViewById(R.id.order_form));
				} else {
					new AlertDialog.Builder(this)
						.setMessage(getResources().getString(R.string.order_alert))
						.setPositiveButton(getResources().getString(R.string.accept_message),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,	int id) {
									dialog.cancel();
									finish();
								}
					}).show();
				}
			}

			break;
		case ApiService.STATUS_ERROR:
			System.out.println("error");
			System.out.println(resultData.get(Intent.EXTRA_TEXT));
			// handle the error;
			break;
		}
	}

	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
		public void onItemClick(AdapterView parent, View v, int position,
				long id) {
			Integer productId = ((SpecificOrder) parent.getAdapter().getItem(
					position)).getProduct().getId();

			Intent intent = new Intent(parent.getContext(),
					ProductActivity.class);
			intent.putExtra(Utils.ID, productId);
			startActivity(intent);
		}
	};

}
