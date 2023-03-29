package eg.iti.sv.weather.fav.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.iti.sv.weather.models.RepositoryInterface

class FavViewModelFactory (private val _repo: RepositoryInterface)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(FavViewModel::class.java)){
            FavViewModel(_repo) as T
        }else
            throw IllegalArgumentException("class not found")
    }
}