package eg.iti.sv.weather.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavPlaces")
data class FavPlace(

    val paceName:String,
    val longitude:Double,
    val latitude:Double
    ){
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
}
