package eg.iti.sv.weather.utils
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import eg.iti.sv.weather.home.view.HomeFragment
import eg.iti.sv.weather.models.AppSettings
import java.text.SimpleDateFormat
import java.util.*


var appSettings:AppSettings = HomeFragment.appSettings


private lateinit var simpleDateFormat:SimpleDateFormat
private lateinit var simpleDayFormat:SimpleDateFormat
private lateinit var simpleHourFormat:SimpleDateFormat


fun getHourString(time: Int): String {
    getLanguge()
    return simpleHourFormat.format(time * 1000L)
}
fun getDateString(time: Int) : String {
    getLanguge()
    return simpleDateFormat.format(time * 1000L)}
fun getDayString(time: Int) : String {
    getLanguge()
    return simpleDayFormat.format(time * 1000L)
}

fun getWeekDay(time: Int):String{
    val day = getDayString(time)
    var fullDayName = ""

    if(appSettings.lang =="English"){
    when (day){
        "Sat" -> fullDayName = "Saturday"
        "Sun" -> fullDayName = "Sunday"
        "Mon" -> fullDayName = "Monday"
        "Tue" -> fullDayName = "Tuesday"
        "Wed" -> fullDayName = "Wednesday"
        "Thu" -> fullDayName = "Thursday"
        "Fri" -> fullDayName = "Friday"
    }}else{
        when (day){
        "Sat" -> fullDayName = "السبت"
        "Sun" -> fullDayName = "الاحد"
        "Mon" -> fullDayName = "الاثنين"
        "Tue" -> fullDayName = "الثلاثاء"
        "Wed" -> fullDayName = "الاربعاء"
        "Thu" -> fullDayName = "الخميس"
         "Fri" -> fullDayName = "الجمعة"
        }
    }
    return fullDayName
}

fun getLanguge(){
    if(appSettings.lang =="English"){
        simpleDateFormat =SimpleDateFormat("dd MMMM, h:mm a",Locale.ENGLISH)
        simpleDayFormat = SimpleDateFormat("EEE", Locale.ENGLISH)
        simpleHourFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)

    }else{
        simpleDateFormat =SimpleDateFormat("dd MMMM, h:mm a",Locale("ar"))
       simpleDayFormat = SimpleDateFormat("EEE", Locale.ENGLISH)
        simpleHourFormat = SimpleDateFormat("h:mm a", Locale("ar"))
    }
}


fun checkLanguage(language: String?,context: Context) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration()
    config.locale = locale
    context .resources.updateConfiguration(config, context.resources.displayMetrics)}

fun updateResources(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val res: Resources = context.resources
    val config = Configuration(res.getConfiguration())
    config.locale = locale
    res.updateConfiguration(config, res.getDisplayMetrics())
}
// Locale("ar")
//"h:mm a" -----> 12:08 PM