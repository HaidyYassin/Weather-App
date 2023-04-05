package eg.iti.sv.weather.alerts.viewmodel

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.iti.sv.weather.home.view.CurrentLocation
import eg.iti.sv.weather.models.AlertDetails
import eg.iti.sv.weather.models.RepositoryInterface
import eg.iti.sv.weather.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*


class AlertsViewModel(private val _repo: RepositoryInterface): ViewModel()  {

    private var _alerts = MutableLiveData<List<AlertDetails>>()
    val alerts : LiveData<List<AlertDetails>> = _alerts

    private var _weather = MutableStateFlow<ApiState>(ApiState.Loading)
    val weather  = _weather.asStateFlow()


    init {
        getAlerts()
    }

    private fun getAlerts(){
        viewModelScope.launch(Dispatchers.IO) {
            _repo.getAllAlerts().collect{
                _alerts.postValue(it)
            }

        }

    }

    fun removeAlert(alert: AlertDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            _repo.deleteAlert(alert)
            getAlerts()
        }
    }

    fun addAlert(alert: AlertDetails){
        viewModelScope.launch(Dispatchers.IO) {
            _repo.insertAlert(alert)
            getAlerts()
        }
    }

    fun getWeatherOverNetwork() = viewModelScope.launch(Dispatchers.IO) {
        delay(4000)
        CurrentLocation.locationStateFlow.collectLatest {
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