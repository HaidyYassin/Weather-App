package eg.iti.sv.weather.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.net.ConnectivityManager
import com.google.gson.Gson
import eg.iti.sv.weather.MainActivity
import eg.iti.sv.weather.models.AppSettings
import eg.iti.sv.weather.models.Constants
import java.util.*


fun isNetworkAvailable(context:Context): Boolean {
    val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager?.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun createAppSettings( context: Context, settingsObj: AppSettings = AppSettings()){
    val mPrefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
    val prefsEditor: SharedPreferences.Editor = mPrefs.edit()
    val gson = Gson()
    val json = gson.toJson(settingsObj)
    prefsEditor.putString(Constants.APP_SETTINGS_VALUES, json)
    prefsEditor.commit()
}

fun firstTime(context: Context): Boolean {
    val sh = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
    return sh.getBoolean("finished", false)
}


fun getCustomizedSettings(context: Context): AppSettings? {
    val mPrefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
    val gson = Gson()
    val json = mPrefs.getString(Constants.APP_SETTINGS_VALUES, "")
    val settingsObj:AppSettings? = gson.fromJson(json, AppSettings::class.java)
    return settingsObj
}

fun setAppLocationByMap(context: Context,long:String,lat:String){
    val preferences: SharedPreferences = context.getSharedPreferences(Constants.PREF_Location_NAME, Context.MODE_PRIVATE)
    val editor = preferences.edit()
    editor.putString(Constants.LONGITUDE, long)
    editor.putString(Constants.LATITUDE, lat)
    editor.commit()
}

fun getAppLocationByMap(context: Context): Pair<String?, String?> {
    val preferences: SharedPreferences =
        context.getSharedPreferences(Constants.PREF_Location_NAME, Context.MODE_PRIVATE)
    return Pair(preferences.getString(Constants.LONGITUDE, "not available"),
    preferences.getString(Constants.LATITUDE, "not available"))
}



class LocaleManager {
    fun setLocale(c: Context) {
        //setNewLocale(c, getLanguage(c))
    }

    fun setNewLocale(c: Context, language: String) {
        updateResources(c, language)
    }


    private fun updateResources(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res: Resources = context.resources
        val config = Configuration(res.getConfiguration())
        config.locale = locale
        res.updateConfiguration(config, res.getDisplayMetrics())
    }
}