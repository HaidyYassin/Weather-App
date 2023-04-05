package eg.iti.sv.weather.home.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder

import android.location.LocationManager
import android.os.Looper

import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CurrentLocation(var activity:Activity,var context: Context,var longitude:Double =0.0, var latitude:Double =0.0) {
    private val REQUEST_CODE = 5
    var mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    var myaddress =" "

    companion object {
       @JvmStatic val locationStateFlow = MutableStateFlow("0,0")
    }


    private fun checkPermissions():Boolean{
        return ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions(){
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),REQUEST_CODE
        )
    }

    private fun isLocationEnabled():Boolean{
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(){
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval =1000
        mLocationRequest.numUpdates=1

        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,mLocationCallback, Looper.myLooper()
        )

    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
            @SuppressLint("SuspiciousIndentation")
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)


                val mLastLocation = locationResult.lastLocation
                longitude = mLastLocation?.longitude as Double
                latitude = mLastLocation?.latitude as Double
                val geocoder = Geocoder(context)
                println("--------------------------")
                println(longitude.toString() + "  " + latitude.toString())

                val theAddress =
                    geocoder.getFromLocation(latitude as Double, longitude as Double, 5)
                if (theAddress?.size!! > 0) {
                    println("--------------------------")
                    println(
                        theAddress?.get(0)?.countryName + theAddress?.get(0)?.subAdminArea + theAddress?.get(
                            0
                        )?.adminArea
                    )
                    myaddress = theAddress?.get(0)?.subAdminArea.toString()
                }

                GlobalScope.launch {
                     locationStateFlow.emit(longitude.toString() + "," + latitude.toString()+","+myaddress)
                }

            }
        }

    @SuppressLint("MissingPermission")
     fun getLastLocation(){
        if(checkPermissions()){
            if(isLocationEnabled()){
                requestNewLocationData()
            }
        }else
            requestPermissions()
    }

}