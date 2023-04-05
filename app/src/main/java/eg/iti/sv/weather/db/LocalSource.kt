package eg.iti.sv.weather.db

import eg.iti.sv.weather.models.AlertDetails
import eg.iti.sv.weather.models.FavPlace
import eg.iti.sv.weather.models.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    suspend fun insertPlace(favPlace: FavPlace)
    suspend fun deletePlace(favPlace: FavPlace?)
    suspend fun getAllFavPlaces() : Flow<List<FavPlace>>
    suspend fun findPlaceById(id: String): FavPlace

    suspend fun insertAlert(alert: AlertDetails)
    suspend fun deleteAlert(alert: AlertDetails)
    suspend fun getAllAlerts(): Flow<List<AlertDetails>>

    suspend fun insertWeather(weatherResponse: WeatherResponse)
    suspend fun getCurrentWeather(): Flow<WeatherResponse>

}