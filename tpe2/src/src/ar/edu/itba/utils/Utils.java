package ar.edu.itba.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;

public class Utils {
    public final static String RECEIVER = "receiver";
    public final static String COMMAND = "command";
    public final static String REQUEST_URL = "request_url";
    public final static String METHOD_CLASS = "method_class";
    public final static String RESPONSE = "response";
    public final static String ID = "id";
    public final static String PREFERENCES = "preferences";
    public final static String GENDER = "gender";
    public final static String AGE = "age";


    
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

}
