package com.goeasy.ride.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.goeasy.ride.RideGoEasyApp;

import org.json.JSONObject;


public class VolleyServerCom
{
	private VolleyResponseCallsBack volleyResponseCallsBackListener;
	private static String TAG = "Volley Response";
	private static final int socketTimeout = 30000;//30 seconds - change to what you want
	private RetryPolicy retryPolicy;

	public VolleyServerCom()
	{
	}

	public VolleyServerCom(VolleyResponseCallsBack volleyCallBackListener)
	{
		this.volleyResponseCallsBackListener = volleyCallBackListener;

		retryPolicy = new DefaultRetryPolicy(socketTimeout,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
	}


	public void volleyGETRequest(final String url, String request_tag, final int request_id)
	{

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(
				Request.Method.GET,
				url,
				(String) null,

				new Response.Listener<JSONObject>()
				{

					@Override
					public void onResponse(JSONObject response)
					{
						volleyResponseCallsBackListener.onVolleySuccess(response.toString(), request_id);
					}
				},

				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						VolleyLog.d(TAG, "Error: " + error.getMessage());

						if (error.networkResponse != null && error.networkResponse.data != null)
						{
							volleyResponseCallsBackListener.onVolleyError(new String(error.networkResponse.data), request_id);
						}
						else
						{
							volleyResponseCallsBackListener.onVolleyError(error.toString(), request_id);
						}
					}
				});

		jsonObjReq.setRetryPolicy(retryPolicy);
		// Adding request to request queue
		RideGoEasyApp.getInstance().addToRequestQueue(jsonObjReq, request_tag);
	}

}
