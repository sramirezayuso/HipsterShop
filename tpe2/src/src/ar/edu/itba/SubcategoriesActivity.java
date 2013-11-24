package ar.edu.itba;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ar.edu.itba.model.Category;

public class SubcategoriesActivity extends MasterActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subcategories);
		// Show the Up button in the action bar.
		setupActionBar();

//		Category[] subcategories = new Category[5];
//		subcategories[0] = new Category(1,"Blazers");
//		subcategories[1] = new Category(2,"Buzos");
//		subcategories[2] = new Category(3,"Calzas");
//		subcategories[3] = new Category(4,"Campera");
//		subcategories[4] = new Category(0,"Ver todos");
//		ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this,
//				android.R.layout.simple_list_item_1, subcategories);
//		ListView listView = (ListView) findViewById(R.id.subcategoriesList);
//		listView.setAdapter(adapter);

	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	public void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subcategories, menu);
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
