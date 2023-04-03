package eg.iti.sv.weather.alerts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.iti.sv.weather.models.RepositoryInterface

class AlertsViewModelFactory(private val _repo: RepositoryInterface)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(AlertsViewModel::class.java)){
            AlertsViewModel(_repo) as T
        }else
            throw IllegalArgumentException("class not found")
    }
}