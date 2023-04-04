package eg.iti.sv.weather.models

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRepository: RepositoryInterface {

    var places:MutableList<FavPlace>? = mutableListOf()
    var alerts:MutableList<AlertDetails>? = mutableListOf()

    override suspend fun getWeatherOverNetwork(
        lat: String?,
        lon: String?,
        exclude: String?,
        units: String?,
        lang: String?,
        appid: String?
    ): Flow<WeatherResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun removePlace(favPlace: FavPlace) {
        places?.remove(favPlace)
    }

    override suspend fun insertPlace(favPlace: FavPlace) {
        places?.add(favPlace)
    }

    override suspend fun getAllStoredPlaces(): Flow<List<FavPlace>> {
        return flowOf(places as List<FavPlace>)
    }

    override suspend fun insertAlert(alertDetails: AlertDetails) {
        alerts?.add(alertDetails)
    }

    override suspend fun deleteAlert(alertDetails: AlertDetails) {
        alerts?.remove(alertDetails)
    }

    override suspend fun getAllAlerts(): Flow<List<AlertDetails>> {
        return flowOf(alerts as List<AlertDetails>)
    }
}