package eg.iti.sv.weather.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eg.iti.sv.weather.R
import eg.iti.sv.weather.databinding.FragmentHomeBinding
import eg.iti.sv.weather.db.ConcreteLocalSource
import eg.iti.sv.weather.home.viewmodel.HomeViewModel
import eg.iti.sv.weather.home.viewmodel.HomeViewModelFactory
import eg.iti.sv.weather.models.*
import eg.iti.sv.weather.network.APIClient
import eg.iti.sv.weather.network.ApiState
import eg.iti.sv.weather.utils.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var hourLayoutManager: LinearLayoutManager
    private lateinit var dayLayoutManager: LinearLayoutManager
    private lateinit var hourlyAdapter: HourWeatherAdapter
    private lateinit var weeklyAdapter: WeekWeatherAdapter
    private lateinit var currentLocation: CurrentLocation
    private lateinit var tempUnit:String
    private lateinit var windUnit:String


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if(Settings.settings.lang == "Arabic")
            Settings.setAppLocale("ar",requireContext())
        else
            Settings.setAppLocale("en",requireContext())

        if(Settings.settings.temp == "Fahrenheit")
            tempUnit = getString(R.string.f)
        else if(Settings.settings.temp == "Celsius")
            tempUnit = getString(R.string.c)
        else
            tempUnit = getString(R.string.k)

        if(Settings.settings.wind == "meter/sec")
            windUnit = getString(R.string.m_s)
        else
            windUnit = getString(R.string.m_h)


        binding = FragmentHomeBinding.inflate(inflater,container,false)
        currentLocation =CurrentLocation(requireActivity(),requireContext())
        currentLocation.getLastLocation()

        return binding.root
    }



   /* override fun onResume() {
        super.onResume()
        currentLocation.getLastLocation()
    }*/


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

          viewModelFactory = HomeViewModelFactory(
              Repository.getInstance(
                  APIClient.getInstance(), ConcreteLocalSource(activity?.applicationContext as Context)
              ,requireContext())
          )

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
       // viewModel.getWeatherOverNetwork(place)
        lifecycleScope.launch {
            viewModel.weather.collectLatest {
                when(it){

                    is ApiState.Success ->{
                        binding.homeDetailsCard.visibility = View.VISIBLE

                        binding.cityNameTxt.text = currentLocation.myaddress
                        binding.tempTxt.text = it.data.current.temp.toString()+tempUnit
                        binding.dateTxt.text =  getDateString(it.data.current.dt)
                        binding.tempDescTxt.text = it.data.current.weather.get(0).description
                        binding.humidityTxt.text = it.data.current.humidity.toString()+" %"
                        binding.windTxt.text = it.data.current.wind_speed.toString()+windUnit
                        binding.pressureTxt.text = it.data.current.pressure.toString()+getString(R.string.hpa)
                        binding.cloudsTxt.text = it.data.current.clouds.toString()+" %"
                        Glide
                            .with(activity?.applicationContext as Context)
                            .load(Constants.WEATHER_IMAGE_BASE_URL+it.data.current.weather.get(0).icon+".png")
                            .into(binding.weatherIcon)

                        //hourly
                        hourLayoutManager = LinearLayoutManager(activity?.applicationContext as Context)
                        hourLayoutManager.orientation = RecyclerView.HORIZONTAL
                        hourlyAdapter = HourWeatherAdapter(activity?.applicationContext as Context, it.data.hourly)
                        binding.hourlyWatherRecycler.adapter = hourlyAdapter
                        binding.hourlyWatherRecycler.layoutManager = hourLayoutManager

                        //Daily
                        dayLayoutManager = LinearLayoutManager(activity?.applicationContext as Context)
                        dayLayoutManager.orientation = RecyclerView.VERTICAL
                        weeklyAdapter = WeekWeatherAdapter(activity?.applicationContext as Context, it.data.daily)
                        binding.weekWeatherRecycler.adapter = weeklyAdapter
                        binding.weekWeatherRecycler.layoutManager = dayLayoutManager

                        binding.animationviewloading.visibility = View.GONE


                    }
                    is ApiState.Loading ->{
                       binding.animationviewloading.visibility = View.VISIBLE
                       binding.homeDetailsCard.visibility = View.INVISIBLE
                    }

                    else -> {
                        Toast.makeText(activity?.applicationContext as Context,"check your connetion", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }

}