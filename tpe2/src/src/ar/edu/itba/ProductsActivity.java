package ar.edu.itba;


import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class ProductsActivity extends MasterActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products);
		// Show the Up button in the action bar.
		setupActionBar();

//		GridView gridView = (GridView) findViewById(R.id.gridview);
//
//		ArrayList<Product> data = new ArrayList<Product>();
//		String[] b = { "Levi's" };
//		Attribute[] atts = new Attribute[1];
//		atts[0] = new Attribute(9, "marca", b);
//		data.add(new Product("Baker4", new Integer(100), atts));
//		data.add(new Product("Baker5", new Integer(3400), atts));
//		data.add(new Product("Baker3", new Integer(60), atts));
//
//		ProductAdapter imageAdapter = new ProductAdapter(this, data);
//		gridView.setAdapter(imageAdapter);
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//
//				Intent intent = new Intent(ProductsActivity.this,
//						ProdActivity.class);
//				ProductsActivity.this.startActivity(intent);
//
//			}
//		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	public void setupActionBar() {
		super.setupActionBar();
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.products, menu);
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
