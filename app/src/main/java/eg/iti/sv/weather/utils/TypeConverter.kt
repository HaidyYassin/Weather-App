package eg.iti.sv.weather.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import eg.iti.sv.weather.models.Alert
import eg.iti.sv.weather.models.Current
import eg.iti.sv.weather.models.Daily
import eg.iti.sv.weather.models.Hourly
import java.lang.reflect.Type

class WeatherModelTypeConverter {
    @TypeConverter
    fun fromCurrent(current: Current?): String {
        val gson = Gson()
        return gson.toJson(current)
    }

    @TypeConverter
    fun toCurrent(json: String?): Current {
        val gson = Gson()
        return gson.fromJson(json, Current::class.java)
    }

    @TypeConverter
    fun fromString(value: String?): Alert? {
        return value?.let {
            // Convert the string to Alert and return it
            val gson = Gson()
            gson.fromJson(it, Alert::class.java)
        }
    }

    @TypeConverter
    fun toString(someObject: Alert?): String? {
        return someObject?.let {
            // Convert the Alert to a string and return it
            val gson = Gson()
            gson.toJson(someObject)
        }
    }

    @TypeConverter
    fun fromDaily(dailyList: List<Daily?>?): String? {
        val gson = Gson()
        return gson.toJson(dailyList)
    }

    @TypeConverter
    fun ToDailyList(data: String?): List<Daily?>? {
        val gson = Gson()
        val listType: Type = object : TypeToken<List<Daily?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun hourlyListToString(hourlyList: List<Hourly?>?): String? {
        val gson = Gson()
        return gson.toJson(hourlyList)
    }

    @TypeConverter
    fun stringToHourlyList(data: String?): List<Hourly?>? {
        val gson = Gson()
        val listType = object : TypeToken<List<Hourly?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromString(value:List< Alert?>? ):String? {
        val gson = Gson()
        return gson.toJson(value)
    }


    @TypeConverter
    fun toString(someObject: String?):List< Alert?>?  {
        val gson = Gson()
        val listType: Type = object : TypeToken<List<Alert?>?>() {}.type
        return gson.fromJson(someObject, listType)
    }
}