package ar.edu.itba.services;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;


public class APIResultReceiver extends ResultReceiver {
	public interface Receiver {
	    public void onReceiveResult(int resultCode, Bundle resultData);
	}

	
    public APIResultReceiver(Handler handler) {
		super(handler);
	}

	private Receiver apiReceiver;

    public void setReceiver(Receiver receiver) {
    	apiReceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (apiReceiver != null) {
        	apiReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}