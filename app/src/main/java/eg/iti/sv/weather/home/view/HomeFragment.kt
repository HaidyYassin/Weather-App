package eg.iti.sv.weather.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
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
import eg.iti.sv.weather.models.Constants
import eg.iti.sv.weather.models.Repository
import eg.iti.sv.weather.network.APIClient
import eg.iti.sv.weather.network.ApiState
import eg.iti.sv.weather.utils.getDateString
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var hourLayoutManager: LinearLayoutManager
    private lateinit var dayLayoutManager: LinearLayoutManager
    private lateinit var hourlyAdapter: HourWeatherAdapter
    private lateinit var weeklyAdapter: WeekWeatherAdapter
    private lateinit var geocoder:Geocoder

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding = FragmentHomeBinding.inflate(inflater,container,false)
        geocoder = Geocoder(activity?.applicationContext as Context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //CurrentLocation(requireActivity(),requireContext()).getLastLocation()

        viewModelFactory = HomeViewModelFactory(
            Repository.getInstance(
                APIClient.getInstance(), ConcreteLocalSource(activity?.applicationContext as Context)
            )
        )

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        lifecycleScope.launch {
            viewModel.weather.collectLatest {
                when(it){

                    is ApiState.Success ->{
                        //binding.cityNameTxt.text = getCityName(it.data.lat.toString(),it.data.lon.toString())
                        binding.tempTxt.text = it.data.current.temp.toString()

                        binding.dateTxt.text =  getDateString(it.data.current.dt)
                        binding.tempDescTxt.text = it.data.current.weather.get(0).description
                        binding.humidityTxt.text = it.data.current.humidity.toString()
                        binding.windTxt.text = it.data.current.wind_speed.toString()
                        binding.pressureTxt.text = it.data.current.pressure.toString()
                        binding.cloudsTxt.text = it.data.current.clouds.toString()
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
                    }

                    else -> {
                        Toast.makeText(activity?.applicationContext as Context,"check your connetion", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }


}