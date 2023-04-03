package eg.iti.sv.weather.models

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.*

object Settings {
    var settings:AppSettings= AppSettings()

    fun changeLanguage(context:Context,locale:String):ContextWrapper{
        var myContext=context
        val sysLocale: Locale
        val rs: Resources = context.resources
        val config: Configuration = rs.configuration
        sysLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.locales.get(0)
        } else {
            config.locale
        }
        if (locale != "" && !sysLocale.language.equals(locale)) {
            val locale = Locale(locale)
            Locale.setDefault(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            { config.setLocale(locale) }
            else { config.locale = locale }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            {
                myContext = context.createConfigurationContext(config)
            } else {
                myContext.resources.updateConfiguration(config, context.resources.displayMetrics)
            }
        }
        return ContextWrapper(myContext)
    }
    fun setAppLayoutDirections(locale:String,context: Context) {
        val configuration: Configuration = context.resources.configuration
        configuration.setLayoutDirection(Locale(locale))
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }

    @SuppressLint("ObsoleteSdkInt")
    fun setAppLocale(localeCode: String, context: Context) {
        val resources = context.resources
        val dm = resources.displayMetrics
        val config: Configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            config.setLocale(Locale(localeCode))
        }
        else
        {
            config.locale = Locale(localeCode)
        }
        resources.updateConfiguration(config, dm)
    }
}