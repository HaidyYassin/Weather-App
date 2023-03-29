package eg.iti.sv.weather.map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.iti.sv.weather.models.RepositoryInterface

class MapViewModelFactory(private val _repo: RepositoryInterface)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MapViewModel::class.java)){
            MapViewModel(_repo) as T
        }else
            throw IllegalArgumentException("class not found")
    }
}