package eg.iti.sv.weather.db

import eg.iti.sv.weather.models.FavPlace
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    suspend fun insertPlace(favPlace: FavPlace)
    suspend fun deletePlace(favPlace: FavPlace?)
    suspend fun getAllFavPlaces() : Flow<List<FavPlace>>
    suspend fun findPlaceById(id: Int): FavPlace
}