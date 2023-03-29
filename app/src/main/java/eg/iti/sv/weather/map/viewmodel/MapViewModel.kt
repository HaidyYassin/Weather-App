package eg.iti.sv.weather.map.viewmodel

import androidx.lifecycle.*
import eg.iti.sv.weather.models.FavPlace
import eg.iti.sv.weather.models.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(private val _repo: RepositoryInterface) :ViewModel(){

    fun addPlaceToFav(favPlace: FavPlace){
        viewModelScope.launch(Dispatchers.IO) {
            _repo.insertPlace(favPlace)
        }


    }
}