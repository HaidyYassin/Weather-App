package eg.iti.sv.weather.settings.view

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import eg.iti.sv.weather.R
import eg.iti.sv.weather.databinding.FragmentSettingsBinding
import eg.iti.sv.weather.models.AppSettings
import eg.iti.sv.weather.utils.createAppSettings
import eg.iti.sv.weather.utils.getCustomizedSettings
import org.intellij.lang.annotations.Language


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var language: RadioButton
    private lateinit var  location:RadioButton
    private lateinit var wind:RadioButton
    private lateinit var temp:RadioButton
    private lateinit var settingsObj:AppSettings
    private lateinit var uiState:AppSettings

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingsBinding.inflate(inflater,container,false)

        uiState = getCustomizedSettings(requireContext()) as AppSettings

        if(uiState.lang == "English")
             binding.radioEnglish.isChecked=true
        else
            binding.radioArabic.isChecked=true

        if(uiState.wind == "meter/sec")
            binding.radioMeterSec.isChecked=true
        else
            binding.radioMilesHour.isChecked=true

        if(uiState.temp == "Celsius")
            binding.radioCelsius.isChecked = true
        else if(uiState.temp == "Fahrenheit")
            binding.radioFahrenheit.isChecked = true
        else
            binding.radioKelvin.isChecked = true

        if(uiState.location == "GPS")
            binding.radioGps.isChecked=true
        else
            binding.radioMap.isChecked = true


        settingsObj = AppSettings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // location
        binding.locationRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            location = view.findViewById<View>(checkedId) as RadioButton

            when (location.text) {
                getString(R.string.gps) -> {
                    println("gps")
                    settingsObj.location = "GPS"
                    createAppSettings(requireContext(),settingsObj)
                }
                getString(R.string.map) -> {
                    println("map")
                    settingsObj.location ="Map"
                    createAppSettings(requireContext(),settingsObj)
                }
            }
        }

        // language
        binding.langRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            language = view.findViewById<View>(checkedId) as RadioButton

            when (language.text) {
                getString(R.string.arabic) -> {
                    println("arabic")
                    settingsObj.lang = "Arabic"
                    createAppSettings(requireContext(),settingsObj)
                }
                getString(R.string.english) -> {
                    println("english")
                    settingsObj.lang = "English"
                    createAppSettings(requireContext(),settingsObj)
                }
            }
        }

        // temperature
        binding.tempRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            temp = view.findViewById<View>(checkedId) as RadioButton

            when (temp.text) {
                getString(R.string.celsius) -> {
                    println("c")
                    settingsObj.temp ="Celsius"
                    createAppSettings(requireContext(),settingsObj)
                }
                getString(R.string.fahrenheit) -> {
                    println("f")
                    settingsObj.temp ="Fahrenheit"
                    createAppSettings(requireContext(),settingsObj)
                }
                getString(R.string.kelvin) -> {
                    println("k")
                    settingsObj.temp ="Kelvin"
                    createAppSettings(requireContext(),settingsObj)
                }
            }
        }

        // windSpeed
        binding.windRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            wind = view.findViewById<View>(checkedId) as RadioButton

            when (wind.text) {
                getString(R.string.miles_hour) -> {
                    println("miles")
                    settingsObj.wind = "miles/hour"
                    createAppSettings(requireContext(),settingsObj)
                }
                getString(R.string.meter_sec) -> {
                    println("meters")
                    settingsObj.wind = "meter/sec"
                    createAppSettings(requireContext(),settingsObj)

                }
            }
        }



    }




}