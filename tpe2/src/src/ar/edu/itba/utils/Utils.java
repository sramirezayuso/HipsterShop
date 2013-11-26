package ar.edu.itba.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import ar.edu.itba.OrderActivity;
import ar.edu.itba.R;

public class Utils {
    public final static String RECEIVER = "receiver";
    public final static String COMMAND = "command";
    public final static String REQUEST_URL = "request_url";
    public final static String METHOD_CLASS = "method_class";
    public final static String RESPONSE = "response";
    public final static String ID = "id";
    public final static String CAT_ID = "cat_id";
    public final static String PREFERENCES = "preferences";
    public final static String GENDER = "gender";
    public final static String AGE = "age";
    public final static String ORDERS = "orders";

    
	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static void showProgress(final boolean show, Activity activity, final View statusView, final View formView) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = activity.getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			statusView.setVisibility(View.VISIBLE);
			statusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							statusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			formView.setVisibility(View.VISIBLE);
			formView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							formView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			statusView.setVisibility(show ? View.VISIBLE : View.GONE);
			formView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	public static void showNotification(Activity activity, String msg, Integer orderId) {
		NotificationCompat.Builder mBuilder =
	   	        new NotificationCompat.Builder(activity)
	   	        .setSmallIcon(R.drawable.ic_action_good)
	   	        .setContentTitle(activity.getResources().getString(R.string.notification_title))
	   	        .setContentText(msg);
	   	// Creates an explicit intent for an Activity in your app
	   	Intent resultIntent = new Intent(activity, OrderActivity.class);
	   	resultIntent.putExtra(Utils.ID, orderId);

	   	// The stack builder object will contain an artificial back stack for the
	   	// started Activity.
	   	// This ensures that navigating backward from the Activity leads out of
	   	// your application to the Home screen.
	   	TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
	   	// Adds the back stack for the Intent (but not the Intent itself)
	   	stackBuilder.addParentStack(OrderActivity.class);
	   	// Adds the Intent that starts the Activity to the top of the stack
	   	stackBuilder.addNextIntent(resultIntent);
	   	PendingIntent resultPendingIntent =
	   	        stackBuilder.getPendingIntent(
	   	            0,
	   	            PendingIntent.FLAG_UPDATE_CURRENT
	   	        );
	   	mBuilder.setContentIntent(resultPendingIntent);
	   	NotificationManager mNotificationManager =
	   	    (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
	   	// mId allows you to update the notification later on.
	   	mNotificationManager.notify(0, mBuilder.build());
	}
}
