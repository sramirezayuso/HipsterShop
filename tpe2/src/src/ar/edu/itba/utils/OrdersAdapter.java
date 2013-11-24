package ar.edu.itba.utils;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ar.edu.itba.OrderActivity;
import ar.edu.itba.R;
import ar.edu.itba.model.Order;

public class OrdersAdapter extends PagerAdapter {

	private ArrayAdapter<List<Order>> adapter;
	private Context context;

	public OrdersAdapter(Context context, ArrayAdapter<List<Order>> adapter) {
		super();
		this.context = context;
		this.adapter = adapter;
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((ListView) view);
	}

	@Override
	public int getCount() {
		return adapter.getCount();
	}

	@Override
	public Object instantiateItem(View collection, int position) {
		ListView v = new ListView( context );
	    SpecificOrderAdapter adp = new SpecificOrderAdapter(context, adapter.getItem(position));
	    v.setAdapter( adp );
	    v.setOnItemClickListener(mMessageClickedHandler);

	    ( (ViewPager) collection ).addView( v, 0 );
	    return v;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		 return view==((ListView)object);
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
		switch(position) {
		case 0: return context.getResources().getString(R.string.received);
		case 1: return context.getResources().getString(R.string.processed);
		case 2: return context.getResources().getString(R.string.shipped);
		case 3: return context.getResources().getString(R.string.delivered);
		}
		return "";
    }
	
    private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
        	Integer orderId = ((Order) parent.getAdapter().getItem(position)).getId();
        	
    		Intent intent = new Intent(parent.getContext(), OrderActivity.class);
    		intent.putExtra(Utils.ID, orderId);
    		context.startActivity(intent);
        }
    };

}
