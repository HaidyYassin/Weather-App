package eg.iti.sv.weather.models

import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {

    suspend fun getWeatherOverNetwork(
        lat: String? ,
        lon: String?,
        exclude: String?="minutely",
        units: String?="metric",
        lang:String?="en",
        appid: String?="f2f9ec409c67b8498f33c2bf4c7fb7e7"
    ): Flow<WeatherResponse>
    suspend fun removePlace(favPlace: FavPlace)
    suspend fun insertPlace(favPlace: FavPlace)
    suspend fun getAllStoredPlaces(): Flow<List<FavPlace>>

    suspend fun insertAlert(alertDetails: AlertDetails)
    suspend fun deleteAlert(alertDetails: AlertDetails)
    suspend fun getAllAlerts(): Flow<List<AlertDetails>>
}