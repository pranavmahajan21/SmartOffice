package com.mw.smartoffice.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

/**
 * Use this class only in Activity & never in Application class. Pass context
 * using 'this' from Activity & never Application. Neither can you use
 * getApplicationContext().
 *
 * Otherwise you will get this exception:
 * android.view.WindowManager$BadTokenException: Unable to add window -- token
 * null is not for an application
 *
 * Solution:
 * http://stackoverflow.com/questions/2634991/android-1-6-android-view-
 * windowmanagerbadtokenexception-unable-to-add-window
 *
 * This is a reported bug in android.
 * **/

 public class CreateDialog {
	Context context;
	ProgressDialog progressDialog;

	public CreateDialog(Context context) {
		this.context = context;
	}

	public AlertDialog.Builder createAlertDialog(String title, String message,
			boolean cancel) {
		// System.out.println("createAlertDialog");
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		if (title != null) {
			alertDialogBuilder.setTitle(title);
		}
		if (message != null) {
			alertDialogBuilder.setMessage(message);
		}
		alertDialogBuilder.setCancelable(cancel);
		return alertDialogBuilder;
	}

	public ProgressDialog createProgressDialog(String title, String message,
			boolean indeterminateState, Drawable drawable) {
		System.out.println("creating progress dialog");
		progressDialog = new ProgressDialog(context);
		if (title != null) {
			progressDialog.setTitle(title);
		}
		if (message != null) {
			progressDialog.setMessage(message);
		}

		progressDialog.setIndeterminate(indeterminateState);
		if (drawable != null)
			progressDialog.setIndeterminateDrawable(drawable);
		progressDialog.setCancelable(false);
		return progressDialog;
	}

	public void singleOKButton(final AlertDialog.Builder alertDialogBuilder) {
		alertDialogBuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
						// finish();
					}
				});
	}
}
