package eg.iti.sv.weather.fav.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.iti.sv.weather.models.FavPlace
import eg.iti.sv.weather.models.RepositoryInterface
import eg.iti.sv.weather.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavViewModel(private val _repo: RepositoryInterface):ViewModel() {

    private var _places  = MutableLiveData<List<FavPlace>>()
    val places : LiveData<List<FavPlace>> = _places

    init {
        getFavPlaces()
    }

    private fun getFavPlaces(){
        viewModelScope.launch(Dispatchers.IO) {
            _repo.getAllStoredPlaces().collect{
                _places.postValue(it)
            }

        }

    }

    fun removeFromFav(place: FavPlace) {
        viewModelScope.launch(Dispatchers.IO) {
            _repo.removePlace(place)
            getFavPlaces()
        }
    }

}