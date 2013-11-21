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
	private Product product;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_prod);
		// Show the Up button in the action bar.
		
		final Intent intent = HipsterShopApi.getProductByIdRequest(this,
				apiResultReceiver, "1");
		startService(intent);
		
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
	
	private void setImages(){
		ImageView imgMain = (ImageView) findViewById(R.id.productImgMain);
		ImageView imgThumb1 = (ImageView) findViewById(R.id.productImgThumbnail1);
		ImageView imgThumb2 = (ImageView) findViewById(R.id.productImgThumbnail2);
		ImageView imgThumb3 = (ImageView) findViewById(R.id.productImgThumbnail3);
		
		String[] images = product.getImageUrls();
		
		new DownloadImageTask(imgMain).execute(product.getImageUrls()[0]);
		
		if (images.length < 2) { return; }
		
		new DownloadImageTask(imgThumb1).execute(product.getImageUrls()[1]);
		imgThumb1.setTag(product.getImageUrls()[1]);
		setThumbnailListener(imgThumb1);
		
		if (images.length < 3) { return; }

		new DownloadImageTask(imgThumb2).execute(product.getImageUrls()[2]);
		imgThumb2.setTag(product.getImageUrls()[2]);
		setThumbnailListener(imgThumb2);
		
		if (images.length < 4) { return; }

		new DownloadImageTask(imgThumb3).execute(product.getImageUrls()[3]);
		imgThumb3.setTag(product.getImageUrls()[3]);
		setThumbnailListener(imgThumb3);
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
			
			this.product = response.getProduct();
			
			TextView productName = (TextView) findViewById(R.id.productName);
			productName.setText(product.getName());
			
			TextView productBrand = (TextView) findViewById(R.id.productBrand);
			productBrand.setText(product.getBrand());
			
			//TextView productDetails = (TextView) findViewById(R.id.productDetails);
			//productDetails.setText(response.getProduct().toString());
			
	
			// TODO: Convert to right currency
			String priceFormat = getResources().getString(R.string.product_price);
			TextView productPrice = (TextView) findViewById(R.id.productPrice);
			productPrice.setText(String.format(priceFormat, response.getProduct().getPrice()));
			
			
			String[] colors_array = product.getColors();
			
			Spinner productColors = (Spinner) findViewById(R.id.productColors);
			ArrayAdapter<String> color_adapter = new ArrayAdapter<String>(this,
					R.layout.spinner_item, colors_array);
			productColors.setAdapter(color_adapter);
			
			
			String[] sizes_array = product.getSizes();
			Spinner productSizes = (Spinner) findViewById(R.id.productSizes);
			ArrayAdapter<String> size_adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,
					sizes_array);
			productSizes.setAdapter(size_adapter);
			
			setImages();
			getActionBar().setTitle(product.getName());

			
			break;
		case ApiService.STATUS_ERROR:
			System.out.println("error");
			System.out.println(resultData.get(Intent.EXTRA_TEXT));
			// handle the error;
			break;
		}
	}


	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	public void setupActionBar() {
		super.setupActionBar();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(getResources().getString(R.string.loading));

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
