package com.ilove.ilove.Class

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat


public class GpsTracker(activity: Activity) : Service(), LocationListener {

    var mContext : Context? = null
    var latitude : Double? = null
    var longitude : Double? = null
    var location : Location? = null

    private var MIN_DISTANCE_CHANGE_FOR_UPDATES = 10
    private var MIN_TIME_BW_UPDATES = 1000 * 60 * 1
    protected var locationManager : LocationManager? = null


    init {
        mContext = activity
        getGps()
    }


    fun distanceTo(userLocation : Location, partnerLocation: Location) {

    }

    fun getGps() : Location? {
        try {
            locationManager =
                mContext!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGPSEnabled =
                locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled =
                locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGPSEnabled && !isNetworkEnabled) {
            } else {
                val hasFineLocationPermission = ContextCompat.checkSelfPermission(
                    mContext!!,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
                val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
                    mContext!!,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
                if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                    hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
                ) {
                } else return null
                if (isNetworkEnabled) {
                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES.toLong(),
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                        this
                    )
                    if (locationManager != null) {
                        location =
                            locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        if (location != null) {
                            latitude = location!!.latitude
                            longitude = location!!.longitude
                        }
                    }
                }
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES.toLong(),
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                            this
                        )
                        if (locationManager != null) {
                            location =
                                locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            if (location != null) {
                                latitude = location!!.latitude
                                longitude = location!!.longitude
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("test", "" + e.toString())
        }

        return location!!
    }

    fun getLatitude() : Double {
        if(location!= null) {
            latitude = location!!.getLatitude()
        }

        return latitude!!
    }

    fun getLongitude() : Double {
        if(location != null) {
            longitude = location!!.getLongitude()
        }

        return longitude!!
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onLocationChanged(p0: Location) {
    }

    fun stopUsingGPS() {
        if(locationManager != null)
        {
            locationManager!!.removeUpdates(this);
        }
    }
}