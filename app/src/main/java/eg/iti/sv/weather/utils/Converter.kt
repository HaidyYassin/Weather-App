package eg.iti.sv.weather.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


private val simpleDateFormat = SimpleDateFormat("dd MMMM, h:mm a", Locale.ENGLISH)
private val simpleDayFormat = SimpleDateFormat("EEE", Locale.ENGLISH)
private val simpleHourFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)


fun getHourString(time: Int): String = simpleHourFormat.format(time * 1000L)
fun getDateString(time: Int) : String = simpleDateFormat.format(time * 1000L)
fun getDayString(time: Int) : String = simpleDayFormat.format(time * 1000L)

fun getWeekDay(time: Int):String{
    val day = getDayString(time)
    var fullDayName = ""

    when (day){
        "Sat" -> fullDayName = "Saturday"
        "Sun" -> fullDayName = "Sunday"
        "Mon" -> fullDayName = "Monday"
        "Tue" -> fullDayName = "Tuesday"
        "Wed" -> fullDayName = "Wednesday"
        "Thu" -> fullDayName = "Thursday"
        "Fri" -> fullDayName = "Friday"
    }
    return fullDayName
}

//"h:mm a" -----> 12:08 PM