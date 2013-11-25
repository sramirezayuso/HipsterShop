package ar.edu.itba.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ar.edu.itba.R;

public class AgeAdapter extends ArrayAdapter<String>{
	
	String[] strings;
	Context mContext;
	 
    public AgeAdapter(Context context, int textViewResourceId,   String[] objects) {
        super(context, textViewResourceId, objects);
        strings = objects;
        mContext = context;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
    	LayoutInflater inflater = LayoutInflater.from(mContext);
    	View row = inflater.inflate(R.layout.spinner_item, parent, false);
    	
        TextView label = (TextView)row.findViewById(R.id.text);
        label.setText(strings[position]);
        
        return row;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	LayoutInflater inflater = LayoutInflater.from(mContext);
    	View topBar = inflater.inflate(R.layout.spinner, parent, false);
    	
        ImageView icon=(ImageView)topBar.findViewById(R.id.image);
        icon.setImageResource(R.drawable.hipster);
        
        return topBar;
    }
}
