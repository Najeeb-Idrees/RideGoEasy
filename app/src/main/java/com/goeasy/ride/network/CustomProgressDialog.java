package com.goeasy.ride.network;

import android.app.ProgressDialog;
import android.content.Context;


public class CustomProgressDialog
{

	private ProgressDialog progressDialog = null;
	private Context context;

	public CustomProgressDialog()
	{
	}

	public CustomProgressDialog(Context context)
	{
		this.context = context;
	}

	public void startProgressDialog()
	{
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Please wait...");
		progressDialog.setCanceledOnTouchOutside(false);

		progressDialog.show();
	}

	public void startProgressDialog(String dialog, boolean cancelable)
	{
		startProgressDialog();
		progressDialog.setMessage(dialog);
		progressDialog.setCancelable(cancelable);
	}

	public void stopProgressDialog()
	{
		if (progressDialog != null)
		{
			if (progressDialog.isShowing())
			{
				progressDialog.dismiss();

				progressDialog = null;
			}
		}
	}

	public ProgressDialog getProgressDialog()
	{
		return progressDialog;
	}

	public void setProgressDialog(ProgressDialog progressDialog)
	{
		this.progressDialog = progressDialog;
	}
}
