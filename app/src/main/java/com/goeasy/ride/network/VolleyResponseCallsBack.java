package com.goeasy.ride.network;

/**
 * Created by AppsGenii-M#13 on 18-Oct-17.
 */

public interface VolleyResponseCallsBack
{

    void onVolleySuccess(String result, int request_id);

    void onVolleyError(String error, int request_id);
}
