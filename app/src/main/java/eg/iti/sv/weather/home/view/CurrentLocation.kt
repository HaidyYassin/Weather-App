package eg.iti.sv.weather.home.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class CurrentLocation(var activity:Activity,var context: Context) {
    private val REQUEST_CODE = 5
    var mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

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
        mLocationRequest.interval =0

        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,mLocationCallback, Looper.myLooper()
        )

    }

    private val mLocationCallback: LocationCallback = object : LocationCallback(){
        @SuppressLint("SuspiciousIndentation")
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation : Location? = locationResult.lastLocation
            val longitude =mLastLocation?.longitude
            val altitude =mLastLocation?.altitude
            val geocoder = Geocoder(context)
            println("--------------------------")
            println(longitude.toString()+"  "+altitude.toString())

            val theAddress = geocoder.getFromLocation(altitude as Double, longitude as Double,5)
            if(theAddress?.size!! > 0)
            {
                println("--------------------------")
                println(theAddress?.get(0)?.countryName +theAddress?.get(0)?.subAdminArea +theAddress?.get(0)?.adminArea)
            }}
    }

    @SuppressLint("MissingPermission")
     fun getLastLocation():Unit{
        if(checkPermissions()){
            if(isLocationEnabled()){
                requestNewLocationData()
            }
            else{
                //Toast.makeText(this,"Turn on Location", Toast.LENGTH_SHORT).show()
            }
        }else
            requestPermissions()

    }

}