package eg.iti.sv.weather.home.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.iti.sv.weather.home.view.CurrentLocation
import eg.iti.sv.weather.home.view.CurrentLocation.Companion.locationStateFlow
import eg.iti.sv.weather.models.FavPlace
import eg.iti.sv.weather.models.RepositoryInterface
import eg.iti.sv.weather.models.Settings
import eg.iti.sv.weather.network.ApiState
import eg.iti.sv.weather.utils.getAppLocationByMap
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
    lateinit var  address:String


   /* fun getWeatherOverNetwork(place: FavPlace) = viewModelScope.launch(Dispatchers.IO) {
        _repo.getWeatherOverNetwork(lon=place.longitude.toString(), lat = place.latitude.toString())
            .catch {e->
                _weather.value = ApiState.Failure(e)

            }.collect{
                _weather.value = ApiState.Success(it)
            }
    }*/



     fun getWeatherOverNetwork(context:Context) = viewModelScope.launch(Dispatchers.IO) {

         if (Settings.settings.location == "Map") {
             val pair = getAppLocationByMap(context)
             _repo.getWeatherOverNetwork(pair.first, pair.second)
                 .catch { e ->
                     _weather.value = ApiState.Failure(e)

                 }.collect {
                     _weather.value = ApiState.Success(it)
                     _repo.insertWeather(it)
                 }

         } else {
             delay(4000)
             locationStateFlow.collectLatest {
                 println("Consumer: $it")
                 val longlat = it
                 val locList = longlat.split(",")
                 println(locList)
                 //val place = FavPlace("", locList[0].toDouble(), locList[1].toDouble(), longlat)
                 //address = locList[2]
                 _repo.getWeatherOverNetwork(lon = locList[0], lat = locList[1])
                     .catch { e ->
                         _weather.value = ApiState.Failure(e)

                     }.collect {
                         _weather.value = ApiState.Success(it)
                         _repo.insertWeather(it)
                     }
             }
         }
     }

    fun getWeatherFromRoom() = viewModelScope.launch(Dispatchers.IO) {
        _repo.getCurrentWeather()
            .catch {
                _weather.value = ApiState.Failure(it)
            }
            .collect{
            _weather.value = ApiState.Success(it)
        }

    }


}