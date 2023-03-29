package eg.iti.sv.weather.favweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.iti.sv.weather.models.FavPlace
import eg.iti.sv.weather.models.RepositoryInterface
import eg.iti.sv.weather.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavWeatherViewModel(private val _repo: RepositoryInterface) :ViewModel(){

    private var _weather = MutableStateFlow<ApiState>(ApiState.Loading)
    val weather  = _weather.asStateFlow()

    fun getWeatherOverNetwork(place: FavPlace) = viewModelScope.launch(Dispatchers.IO) {
        _repo.getWeatherOverNetwork(lon=place.longitude.toString(), lat = place.latitude.toString())
            .catch {e->
                _weather.value = ApiState.Failure(e)

            }.collect{
                _weather.value = ApiState.Success(it)
            }
    }
}