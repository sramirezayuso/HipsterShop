package ar.edu.itba.notifications;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service
{
    Alarm alarm = new Alarm();
    public void onCreate()
    {
    	System.out.println("create service alarm");

    }

    public void onStart(Context context,Intent intent, int startId)
    {
    	System.out.println("start service alarm");
        alarm.SetAlarm(context);
    }

    @Override
    public IBinder onBind(Intent intent) 
    {
        return null;
    }
}