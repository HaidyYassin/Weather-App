package eg.iti.sv.weather.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eg.iti.sv.weather.models.AlertDetails
import eg.iti.sv.weather.models.WeatherResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherResponseDao {

    @get:Query("SELECT * FROM Weather")
    val currentWeather: Flow<WeatherResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherResponse: WeatherResponse)
}