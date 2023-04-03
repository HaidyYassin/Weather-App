package eg.iti.sv.weather.alerts.viewmodel

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.iti.sv.weather.models.AlertDetails
import eg.iti.sv.weather.models.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*


class AlertsViewModel(private val _repo: RepositoryInterface): ViewModel()  {

    private var _alerts = MutableLiveData<List<AlertDetails>>()
    val alerts : LiveData<List<AlertDetails>> = _alerts


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

}