package eg.iti.sv.weather.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import eg.iti.sv.weather.models.FavPlace

@Database(entities = arrayOf(FavPlace::class), version = 1 )

abstract class AppDataBase  : RoomDatabase(){
    abstract fun getFavPlaceDao(): FavPlaceDao
    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getInstance (ctx: Context): AppDataBase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDataBase::class.java, "color_database")
                    .build()
                INSTANCE = instance
// return instance
                instance }
        }
    }
}