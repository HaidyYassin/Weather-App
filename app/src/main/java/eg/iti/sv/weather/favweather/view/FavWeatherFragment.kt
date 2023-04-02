package eg.iti.sv.weather.favweather.view

import android.content.Context
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
import eg.iti.sv.weather.databinding.FragmentFavWeatherBinding
import eg.iti.sv.weather.db.ConcreteLocalSource
import eg.iti.sv.weather.favweather.viewmodel.FavWeatherViewModel
import eg.iti.sv.weather.favweather.viewmodel.FavWeatherViewModelFactory
import eg.iti.sv.weather.home.view.HourWeatherAdapter
import eg.iti.sv.weather.home.view.WeekWeatherAdapter
import eg.iti.sv.weather.home.viewmodel.HomeViewModel
import eg.iti.sv.weather.home.viewmodel.HomeViewModelFactory
import eg.iti.sv.weather.models.Constants
import eg.iti.sv.weather.models.FavPlace
import eg.iti.sv.weather.models.Repository
import eg.iti.sv.weather.network.APIClient
import eg.iti.sv.weather.network.ApiState
import eg.iti.sv.weather.utils.getDateString
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavWeatherFragment : Fragment() {
    private lateinit var hourLayoutManager: LinearLayoutManager
    private lateinit var dayLayoutManager: LinearLayoutManager
    private lateinit var hourlyAdapter: HourWeatherAdapter
    private lateinit var weeklyAdapter: WeekWeatherAdapter

    private lateinit var viewModel: FavWeatherViewModel
    private lateinit var viewModelFactory: FavWeatherViewModelFactory

    private lateinit var binding: FragmentFavWeatherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavWeatherBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val place = arguments?.get("weather")  //arguments?.getSerializable("weather",ApiState::class.java)
        println(place)

        viewModelFactory = FavWeatherViewModelFactory(
            Repository.getInstance(
                APIClient.getInstance(), ConcreteLocalSource(activity?.applicationContext as Context)
            ,requireContext())
        )

        viewModel = ViewModelProvider(this, viewModelFactory).get(FavWeatherViewModel::class.java)
        viewModel.getWeatherOverNetwork(place as FavPlace)
        lifecycleScope.launch {
            viewModel.weather.collectLatest {
                when(it){

                    is ApiState.Success ->{
                        binding.favCityNameTxt.text = place.paceName
                        binding.favTempTxt.text = it.data.current.temp.toString()
                        binding.favDateTxt.text =  getDateString(it.data.current.dt)
                        binding.favTempDescTxt.text = it.data.current.weather.get(0).description
                        binding.favHumidityTxt.text = it.data.current.humidity.toString()
                        binding.favWindTxt.text = it.data.current.wind_speed.toString()
                        binding.favPressureTxt.text = it.data.current.pressure.toString()
                        binding.favCloudsTxt.text = it.data.current.clouds.toString()
                        Glide
                            .with(activity?.applicationContext as Context)
                            .load(Constants.WEATHER_IMAGE_BASE_URL+it.data.current.weather.get(0).icon+".png")
                            .into(binding.favWeatherIcon)

                        //hourly
                        hourLayoutManager = LinearLayoutManager(activity?.applicationContext as Context)
                        hourLayoutManager.orientation = RecyclerView.HORIZONTAL
                        hourlyAdapter = HourWeatherAdapter(activity?.applicationContext as Context, it.data.hourly)
                        binding.favHourlyWatherRecycler.adapter = hourlyAdapter
                        binding.favHourlyWatherRecycler.layoutManager = hourLayoutManager

                        //Daily
                        dayLayoutManager = LinearLayoutManager(activity?.applicationContext as Context)
                        dayLayoutManager.orientation = RecyclerView.VERTICAL
                        weeklyAdapter = WeekWeatherAdapter(activity?.applicationContext as Context, it.data.daily)
                        binding.favWeekWeatherRecycler.adapter = weeklyAdapter
                        binding.favWeekWeatherRecycler.layoutManager = dayLayoutManager


                        binding.animationviewloading.visibility = View.GONE
                    }
                    is ApiState.Loading -> {
                        binding.animationviewloading.visibility = View.VISIBLE
                    }

                    else -> {
                        Toast.makeText(activity?.applicationContext as Context,"check your connetion", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            }
        }
}