package eg.iti.sv.weather.models

import eg.iti.sv.weather.db.LocalSource
import eg.iti.sv.weather.network.RemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository private constructor(
    var remoteSource: RemoteSource,
    var localSource: LocalSource
) : RepositoryInterface{


    companion object{
        private var instance:Repository? = null
        fun getInstance(
            remoteSource: RemoteSource,
            localSource: LocalSource
        ) : Repository {
            return instance ?: synchronized(this){
                val temp = Repository(remoteSource,localSource)
                instance = temp
                temp
            }
        }
    }

    override suspend fun getWeatherOverNetwork(): Flow<WeatherResponse> {
        return flowOf( remoteSource.getWeatherOverNetwork())
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
}
