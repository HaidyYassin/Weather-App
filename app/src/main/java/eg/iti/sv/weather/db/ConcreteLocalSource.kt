package eg.iti.sv.weather.db

import android.content.Context
import eg.iti.sv.weather.models.AlertDetails
import eg.iti.sv.weather.models.FavPlace
import kotlinx.coroutines.flow.Flow

class ConcreteLocalSource(context: Context):LocalSource {

    private val favPlaceDao: FavPlaceDao by lazy{
        val db :AppDataBase = AppDataBase.getInstance(context)
       db.getFavPlaceDao()
    }

    private val alertsDao:AlertsDao by lazy{
        val db :AppDataBase = AppDataBase.getInstance(context)
       db.getAlertsDao()
    }

    override suspend fun insertPlace(favPlace: FavPlace) {
        favPlaceDao.insertPlace(favPlace)
    }

    override suspend fun deletePlace(favPlace: FavPlace?) {
        favPlaceDao.deletePlace(favPlace)
    }

    override suspend fun getAllFavPlaces(): Flow<List<FavPlace>> {
        return favPlaceDao.allFavPlaces
    }

    override suspend fun findPlaceById(id: String): FavPlace {
       return favPlaceDao.findPlaceById(id)
    }

    override suspend fun insertAlert(alert: AlertDetails) {
        alertsDao.insertAlert(alert)
    }

    override suspend fun deleteAlert(alert: AlertDetails) {
        alertsDao.deleteAlert(alert)
    }

    override suspend fun getAllAlerts(): Flow<List<AlertDetails>> {
       return alertsDao.allAlerts
    }
}