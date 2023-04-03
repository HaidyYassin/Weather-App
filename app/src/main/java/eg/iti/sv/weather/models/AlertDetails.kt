package eg.iti.sv.weather.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Alerts")
data class AlertDetails(val date:String="",
                        val startTime:String="",
                        val endTime:String="",
                        val type:String="",
                        val placeName:String="",
                        val longitude:Double=0.0,
                        val latitude:Double=0.0,

                        @PrimaryKey
                        val pk:String
                        ) :java.io.Serializable
