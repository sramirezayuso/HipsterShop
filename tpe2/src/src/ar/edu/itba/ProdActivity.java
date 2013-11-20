package ar.edu.itba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import ar.edu.itba.model.GetProductById;
import ar.edu.itba.model.Product;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.DownloadImageTask;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.Utils;

public class ProdActivity extends MasterActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prod);
		// Show the Up button in the action bar.
		setupActionBar();

		final Intent intent = HipsterShopApi.getProductByIdRequest(this,
				apiResultReceiver, "1");
		startService(intent);

		TextView productName = (TextView) findViewById(R.id.productName);
		productName.setText("  Baker");

		TextView productBrand = (TextView) findViewById(R.id.productBrand);
		productBrand.setText("Kevingston");

		TextView productPrice = (TextView) findViewById(R.id.productPrice);
		productPrice.setText("$660");

		TextView productDetails = (TextView) findViewById(R.id.productDetails);
		productDetails
				.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed consequat eu augue quis tempus. Quisque consectetur, enim vitae pretium suscipit, eros purus vehicula leo, in venenatis mauris nibh quis velit. Suspendisse non purus est. Nullam ligula leo, luctus sed ante ac, aliquet ultrices eros. Nullam venenatis sapien in pellentesque pellentesque. Vestibulum molestie orci ac tristique blandit. Cras aliquet mi sit amet ultricies pharetra. Fusce id rhoncus mi. Vestibulum congue tortor in nisl feugiat pellentesque. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed consequat eu augue quis tempus. Quisque consectetur, enim vitae pretium suscipit, eros purus vehicula leo, in venenatis mauris nibh quis velit. FIN.");
		productDetails.setMovementMethod(new ScrollingMovementMethod());

		String[] array_spinner = new String[4];
		array_spinner[0] = "Rojo";
		array_spinner[1] = "Negro";
		array_spinner[2] = "Verde";
		array_spinner[3] = "Azul";
		Spinner productColors = (Spinner) findViewById(R.id.productColors);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.spinner_item, array_spinner);
		productColors.setAdapter(adapter);

		array_spinner = new String[3];
		array_spinner[0] = "S";
		array_spinner[1] = "M";
		array_spinner[2] = "L";
		Spinner productSizes = (Spinner) findViewById(R.id.productSizes);
		adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,
				array_spinner);
		productSizes.setAdapter(adapter);

		ImageView imgMain = (ImageView) findViewById(R.id.productImgMain);
		ImageView imgThumb1 = (ImageView) findViewById(R.id.productImgThumbnail1);
		ImageView imgThumb2 = (ImageView) findViewById(R.id.productImgThumbnail2);
		ImageView imgThumb3 = (ImageView) findViewById(R.id.productImgThumbnail3);
		new DownloadImageTask(imgMain).execute("http://eiffel.itba.edu.ar/hci/service3/images/camver1.jpg");
		new DownloadImageTask(imgThumb1).execute("http://eiffel.itba.edu.ar/hci/service3/images/camver1.jpg");
		imgThumb1.setTag("http://eiffel.itba.edu.ar/hci/service3/images/camver1.jpg");
		setThumbnailListener(imgThumb1);
		new DownloadImageTask(imgThumb2).execute("http://eiffel.itba.edu.ar/hci/service3/images/camver2.jpg");
		imgThumb2.setTag("http://eiffel.itba.edu.ar/hci/service3/images/camver2.jpg");
		setThumbnailListener(imgThumb2);
		new DownloadImageTask(imgThumb3).execute("http://eiffel.itba.edu.ar/hci/service3/images/camver3.jpg");
		imgThumb3.setTag("http://eiffel.itba.edu.ar/hci/service3/images/camver3.jpg");
		setThumbnailListener(imgThumb3);

	}

	private void setThumbnailListener(ImageView thumbnail) {
		final String url = (String) thumbnail.getTag();
		thumbnail.setClickable(true);
		thumbnail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ImageView imgMain = (ImageView) findViewById(R.id.productImgMain);
				new DownloadImageTask(imgMain).execute(url); //MEJORAR ESTO
			}
		});

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
			GetProductById response = (GetProductById) resultData
					.get(Utils.RESPONSE);
			
			TextView productDetails = (TextView) findViewById(R.id.productDetails);
			productDetails.setText(response.getProduct().toString());
			
			// setProduct(response.getProduct());
			break;
		case ApiService.STATUS_ERROR:
			System.out.println("error");
			System.out.println(resultData.get(Intent.EXTRA_TEXT));
			// handle the error;
			break;
		}
	}

	private void setProduct(Product product) {
		TextView productName = (TextView) findViewById(R.id.productName);
		productName.setText("*" + product.getName() + "*");
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prod, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
