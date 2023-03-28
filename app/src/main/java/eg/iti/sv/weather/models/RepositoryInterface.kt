package eg.iti.sv.weather.models

import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {

    suspend fun getWeatherOverNetwork(): Flow<WeatherResponse>
    suspend fun removePlace(favPlace: FavPlace)
    suspend fun insertPlace(favPlace: FavPlace)
    suspend fun getAllStoredPlaces(): Flow<List<FavPlace>>
}