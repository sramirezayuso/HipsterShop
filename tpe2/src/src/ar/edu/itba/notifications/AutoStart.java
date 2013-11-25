package ar.edu.itba.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStart extends BroadcastReceiver
{   
    Alarm alarm = new Alarm();
    @Override
    public void onReceive(Context context, Intent intent)
    {   
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
        	System.out.println("boot complete");
            alarm.SetAlarm(context);
        }
    }
}