package ar.edu.itba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import ar.edu.itba.model.Attribute;
import ar.edu.itba.model.GetAllCategories;
import ar.edu.itba.model.GetProductById;
import ar.edu.itba.model.Product;
import ar.edu.itba.services.ApiService;
import ar.edu.itba.utils.DownloadImageTask;
import ar.edu.itba.utils.HipsterShopApi;
import ar.edu.itba.utils.Utils;

public class ProductActivity extends MasterActivity {
	private Product product;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_prod);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				System.out.println(categories.get(position).getName());
				Intent intent = new Intent(ProductActivity.this, SubcategoriesActivity.class);
		    	SharedPreferences prefs = ProductActivity.this.getSharedPreferences(Utils.PREFERENCES, 0);
		    	SharedPreferences.Editor editor = prefs.edit();
		        editor.putInt("selectedCategory", categories.get(position).getId());
		        editor.putString("selectedCategoryName", categories.get(position).getName());
		        editor.commit();
				startActivity(intent);
			}
	    });
		
        final Intent catIntent = HipsterShopApi.getAllCategoriesRequest(this, apiResultReceiver);
	   	startService(catIntent);
	   	
		// Show the Up button in the action bar.
		Intent receivedIntent = getIntent();
		Integer productId = receivedIntent.getIntExtra(Utils.ID, -1);
		final Intent intent = HipsterShopApi.getProductByIdRequest(this,
				apiResultReceiver, productId);
		
		startService(intent);
		
	}

	private void setThumbnailListener(ImageView thumbnail) {
		final String url = (String) thumbnail.getTag();
		thumbnail.setClickable(true);
		thumbnail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ImageView imgMain = (ImageView) findViewById(R.id.productImgMain);
				new DownloadImageTask(imgMain).execute(url); //TODO: MEJORAR ESTO
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
		
		new DownloadImageTask(imgThumb1).execute(product.getImageUrls()[0]);
		imgThumb1.setTag(product.getImageUrls()[0]);
		setThumbnailListener(imgThumb1);
		
		if (images.length < 2) { return; }

		new DownloadImageTask(imgThumb2).execute(product.getImageUrls()[1]);
		imgThumb2.setTag(product.getImageUrls()[1]);
		setThumbnailListener(imgThumb2);
		
		if (images.length < 3) { return; }

		new DownloadImageTask(imgThumb3).execute(product.getImageUrls()[2]);
		imgThumb3.setTag(product.getImageUrls()[2]);
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
			if(resultData.getString(Utils.METHOD_CLASS).equals("ar.edu.itba.model.GetAllCategories")) {
				GetAllCategories response = (GetAllCategories) resultData.get(Utils.RESPONSE);
				categories = response.getCategories();	
	    	
				String[] values = response.getNames();
				mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, values));
			} else {
				GetProductById response = (GetProductById) resultData.get(Utils.RESPONSE);
				
				this.product = response.getProduct();
				
				TextView productName = (TextView) findViewById(R.id.productName);
				productName.setText(product.getId().toString());
				
				TextView productBrand = (TextView) findViewById(R.id.productBrand);
				productBrand.setText(product.getBrand());
				
				/*TextView productDetails = (TextView) findViewById(R.id.productDetails);
				productDetails.setText(getProductDetails(product));
				productDetails.setMovementMethod(new ScrollingMovementMethod());
				
				String priceFormat = getResources().getString(R.string.product_format);
				TextView productPrice = (TextView) findViewById(R.id.productPrice);
				String num = String.format(priceFormat, response.getProduct().getPrice());
				productPrice.setText(getResources().getString(R.string.order_price) + num);
				
				String[] colors_array = product.getColors();
				if(colors_array != null) {
					Spinner productColors = (Spinner) findViewById(R.id.productColors);
					ArrayAdapter<String> color_adapter = new ArrayAdapter<String>(this,
							R.layout.spinner_item, colors_array);
					productColors.setAdapter(color_adapter);
				}
				
				String[] sizes_array = product.getSizes();
				if(sizes_array != null) {
					Spinner productSizes = (Spinner) findViewById(R.id.productSizes);
					ArrayAdapter<String> size_adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,
							sizes_array);
					productSizes.setAdapter(size_adapter);
				}
				
				setImages();*/
				getActionBar().setTitle(product.getName());
			}

			
			break;
		case ApiService.STATUS_ERROR:
			System.out.println("error");
			System.out.println(resultData.get(Intent.EXTRA_TEXT));
			// handle the error;
			break;
		}
	}
	
	private String getProductDetails(Product product) {
		StringBuffer stbf = new StringBuffer();

		for(Attribute att: product.getAttributes()) {
			switch(att.getId()) {
				case 4:
				case 7: break;
				case 5:
				case 6:
						stbf.append(att.getValues()[0] + '\n');
						break;
				default:
						stbf.append(att.getName() + ":");
						for(String each : att.getValues()) {
							stbf.append(each + "-");
						}
						stbf.deleteCharAt(stbf.length()-1);
						stbf.append('\n');
			}
		}
		
		return stbf.toString();
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
