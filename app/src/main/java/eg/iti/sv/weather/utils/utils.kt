package eg.iti.sv.weather.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import com.google.gson.Gson
import eg.iti.sv.weather.models.AppSettings
import eg.iti.sv.weather.models.Constants
import java.util.*


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