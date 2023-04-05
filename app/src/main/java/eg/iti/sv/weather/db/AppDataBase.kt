package eg.iti.sv.weather.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import eg.iti.sv.weather.models.AlertDetails
import eg.iti.sv.weather.models.FavPlace
import eg.iti.sv.weather.models.WeatherResponse
import eg.iti.sv.weather.utils.WeatherModelTypeConverter


@Database(entities = arrayOf(FavPlace::class,AlertDetails::class,WeatherResponse::class), version = 3)
@TypeConverters(WeatherModelTypeConverter::class)

abstract class AppDataBase  : RoomDatabase(){
    abstract fun getFavPlaceDao(): FavPlaceDao
    abstract fun getAlertsDao() : AlertsDao
    abstract fun getResponseDao():WeatherResponseDao
    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getInstance (ctx: Context): AppDataBase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDataBase::class.java, "color_database")
                    .build()
                INSTANCE = instance
                instance }
        }
    }
}