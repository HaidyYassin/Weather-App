package eg.iti.sv.weather.db

import androidx.room.*
import eg.iti.sv.weather.models.AlertDetails
import eg.iti.sv.weather.models.FavPlace
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertsDao {

    @get:Query("SELECT * FROM Alerts")
    val allAlerts: Flow<List<AlertDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlert(alertDetails: AlertDetails)

    @Delete
    fun deleteAlert(alertDetails: AlertDetails)
}