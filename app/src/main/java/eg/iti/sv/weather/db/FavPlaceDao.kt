package eg.iti.sv.weather.db

import androidx.room.*
import eg.iti.sv.weather.models.FavPlace
import kotlinx.coroutines.flow.Flow

@Dao
interface FavPlaceDao {
    @get:Query("SELECT * FROM FavPlaces")
    val allFavPlaces: Flow<List<FavPlace>>

    @Query("SELECT * FROM FavPlaces WHERE latLog = :first")
    fun findPlaceById(first: String): FavPlace

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlace(favPlace: FavPlace?)

    @Delete
    fun deletePlace(favPlace: FavPlace?)
}