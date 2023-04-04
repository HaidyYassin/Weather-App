package eg.iti.sv.weather.db

import eg.iti.sv.weather.models.AlertDetails
import eg.iti.sv.weather.models.FavPlace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLocalDataSource(var places:MutableList<FavPlace>? = mutableListOf(),
                          var alerts:MutableList<AlertDetails>? = mutableListOf()) :LocalSource {

    override suspend fun insertPlace(favPlace: FavPlace) {
        places?.add(favPlace)
    }

    override suspend fun deletePlace(favPlace: FavPlace?) {
        places?.remove(favPlace)
    }

    override suspend fun getAllFavPlaces(): Flow<List<FavPlace>> {
        return flowOf(places as List<FavPlace>)
    }

    override suspend fun findPlaceById(id: String): FavPlace {
        return places?.get(id.toInt()) as FavPlace
    }

    override suspend fun insertAlert(alert: AlertDetails) {
        alerts?.add(alert)
    }

    override suspend fun deleteAlert(alert: AlertDetails) {
        alerts?.remove(alert)
    }

    override suspend fun getAllAlerts(): Flow<List<AlertDetails>> {
        return flowOf(alerts as List<AlertDetails>)
    }
}