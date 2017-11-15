package com.goeasy.ride;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by 123 on 16-Nov-17.
 */

public class RideGoEasyApp extends Application
{
	public static final String TAG = RideGoEasyApp.class.getSimpleName();

	private RequestQueue mRequestQueue;
	private static RideGoEasyApp mInstance;

	@Override
	public void onCreate()
	{
		super.onCreate();

		mInstance = this;
	}

	public RequestQueue getRequestQueue()
	{
		if (mRequestQueue == null)
		{
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}


	public static synchronized RideGoEasyApp getInstance()
	{
		return mInstance;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag)
	{
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req)
	{
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag)
	{
		if (mRequestQueue != null)
		{
			mRequestQueue.cancelAll(tag);
		}
	}

	public void cancelAllPendingRequests()
	{
		mRequestQueue.cancelAll(new RequestQueue.RequestFilter()
		{
			@Override
			public boolean apply(Request<?> request)
			{
				// do I have to cancel this?
				return true; // -> always yes
			}
		});
	}

}
