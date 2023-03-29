package eg.iti.sv.weather.favweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.iti.sv.weather.models.RepositoryInterface

class FavWeatherViewModelFactory(private val _repo: RepositoryInterface): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(FavWeatherViewModel::class.java)){
            FavWeatherViewModel(_repo) as T
        }else
            throw IllegalArgumentException("class not found")
    }
}