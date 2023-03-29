package eg.iti.sv.weather.home.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.iti.sv.weather.home.view.CurrentLocation
import eg.iti.sv.weather.models.RepositoryInterface

class HomeViewModelFactory (private val _repo: RepositoryInterface,private val location: Pair<Double,Double>)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(_repo,location) as T
        }else
            throw IllegalArgumentException("class not found")
    }
}