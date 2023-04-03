package eg.iti.sv.weather.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.iti.sv.weather.home.view.CurrentLocation
import eg.iti.sv.weather.home.view.CurrentLocation.Companion.locationStateFlow
import eg.iti.sv.weather.models.FavPlace
import eg.iti.sv.weather.models.RepositoryInterface
import eg.iti.sv.weather.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(private val _repo: RepositoryInterface) : ViewModel(){

    private var _weather = MutableStateFlow<ApiState>(ApiState.Loading)
    val weather  = _weather.asStateFlow()

    init {
        getWeatherOverNetwork()
    }

   /* fun getWeatherOverNetwork(place: FavPlace) = viewModelScope.launch(Dispatchers.IO) {
        _repo.getWeatherOverNetwork(lon=place.longitude.toString(), lat = place.latitude.toString())
            .catch {e->
                _weather.value = ApiState.Failure(e)

            }.collect{
                _weather.value = ApiState.Success(it)
            }
    }*/





    private fun getWeatherOverNetwork() = viewModelScope.launch(Dispatchers.IO) {
        delay(4000)
        locationStateFlow.collectLatest {
            println("Consumer: $it")
            val longlat = it
            val locList = longlat.split(",")
            println(locList)
            //val place = FavPlace("", locList[0].toDouble(), locList[1].toDouble(), longlat)
            _repo.getWeatherOverNetwork(lon = locList[0], lat = locList[1])
                .catch { e ->
                    _weather.value = ApiState.Failure(e)

                }.collect {
                    _weather.value = ApiState.Success(it)
                }
        }
    }



   /* fun getLocation():Pair<String,String>{
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener =object : LocationListener {
            override fun onLocationChanged(location: Location) {
                long = location.longitude.toString()
                lat = location.latitude.toString()
            }

        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)

            ActivityCompat.requestPermissions(context as Activity,permissions,REQUEST_CODE)
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,5f,locationListener)
        }
        return Pair(long,lat)
    }
    public fun onRequestPermissionsResult(requestCode:Int,permissions:Array<String>, grantResults:Array<Int>):Unit {

    }*/

}