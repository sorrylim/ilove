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
import android.widget.Toast
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


    fun getDistance(userLatitude: String, userLongitude: String, partnerLatitude: String, partnerLongitude: String) : ArrayList<String> {
        var locationA : Location = Location("pointA")
        var locationB : Location = Location("pointB")

        locationA.setLatitude(userLatitude.toDouble())
        locationA.setLongitude(userLongitude.toDouble())

        locationB.setLatitude(partnerLatitude.toDouble())
        locationB.setLongitude(partnerLongitude.toDouble())

        var distanceInt = locationA.distanceTo(locationB)
        var distance = ""

        var distanceForm = ArrayList<String>()

        if(distanceInt < 100) {
            distanceForm.clear()
            distanceForm.add(String.format("%.0f", distanceInt))
            distanceForm.add("m")
        }
        else if(100<= distanceInt && distanceInt < 1000) {
            distanceForm.clear()
            distanceForm.add((distanceInt - (distanceInt%100)).toString())
            distanceForm.add("m")
        }
        else if(1000<=distanceInt && distanceInt < 10000) {
            distanceInt /= 1000
            distanceForm.clear()
            distanceForm.add(String.format("%.1f", distanceInt))
            distanceForm.add("km")
        }
        else if(10000<=distanceInt) {
            distanceForm.clear()
            distanceForm.add(String.format("%.0f", (distanceInt - distanceInt%1000)))
            distanceForm.add("km")
        }

        return distanceForm
    }

    fun getSortDistance(userLatitude: String, userLongitude: String, partnerLatitude: String, partnerLongitude: String) : String {
        var locationA : Location = Location("pointA")
        var locationB : Location = Location("pointB")

        locationA.setLatitude(userLatitude.toDouble())
        locationA.setLongitude(userLongitude.toDouble())

        locationB.setLatitude(partnerLatitude.toDouble())
        locationB.setLongitude(partnerLongitude.toDouble())

        var distanceInt = locationA.distanceTo(locationB)

        return distanceInt.toString()
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
                else {Toast.makeText(this,"네트워크 상태를 확인해주세요",Toast.LENGTH_SHORT).show()}
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

    enum class TimeValue(val value: Int,val maximum : Int, val msg : String) {
        SEC(60,60,"분"),
        MIN(60,24,"시간"),
        HOUR(24,30,"일"),
        DAY(30,12,"달"),
        MONTH(12,Int.MAX_VALUE,"년")
    }

    fun timeDiff(time : Long):String{
        val curTime = System.currentTimeMillis()
        var diffTime = (curTime- time) / 1000
        var msg: String? = null
        if(diffTime < TimeValue.SEC.value)
            msg= "방금 전"
        else {
            for (i in TimeValue.values()) {
                diffTime /= i.value
                if (diffTime < i.maximum) {
                    msg= diffTime.toString() + i.msg
                    break
                }
            }
        }
        return msg!!
    }
}