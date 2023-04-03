package eg.iti.sv.weather.models

import android.content.Context
import eg.iti.sv.weather.db.LocalSource
import eg.iti.sv.weather.network.RemoteSource
import eg.iti.sv.weather.utils.getCustomizedSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository private constructor(
    var remoteSource: RemoteSource,
    var localSource: LocalSource,
    val context: Context
) : RepositoryInterface{


    companion object{
        private var instance:Repository? = null
        fun getInstance(
            remoteSource: RemoteSource,
            localSource: LocalSource,
            context: Context
        ) : Repository {
            return instance ?: synchronized(this){
                val temp = Repository(remoteSource,localSource,context)
                instance = temp
                temp
            }
        }
    }

    override suspend fun getWeatherOverNetwork(
        lat: String?,
        lon: String?,
        exclude: String?,
        units: String?,
        lang:String?,
        appid: String?,
    ): Flow<WeatherResponse> {
        var unit:String
        var lan:String
      //  val appSettings = getCustomizedSettings(context)
        if(Settings.settings?.temp == "Celsius")
            unit = "metric"
        else if(Settings.settings?.temp == "Fahrenheit")
            unit = "imperial"
        else
            unit ="standard"

        if(Settings.settings?.lang =="Arabic")
            lan = "ar"
        else
            lan ="en"
        return flowOf( remoteSource.getWeatherOverNetwork(lat,lon,units = unit, lang = lan))
    }

    override suspend fun removePlace(favPlace: FavPlace) {
        localSource.deletePlace(favPlace)
    }

    override suspend fun insertPlace(favPlace: FavPlace) {
        localSource.insertPlace(favPlace)
    }

    override suspend fun getAllStoredPlaces(): Flow<List<FavPlace>> {
        return localSource.getAllFavPlaces()
    }

    override suspend fun insertAlert(alertDetails: AlertDetails) {
        localSource.insertAlert(alertDetails)
    }

    override suspend fun deleteAlert(alertDetails: AlertDetails) {
        localSource.deleteAlert(alertDetails)
    }

    override suspend fun getAllAlerts(): Flow<List<AlertDetails>> {
        return localSource.getAllAlerts()
    }
}
