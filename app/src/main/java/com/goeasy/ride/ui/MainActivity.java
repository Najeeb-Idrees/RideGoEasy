package com.goeasy.ride.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.najeeb.uberclone.R;
import com.goeasy.ride.network.VolleyResponseCallsBack;
import com.goeasy.ride.network.VolleyServerCom;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements VolleyResponseCallsBack,
		OnMapReadyCallback
{

	VolleyServerCom volleyServerCom = null;
	boolean mLocationPermissionGranted = false;
	GoogleMap mGoogleMap = null;

	//	Location mLastKnownLocation = null;
	//	GeoDataClient mGeoDataClient;
	//	PlaceDetectionClient mPlaceDetectionClient;
	//	FusedLocationProviderClient mFusedLocationProviderClient;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setUpGMap();
		//		setUpCurrentLocation();
		getDistanceBetweenTwoPlaces("Shad Bagh", "University of Lahore");
		getDistanceBetweenTwoPlaces("31.60981,74.3368011", "31.3913063,74.2269274");
	}

	//	private void setUpCurrentLocation()
	//	{
	//		// Construct a GeoDataClient.
	//		mGeoDataClient = Places.getGeoDataClient(this, null);
	//
	//		// Construct a PlaceDetectionClient.
	//		mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
	//
	//		// Construct a FusedLocationProviderClient.
	//		mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
	//	}

	private void setUpGMap()
	{
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	//Shadbagh, University of lahore
	private void getDistanceBetweenTwoPlaces(String origin, String destination)
	{
		origin = origin.replaceAll(" ", "+");
		destination = destination.replaceAll(" ", "+");

		hitDirectionAPI(origin, destination);
	}

	private void hitDirectionAPI(String origin, String destination)
	{
		String url = "https://maps.googleapis.com/maps/api/directions/json?" +
				"origin=" + origin + "&destination=" + destination
				+ "&key=" + getString(R.string.DIRECTION_API_KEY) + "&sensor=false";

		volleyServerCom = new VolleyServerCom(this);
		volleyServerCom.volleyGETRequest(url, "direction", 1);
	}

	@Override
	public void onVolleySuccess(String result, int request_id)
	{
		try
		{
			JSONObject jsonObject = new JSONObject(result);
			if (request_id == 1 && jsonObject.getString("status").equalsIgnoreCase("OK"))
			{
				Log.i("result", jsonObject.toString());

				JSONArray routeArray = jsonObject.getJSONArray("routes");
				JSONObject routes = routeArray.getJSONObject(0);

				JSONArray legsArray = routes.getJSONArray("legs");
				JSONObject legs = legsArray.getJSONObject(0);

				JSONObject distOb = legs.getJSONObject("distance");
				JSONObject timeOb = legs.getJSONObject("duration");

				Log.i("Diatance :", distOb.getString("text"));
				Log.i("Time :", timeOb.getString("text"));
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onVolleyError(String error, int request_id)
	{
		Log.e("result", error);
	}

	public void onMapReady(GoogleMap googleMap)
	{
		//TODO set device current location
		LatLng sydney = new LatLng(-33.852, 151.211);
		googleMap.addMarker(new MarkerOptions().position(sydney)
				.title("Marker in Sydney"));
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

		mGoogleMap = googleMap;

		// Do other setup activities here too, as described elsewhere in this tutorial.

		// Turn on the My Location layer and the related control on the map.
		//		updateLocationUI();

		// Get the current location of the device and set the position of the map.
		//		getDeviceLocation();
	}

	//	private void getLocationPermission()
	//	{
	//	/*
	//	 * Request location permission, so that we can get the location of the
	//     * device. The result of the permission request is handled by a callback,
	//     * onRequestPermissionsResult.
	//     */
	//		if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
	//				android.Manifest.permission.ACCESS_FINE_LOCATION)
	//				== PackageManager.PERMISSION_GRANTED)
	//		{
	//			mLocationPermissionGranted = true;
	//		}
	//		else
	//		{
	//			ActivityCompat.requestPermissions(this,
	//					new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
	//					12);
	//		}
	//	}
	//
	//	@Override
	//	public void onRequestPermissionsResult(int requestCode,
	//	                                       @NonNull String permissions[],
	//	                                       @NonNull int[] grantResults)
	//	{
	//		mLocationPermissionGranted = false;
	//		switch (requestCode)
	//		{
	//			case 12:
	//			{
	//				// If request is cancelled, the result arrays are empty.
	//				if (grantResults.length > 0
	//						&& grantResults[0] == PackageManager.PERMISSION_GRANTED)
	//				{
	//					mLocationPermissionGranted = true;
	//				}
	//			}
	//		}
	//		updateLocationUI();
	//	}
	//
	//
	//	private void updateLocationUI()
	//	{
	//		if (mGoogleMap == null)
	//		{
	//			return;
	//		}
	//		try
	//		{
	//			if (mLocationPermissionGranted)
	//			{
	//				mGoogleMap.setMyLocationEnabled(true);
	//				mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
	//			}
	//			else
	//			{
	//				mGoogleMap.setMyLocationEnabled(false);
	//				mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
	//				mLastKnownLocation = null;
	//				getLocationPermission();
	//			}
	//		}
	//		catch (SecurityException e)
	//		{
	//			Log.e("Exception: %s", e.getMessage());
	//		}
	//	}
	//
	//	private void getDeviceLocation()
	//	{
	//	/*
	//	 * Get the best and most recent location of the device, which may be null in rare
	//     * cases when a location is not available.
	//     */
	//		try
	//		{
	//			if (mLocationPermissionGranted)
	//			{
	//
	//				mFusedLocationProviderClient.getLastLocation()
	//						.addOnCompleteListener(new OnCompleteListener<Location>()
	//						{
	//							@Override
	//							public void onComplete(@NonNull Task<Location> task)
	//							{
	//								if (task.isSuccessful() && task.getResult() != null)
	//								{
	//									mLastKnownLocation = task.getResult();
	//								}
	//								else
	//								{
	//									Log.w("test", "Failed to get location.");
	//								}
	//							}
	//						});
	//
	//				mFusedLocationProviderClient.getLastLocation()
	//						.addOnCompleteListener(this, new OnCompleteListener<Location>()
	//						{
	//							@Override
	//							public void onComplete(@NonNull Task<Location> task)
	//							{
	//								if (task.isSuccessful() && task.getResult() != null)
	//								{
	//									// Set the map's camera position to the current location of the device.
	//									mLastKnownLocation = task.getResult();
	//									mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
	//											new LatLng(mLastKnownLocation.getLatitude(),
	//													mLastKnownLocation.getLongitude()), 20));
	//								}
	//								else
	//								{
	//									LatLng sydney = new LatLng(-33.852, 151.211);
	//
	//									Log.d("Device Location", "Current location is null. Using defaults.");
	//									Log.e("Device Location", "Exception: %s", task.getException());
	//									mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 20));
	//									mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
	//								}
	//							}
	//						});
	//			}
	//		}
	//		catch (SecurityException e)
	//		{
	//			Log.e("Exception: %s", e.getMessage());
	//		}
	//	}
}
